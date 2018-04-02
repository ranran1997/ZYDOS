package com.hyy.sys.role;

import com.hyy.base.Dao;

import java.util.List;

/**
 * Created by HengYanYan on 2016/4/26  2:58
 */
public interface RoleDao extends Dao<Role> {
    List<Role> getRoleName();
}
