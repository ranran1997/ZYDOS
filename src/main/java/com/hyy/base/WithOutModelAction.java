package com.hyy.base;

import com.hyy.core.ZYDOS;
import com.hyy.sys.login.UserPermission;
import com.hyy.sys.permission.Permission;
import com.hyy.utils.StringUtils;
import net.sf.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HengYanYan on 2016/4/17  16:17
 */
public class WithOutModelAction extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }

    /**
     * 取得一个session
     */
    public HttpSession getSession() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest().getSession();
    }

    /**
     * 重定向
     *
     * @param path
     * @return
     */
    public String redirect(String path) {
        return StringUtils.join(new String[]{"redirect:", path});
    }

    /**
     * 统一的错误转向
     *
     * @param model
     * @param errorMsg
     * @return
     */
    public String error(Model model, String errorMsg) {
        return error(errorMsg);
    }

    /**
     * 统一的错误转向
     *
     * @param errorMsg
     * @return
     */
    public String error(String errorMsg) {
        throw new RuntimeException(errorMsg);
    }

    /**
     * 无model的action取RequestMapping中的value为路径
     *
     * @return
     */
    public String dir() {
        return StringUtils.lowerCase(getClass().getAnnotation(RequestMapping.class).value()[0].replaceAll("/", ""));
    }

    /**
     * 跳转
     *
     * @param path
     * @return
     */
    public String forward(String path) {
        return StringUtils.join("/", new String[]{dir(), path});
    }

    /**
     * 跳转
     *
     * @param path
     * @return
     */
    public String forward(String path, String viewsName) {
        return StringUtils.join("/", new String[]{viewsName, dir(), path});
    }

    public UserPermission getUserPermission() {
        return (UserPermission) this.getSession().getAttribute(UserPermission.LOGIN_SESSION_INFO);
    }

    public Map getAddressMes(HttpServletRequest request) {
        Map map = null;
        //百度获取当前定位信息
        try {
            String ip = getIpAddr(request);
            ip = "111.23.48.152";
            URL rejson = new URL("http://api.map.baidu.com/location/ip?ak=" + ZYDOS.mapAPI.BDWEBAK + "&ip=" + ip + "&coor=bd09ll");
            //读取
            BufferedReader in = new BufferedReader(new InputStreamReader(rejson.openStream(), Charset.forName("UTF-8")));
            String res;
            StringBuffer sb = new StringBuffer();
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            in.close();
            String str = sb.toString();
            //String to map
            JSONObject obj = JSONObject.fromObject(str);
            map = obj;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        assert map != null;
        if ((Integer) map.get("status") != 0) {
            return null;
        }
        Map point = (Map) ((Map) map.get("content")).get("point");
        String lnglat = point.get("x").toString() + ',' + point.get("y").toString();
        try {

            URL rejson = new URL("http://restapi.amap.com/v3/assistant/coordinate/convert?locations=" + lnglat + "&coordsys=baidu&key=" + ZYDOS.mapAPI.WEBAPI);
            //读取
            BufferedReader in = new BufferedReader(new InputStreamReader(rejson.openStream(), Charset.forName("UTF-8")));
            String res;
            StringBuffer stringBuffer = new StringBuffer();
            while ((res = in.readLine()) != null) {
                stringBuffer.append(res.trim());
            }
            in.close();
            String str = stringBuffer.toString();
            //String to map
            JSONObject object = JSONObject.fromObject(str);
            map = object;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return map;
    }


    /**
     * 获取访问者IP
     * <p>
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * <p>
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 判断当前登录账户是否重复登录登录
     */
    public boolean userCheck(HttpServletRequest request) {
        boolean flag;
        SessionListener sessionListener = new SessionListener();
        Map<Integer, HttpSession> loginUsers = sessionListener.getLoginUsers();
        if (loginUsers.isEmpty()) {
            flag = false;
        } else {
            if (loginUsers.containsValue(request.getSession()))
                flag = true;
            else flag = false;
        }
        return flag;
    }

    /**
     * 判断是否为管理员
     */
    /*@Autowired
    UserService userService;
    public boolean isAdmin(Integer id) {

        boolean flag = false;
        List<Integer> adminIds = userService.findAdminIds();
        if (adminIds.contains(id))
            flag = true;
        return flag;
    }*/
    public List<Permission> getchildPermission(String uri) {
        Assert.notNull(uri, "uri is null, can not get child permission");
        String path = uri.substring(1, uri.lastIndexOf("."));

        List<Permission> permissions = new ArrayList<Permission>(this.getUserPermission().getPermissions());

        //非递归实现
        for (int i = 0; i < permissions.size(); i++) {
            Permission permission = permissions.get(i);
            if (path.equals(permission.getPath())) {
                return permission.getChildPermissions();
            } else if (!permission.getChildPermissions().isEmpty()) {
                permissions.addAll(permission.getChildPermissions());
            }
        }

        return null;
    }
}
