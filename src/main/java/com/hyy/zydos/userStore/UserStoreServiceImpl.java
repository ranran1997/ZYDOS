package com.hyy.zydos.userStore;

import com.hyy.base.Dao;
import com.hyy.base.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hengyanyan on 2017/3/13.
 */
@Service
public class UserStoreServiceImpl extends ServiceSupport<UserStore> implements UserStoreService {

    @Autowired
    UserStoreDao dao;

    @Override
    public Dao<UserStore> getDao() {
        return dao;
    }
}
