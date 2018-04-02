package com.hyy.zydos.customer;

import com.hyy.base.Dao;

import java.util.List;

/**
 * Created by HengYanYan on 2016/4/25  2:04
 */
public interface CustomerDao extends Dao<Customer> {
    List<Customer> findAllCustomersName();
}
