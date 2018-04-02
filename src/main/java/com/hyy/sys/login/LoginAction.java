package com.hyy.sys.login;

import com.hyy.base.WithOutModelAction;
import com.hyy.base.ZydosResponse;
import com.hyy.config.ViewConfig;
import com.hyy.sys.permission.Permission;
import com.hyy.sys.permission.PermissionService;
import com.hyy.sys.rank.RankService;
import com.hyy.sys.role.Role;
import com.hyy.sys.role.RoleService;
import com.hyy.sys.rolePermission.RolePermission;
import com.hyy.sys.rolePermission.RolePermissionService;
import com.hyy.sys.user.User;
import com.hyy.sys.user.UserService;
import com.hyy.sys.userRole.UserRole;
import com.hyy.sys.userRole.UserRoleService;
import com.hyy.utils.ObjectUtil;
import com.hyy.utils.StringUtils;
import com.hyy.validator.Validator;
import com.hyy.validator.propertyValidator.Rule;
import com.hyy.validator.propertyValidator.ValidateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by HengYanYan on 2016/4/14  15:37
 */
@Controller
@RequestMapping("/login")
public class LoginAction extends WithOutModelAction {

    private static final Short FIRSTLOGIN = 1;
    private static final Short NOTFIRSTLOGIN = 0;
    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    RankService rankService;

    @Autowired
    PermissionService permissionService;

    protected final Log logger = LogFactory.getLog(getClass());

    Rule[] rules = {
            Rule.required("code", "请输入职工号！"),
            Rule.required("password", "密码不能为空"),
            Rule.length("code", 2, 16, "工号长度在2到16位之间"),
            Rule.length("password", 5, 16, "密码长度在5到16位之间")
    };

    /*@responsebody表示该方法的返回结果直接写入HTTP response body中
一般在异步获取数据时使用，在使用@RequestMapping后，返回值通常解析为跳转路径，
加上@responsebody后返回结果不会被解析为跳转路径，而是直接写入HTTP response body中。
比如异步获取json数据，加上@responsebody后，会直接返回json数据。*/

    @ResponseBody
    @RequestMapping("login")
    public ZydosResponse<Short> login(User user, String validateCode, HttpServletRequest request) {
        try {
            String message = Validator.validate(user, rules);
            if (!StringUtils.isEmpty(message))
                return ZydosResponse.failResponse(message);
        } catch (ValidateException e) {
            this.logger.error(e);
            return ZydosResponse.failResponse("登陆验证失败,请重试。");
        }

        user = userService.userLoginCheck(user.getCode(), user.getPassword());

        if (ObjectUtil.isEmpty(user))
            return ZydosResponse.failResponse("账号或密码错误，请重新登录");
        //用户存在
        Short data = NOTFIRSTLOGIN;
        if (user.getFirstLogin() == FIRSTLOGIN) {
            data = FIRSTLOGIN;
            user.setFirstLogin(NOTFIRSTLOGIN);
        }
        userService.saveOrUpdate(user);

        //获取登录用户个人及权限信息，存入session
        List<UserRole> childUserRoles = userRoleService.findChildUserRoles(user.getId());
        List<Integer> childUserIds = new ArrayList<Integer>();
        for (UserRole childUserRole : childUserRoles) {
            childUserIds.add(childUserRole.getUserId());
        }
        List<RolePermission> rolePermissionList = rolePermissionService.findByUserId(user.getId());

        UserPermission userPermission = new UserPermission(user,childUserIds,rolePermissionList);

        //将要存入session的信息封装成类，方便获取
        this.getSession().setAttribute(UserPermission.LOGIN_SESSION_INFO,userPermission);

        return ZydosResponse.successResponse(data);
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, Model model) {

        User user = this.getUserPermission().getCurrentUser();

        model.addAttribute("name", user.getName());
        model.addAttribute("code", user.getCode());
        model.addAttribute("image", user.getImage());
        return forward("loginLock", ViewConfig.sysviews);
    }

}




