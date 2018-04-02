package com.hyy.sys.login;

import com.hyy.sys.permission.PermissionService;
import com.hyy.sys.role.Role;
import com.hyy.sys.rolePermission.RolePermission;
import com.hyy.sys.rolePermission.RolePermissionService;
import com.hyy.sys.user.User;
import com.hyy.sys.user.UserService;
import com.hyy.sys.permission.Permission;
import com.hyy.sys.role.RoleService;
import com.hyy.sys.userRole.UserRole;
import com.hyy.sys.userRole.UserRoleService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import java.util.*;

/**
 * Created by HengYanYan on 2016/4/26  16:32
 */
@Component
public class UserPermission {

    public static final String LOGIN_SESSION_INFO = "LOGINSESSIONINFO";
    private User currentUser;
    private List<Permission> permissions;

    public UserPermission(){

    }

    public UserPermission(User user,List<Integer> childUserIds,List<RolePermission> RolePermissionList) {

        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        PermissionService permissionService = context.getBean(PermissionService.class);
        UserService userService = context.getBean(UserService.class);

        user.setChildUserIds(childUserIds);
        user.setChildUsers(userService.findByIds(childUserIds));

        List<Permission> permissionList = new ArrayList<Permission>();
        for (RolePermission rolePermission : RolePermissionList) {
            permissionList.add(permissionService.findById(rolePermission.getPermissionId()));
        }
        this.currentUser = user;
//        this.permissions = setPermissionLev(permissionList);
        this.permissions = permissionService.getPermissionInOrder(permissionList);


    }

  /*  private List<Permission> setPermissionLev(List<Permission> permissionList) {
        if (permissionList == null || permissionList.isEmpty()) return permissionList;

        Map<Integer, Permission> paramListMap = new TreeMap<Integer, Permission>();

        for (Permission permission : permissionList) {
            if (permission.getParentId() == 0)
                paramListMap.put(permission.getOrder(), findChildren(permission, permissionList));
        }

        return new LinkedList<Permission>(paramListMap.values());
    }

    private  Permission findChildren(Permission permission, List<Permission> permissions){
        Map<Integer, Permission> paramListMap = new TreeMap<Integer, Permission>();

        for (Permission permission1 : permissions) {
            if (permission1.getParentId().equals(permission.getId()) && permission1.getStatus() == 1){
                findChildren(permission1, permissions);
                paramListMap.put(permission1.getOrder(),permission1);
            }

        }

        permission.setChildPermissions(new LinkedList<Permission>(paramListMap.values()));

        return permission;
    }*/

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
