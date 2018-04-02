package com.hyy.sys.userRole;

import com.hyy.base.Dao;
import com.hyy.base.Pair;
import com.hyy.base.ServiceSupport;
import com.hyy.sys.role.Role;
import com.hyy.sys.role.RoleService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hengyanyan on 2017/3/7.
 */
@Service
public class UserRoleServiceImpl extends ServiceSupport<UserRole> implements UserRoleService {

    @Autowired
    UserRoleDao dao;

    @Autowired
    RoleService roleService;

    @Override
    public Dao<UserRole> getDao() {
        return dao;
    }

    public List<UserRole> findByUserId(Integer userId) {
        List<UserRole> userRoles = hasSames(new Pair<String, Integer>("userId", userId));
        return userRoles == null ? new ArrayList<UserRole>():userRoles;
    }

    public List<UserRole> findByRoleId(Integer roleId) {
        List<UserRole> userRoles = hasSames(new Pair<String, Integer>("roleId", roleId));
        return userRoles == null ? new ArrayList<UserRole>():userRoles;
    }

    public List<UserRole> findChildUserRoles(Integer userId) {
        List<UserRole> userRoles = this.findByUserId(userId);
        List<Integer> childRoleIds = new ArrayList<Integer>();
        for (UserRole userRole : userRoles) {
            childRoleIds.addAll(roleService.findChildIdsById(userRole.getRoleId()));
        }
        List<UserRole> childUserRoles = dao.find(Restrictions.in("roleId",childRoleIds));

        return childUserRoles;
    }
}
