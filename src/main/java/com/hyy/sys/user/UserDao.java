package com.hyy.sys.user;

import com.hyy.base.Dao;

import java.util.List;

/**
 * Created by HengYanYan on 2016/4/15  1:49
 */
public interface UserDao extends Dao<User> {


    List<Integer> findAdminIds();


}
