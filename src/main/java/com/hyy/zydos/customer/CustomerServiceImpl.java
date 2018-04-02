package com.hyy.zydos.customer;

import com.hyy.base.Dao;
import com.hyy.base.ServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HengYanYan on 2016/4/25  2:02
 */
@Service
public class CustomerServiceImpl extends ServiceSupport<Customer> implements CustomerService {

    @Autowired
    CustomerDao dao;
    @Override
    public Dao<Customer> getDao() {
        return dao;
    }

    public List<Customer> findAllCustomersName() {
        return dao.findAllCustomersName();
    }

    public List<Customer> findAllCustomers() {
        return dao.findAll();
    }
}
