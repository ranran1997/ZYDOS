package com.hyy.view.vstoredelivererclient;

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
public class VStoreDelivererClientServiceImpl extends ServiceSupport<VStoreDelivererClient> implements vStoreDelivererClientService {
    @Autowired VStoreDelivererClientDao delivererClientDao;
    @Override
    public Dao<VStoreDelivererClient> getDao() {
        return delivererClientDao;
    }


    public VStoreDelivererClient findByStoreId(Integer storeId){
        Criterion criterion = Restrictions.eq("id",storeId);
        return delivererClientDao.find(criterion).get(0);
    }
}
