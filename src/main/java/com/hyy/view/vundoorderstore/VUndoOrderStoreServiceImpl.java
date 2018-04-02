package com.hyy.view.vundoorderstore;

import com.hyy.base.Dao;
import com.hyy.base.ServiceSupport;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HengYanYan on 2016/5/1  14:10
 */
@Service
public class VUndoOrderStoreServiceImpl extends ServiceSupport<VUndoOrderStore> implements VUndoOrderStoreService {

    @Autowired VUndoOrderStoreDao dao;

    @Override
    public Dao<VUndoOrderStore> getDao() {
        return dao;
    }

    public List<VUndoOrderStore> findUndoOrderInfoByDeliver(Integer delivererId) {
        return dao.find(Restrictions.eq("delivererId",delivererId));
    }
}
