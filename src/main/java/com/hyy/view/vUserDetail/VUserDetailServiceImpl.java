package com.hyy.view.vUserDetail;

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
public class VUserDetailServiceImpl extends ServiceSupport<VUserDetail> implements VUserDetailService {
    @Autowired
    VUserDetailDao vUserDatailDao;
    @Override
    public Dao<VUserDetail> getDao() {
        return vUserDatailDao;
    }

    public List<VUserDetail> findUserByJobNum(String jobNumber) {
        Criterion criterion = Restrictions.eq("jobNumber", jobNumber);
        return vUserDatailDao.find(criterion);
    }
}
