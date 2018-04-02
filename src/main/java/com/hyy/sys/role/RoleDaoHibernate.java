package com.hyy.sys.role;

import com.hyy.base.DaoSupport;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/26  2:59
 */
@Repository
public class RoleDaoHibernate extends DaoSupport<Role> implements RoleDao {
    public List<Role> getRoleName() {
        final String hql = "from Role";
        return getHibernateTemplate().execute(new HibernateCallback<List<Role>>() {
            public List<Role> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery("select new Role(id,name) "+hql);
                return query.list();
            }
        });
    }
}
