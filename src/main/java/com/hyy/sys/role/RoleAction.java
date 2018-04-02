package com.hyy.sys.role;

import com.hyy.base.WithOutModelAction;
import com.hyy.config.ViewConfig;
import com.hyy.sys.permission.Permission;
import com.hyy.sys.permission.PermissionService;
import com.hyy.sys.rolePermission.RolePermission;
import com.hyy.sys.rolePermission.RolePermissionService;
import com.hyy.sys.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hengyanyan on 2017/3/19.
 */
@Controller
@RequestMapping("role")
public class RoleAction extends WithOutModelAction {

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    RolePermissionService rolePermissionService;

    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model){
        User user = this.getUserPermission().getCurrentUser();
        List<Permission> childPermissions = this.getchildPermission(request.getRequestURI());

        Role role = roleService.findById(user.getId());
        List<Role> childRoles =  roleService.findChildrenById(role.getId());


        model.addAttribute("childPermissions",childPermissions);
        model.addAttribute("childRoles",childRoles);

        return  forward("list", ViewConfig.sysviews);
    }

    @RequestMapping("permissionSet")
    public String permissionSet(Integer id, HttpServletRequest request, Model model){

        Role role = roleService.findById(id);
        List<RolePermission> rolePermissions = rolePermissionService.findByRoleId(role.getId());
        List<Integer> permissionIds = new ArrayList<Integer>();
        for (RolePermission rolePermission : rolePermissions) {
            permissionIds.add(rolePermission.getPermissionId());
        }

        List<Permission> permissions = permissionService.findAll();
        for (Permission permission : permissions) {
            if (permissionIds.contains(permission.getId()))
                permission.setSelected(1);
        }
        permissions = permissionService.getPermissionInOrder(permissions);

        model.addAttribute("role",role);
        model.addAttribute("permissions",permissions);

        return  forward("permissionSet", ViewConfig.sysviews);
    }
}
