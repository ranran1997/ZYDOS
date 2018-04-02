package com.hyy.sys.user;

import com.hyy.base.Dao;
import com.hyy.base.Pair;
import com.hyy.base.ServiceSupport;
import com.hyy.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/15  1:48
 */
@Service
public class UserServiceImpl extends ServiceSupport<User> implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Dao getDao() {
        return userDao;
    }

    public User userLoginCheck(String code, String password) {
        return  this.hasSame(new Pair<String, String>("code",code),new Pair<String, String>("password", DigestUtils.md5Hex(password)));

//        List<Criterion> criterionList = new ArrayList<Criterion>();
//
//        if (!StringUtils.isEmpty(code))
//            criterionList.add(Restrictions.eq("code", code));
//        if (!StringUtils.isEmpty(password))
//            criterionList.add(Restrictions.eq("password", DigestUtils.md5Hex(password)));
//        Criterion[] criterions = new Criterion[criterionList.size()];
//        criterionList.toArray(criterions);
//        List<User> users = userDao.find(criterions);
//        if (users.size() > 0)
//            return users.get(0);
//        else
//            return null;
    }

    public List<Integer> findAdminIds() {
        return userDao.findAdminIds();
    }


    public Boolean updateUser(String jobNumber, String password) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        if (!StringUtils.isEmpty(jobNumber))
            criterionList.add(Restrictions.eq("jobNumber", jobNumber));
        Criterion[] criterions = new Criterion[criterionList.size()];
        criterionList.toArray(criterions);
        User user = userDao.find(criterions).get(0);
        user.setPassword(DigestUtils.md5Hex(password));
        return userDao.saveOrUpdate(user);
    }

    public List<User> findUserByRole(Integer roleId) {
        Criterion criterion =Restrictions.eq("roleId",roleId);
        return userDao.find(criterion);
    }


}
