package com.hyy.view.vStoreUserCustomer;

import com.hyy.base.Dao;
import com.hyy.base.ServiceSupport;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by HengYanYan on 2016/4/30  16:29
 */
@Service
public class VStoreUserCustomerServiceImpl extends ServiceSupport<VStoreUserCustomer> implements vStoreUserCustomerService {
    @Autowired
    VStoreUserCustomerDao userCustomerDao;
    @Override
    public Dao<VStoreUserCustomer> getDao() {
        return userCustomerDao;
    }


    public VStoreUserCustomer findByStoreId(Integer storeId){
        Criterion criterion = Restrictions.eq("id",storeId);
        return userCustomerDao.find(criterion).get(0);
    }
}
