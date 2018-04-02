package com.hyy.zydos.customerStore;

import com.hyy.base.Service;

import javax.jws.WebService;

/**
 * Created by hengyanyan on 2017/3/13.
 */
@WebService
public interface CustomerStoreService extends Service<CustomerStore> {

    CustomerStore findByStoreId(Integer storeId);
}
