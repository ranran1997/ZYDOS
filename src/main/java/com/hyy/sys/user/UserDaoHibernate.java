package com.hyy.sys.user;

import com.hyy.base.DaoSupport;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/15  1:49
 */
@Repository
public class UserDaoHibernate extends DaoSupport<User> implements UserDao {



    public List<Integer> findAdminIds() {
        final String hql = "select id from Deliverer where isAdmin = 1";
        return getHibernateTemplate().execute(new HibernateCallback<List<Integer>>() {
            public List<Integer> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                return query.list();
            }
        });
    }




    /*@Override
    public boolean saveOrUpdate(final User deliverer) {
        return getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
            public Boolean doInHibernate(Session session) throws HibernateException {
                session.setFlushMode(FlushMode.COMMIT);
                session.saveOrUpdate(deliverer);
                session.flush();
                return true;
            }
        });
    }*/
}
