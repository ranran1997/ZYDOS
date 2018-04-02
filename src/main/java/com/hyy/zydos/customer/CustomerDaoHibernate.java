package com.hyy.zydos.customer;

import com.hyy.base.DaoSupport;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/25  2:05
 */
@Repository
public class CustomerDaoHibernate extends DaoSupport<Customer> implements CustomerDao {
    public List<Customer> findAllCustomersName() {
        final String hql = "from Customer order by id";
        return getHibernateTemplate().execute(new HibernateCallback<List<Customer>>() {
            public List<Customer> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery("select new Customer (id,name) "+hql);
                return query.list();
            }
        });
    }
}
