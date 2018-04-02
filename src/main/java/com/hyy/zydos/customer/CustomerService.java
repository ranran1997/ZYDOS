package com.hyy.zydos.customer;

import com.hyy.base.Service;

import javax.jws.WebService;
import java.util.List;


/**
 * Created by HengYanYan on 2016/4/25  2:01
 */
@WebService
public interface CustomerService extends Service<Customer> {
    List<Customer> findAllCustomersName();

    List<Customer> findAllCustomers();
}
