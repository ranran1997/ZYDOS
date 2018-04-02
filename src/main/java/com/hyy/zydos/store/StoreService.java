package com.hyy.zydos.store;

import com.hyy.base.Service;
import com.hyy.sys.user.User;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/20  23:33
 */
@WebService
public interface StoreService extends Service<Store> {

    List<Store> findStoreByUserId(Integer userId);

    //获取当前登录者及其下属所负责的所有店铺信息
    List<Store> findAllStoreByUsers(List<User> users);

    List<Store> findByUserIds(List<Integer> userIds);
}
