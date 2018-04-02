package com.hyy.sys.role;

import com.hyy.base.Dao;
import com.hyy.base.ServiceSupport;
import com.hyy.sys.userRole.UserRole;
import com.hyy.sys.userRole.UserRoleService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/26  2:56
 */
@Service
public class RoleServiceImpl extends ServiceSupport<Role> implements RoleService {
    @Autowired
    RoleDao roleDao;

    @Autowired
    UserRoleService userRoleService;

    @Override
    public Dao<Role> getDao() {
        return roleDao;
    }

    public List<Role> getRoleTree() {
        //先找到所有的父亲
        List<Role> roleList = roleDao.find(Restrictions.eq("parentId", 0));
        //遍历获得角色树
        for (Role role : roleList) {
            this.getChildTree(role);
        }
        return roleList;
    }

    private void getChildTree(Role role) {
        Role root = role;
        //getBeanClass();
        //递归遍历查询所有child；
        List<Role> children = this.findChildListByPId(root.getId());
        if (children != null) {
            root.setChildRole(children);
            for (Role childRole : children) {
                this.getChildTree(childRole);
            }
        }
    }

    public List<Role> findByUserId(Integer userId) {

        List<UserRole> userRoles = userRoleService.findByUserId(userId);
        List<Role> roleList = new ArrayList<Role>();

        for (UserRole userRole : userRoles) {
            roleList.add(this.findById(userRole.getRoleId()));
        }

        return roleList;
    }
}
