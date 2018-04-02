package com.hyy.zydos.customerStore;

import com.hyy.base.Dao;
import com.hyy.base.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hengyanyan on 2017/3/13.
 */
@Service
public class CustomerStoreServiceImpl extends ServiceSupport<CustomerStore> implements CustomerStoreService {

    @Autowired
    CustomerStoreDao dao;

    @Override
    public Dao<CustomerStore> getDao() {
        return dao;
    }

    public CustomerStore findByStoreId(Integer storeId) {

        return hasSame("storeId",storeId.toString());
    }
}
