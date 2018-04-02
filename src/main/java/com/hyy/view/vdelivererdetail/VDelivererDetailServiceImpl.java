package com.hyy.view.vdelivererdetail;

import com.hyy.base.Dao;
import com.hyy.base.ServiceSupport;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HengYanYan on 2016/4/30  20:20
 */
@Service
public class VDelivererDetailServiceImpl extends ServiceSupport<VDelivererDetail> implements VDelivererDetailService {
    @Autowired
    VDelivererDetailDao vDelivererDatailDao;
    @Override
    public Dao<VDelivererDetail> getDao() {
        return vDelivererDatailDao;
    }

    public List<VDelivererDetail> findDelivererByJobNum(String jobNumber) {
        Criterion criterion = Restrictions.eq("jobNumber", jobNumber);
        return vDelivererDatailDao.find(criterion);
    }
}
