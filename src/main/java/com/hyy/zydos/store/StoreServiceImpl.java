package com.hyy.zydos.store;

import com.hyy.base.Dao;
import com.hyy.base.ServiceSupport;
import com.hyy.sys.user.User;
import com.hyy.sys.user.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/20  23:36
 */
@Service
public class StoreServiceImpl extends ServiceSupport<Store> implements StoreService{
    @Override
    public Dao<Store> getDao() {
        return dao;
    }
    @Autowired StoreDao dao;

    public List<Store> findStoreByUserId(Integer userId) {
        return dao.find(Restrictions.eq("delivererId", userId));
    }

    public List<Store> findAllStoreByUsers(List<User> users) {
        /*List<Integer> delivererIds = new ArrayList<Integer>();
        for(User deliverer:deliverers){
            delivererIds.add(deliverer.getId());
        }
        return dao.find(Restrictions.in("delivererId", delivererIds));*/

        List<Store> stores = new ArrayList<Store>();
        for(User user:users){
            stores.addAll(this.findStoreByUserId(user.getId()));
        }
        return stores;
    }

    public List<Store> findByUserIds(List<Integer> delivererIds) {
        return dao.find(Restrictions.and(Restrictions.in("delivererId", delivererIds), Restrictions.isNotNull("ownerId")));
    }
}
