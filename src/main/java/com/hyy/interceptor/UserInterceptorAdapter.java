package com.hyy.interceptor;

import com.hyy.sys.login.UserPermission;
import com.hyy.sys.permission.Permission;
import com.hyy.utils.ObjectUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户拦截适配器，用以做权限控制或者Ip限制
 * Created by HengYanYan on 2016/4/22  23:44
 */
//Todo 添加角色，进行权限管理，单独数据表进行权限控制
public class UserInterceptorAdapter extends HandlerInterceptorAdapter {

    private static final String LOGINPAGE = "/";
    private static final String NOPERMISSION = "/nopermission.jsp";

    //该方法将在请求处理之前被调用,链式调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        uri = uri.substring(1,uri.indexOf('.'));
        if (!uri.equals("login/login") && !uri.equals("login/logout")&& !uri.equals("layout/main")) {
            UserPermission userPermission = (UserPermission) request.getSession().getAttribute(UserPermission.LOGIN_SESSION_INFO);
            if(ObjectUtil.isNotEmpty(userPermission)){
                List<Permission> permissions = userPermission.getPermissions();

                if(!checkPermission(permissions,uri)){
                    response.sendRedirect(NOPERMISSION);
                    return false;
                }

            }else{
                response.sendRedirect(LOGINPAGE);
                return false;
            }
        }
        //拦截器链式调用，此句将继续执行下一个拦截器，若为最后一个，则返回true；
        return true;
    }

    //在当前请求进行处理之后，也就是Controller 方法调用之后执行，但是它会在DispatcherServlet 进行视图返回渲染之前被调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。
     * 这个方法的主要作用是用于进行资源清理工作的。
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request,response,handler,ex);
    }

    private boolean checkPermission(List<Permission> permissions,String uri){
        for (Permission permission : permissions) {
            if (uri.equals(permission.getPath())){
                return true;
            }
            if (permission.getChildPermissions() != null){
                if (checkPermission(permission.getChildPermissions(),uri))
                    return true;
            }
        }
        return false;
    }
}
