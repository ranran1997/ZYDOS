package com.hyy.sys.rolePermission;

import com.hyy.base.Dao;
import com.hyy.base.Pair;
import com.hyy.base.ServiceSupport;
import com.hyy.sys.role.Role;
import com.hyy.sys.role.RoleService;
import com.hyy.sys.userRole.UserRole;
import com.hyy.sys.userRole.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hengyanyan on 2017/3/7.
 */
@Service
public class RolePermissionServiceImpl extends ServiceSupport<RolePermission> implements RolePermissionService {

    @Autowired
    RolePermissionDao dao;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    @Override
    public Dao<RolePermission> getDao() {
        return dao;
    }

    public List<RolePermission> findByUserId(Integer userId) {

        Set<RolePermission> rolePermissionList = new HashSet<RolePermission>();

        List<UserRole> userRoles = userRoleService.findByUserId(userId);

        for (UserRole userRole : userRoles) {
            rolePermissionList.addAll(this.findByRoleId(userRole.getRoleId()));
        }

        return new ArrayList<RolePermission>(rolePermissionList);
    }

    public List<RolePermission> findByRoleId(Integer roleId){
        return hasSames(new Pair<Object, Object>("roleId", roleId));
    }
}
