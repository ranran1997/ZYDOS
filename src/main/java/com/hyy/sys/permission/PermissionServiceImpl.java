package com.hyy.sys.permission;

import com.hyy.base.Dao;
import com.hyy.base.Pair;
import com.hyy.base.ServiceSupport;
import com.hyy.sys.rolePermission.RolePermission;
import com.hyy.sys.rolePermission.RolePermissionService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by HengYanYan on 2016/4/26  3:26
 */
@Service
public class PermissionServiceImpl extends ServiceSupport<Permission> implements PermissionService {
    @Autowired
    PermissionDao dao;

    @Autowired
    RolePermissionService rolePermissionService;

    @Override
    public Dao<Permission> getDao() {
        return dao;
    }

    public List<Permission> findByRankIds(List<Integer> rankIds) {
        Criterion criterion = Restrictions.in("rankId",rankIds);
        return dao.find(criterion);
    }

    public List<Permission> getPermissionInOrder(List<Permission> permissionList) {
        if (permissionList == null || permissionList.isEmpty()) return permissionList;

        Map<Integer, Permission> paramListMap = new TreeMap<Integer, Permission>();

        for (Permission permission : permissionList) {
            if (permission.getParentId() == 0)
                paramListMap.put(permission.getOrder(), findChildren(permission, permissionList));
        }

        return new LinkedList<Permission>(paramListMap.values());
    }

    public List<Permission> findByRoleId(Integer roleId) {
        List<Permission> permissions = new ArrayList<Permission>();

        List<RolePermission> rolePermissionList = rolePermissionService.findByRoleId(roleId);
        for (RolePermission rolePermission : rolePermissionList) {
            permissions.add(findById(rolePermission.getPermissionId()));
        }
        return permissions;
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
    }
}
