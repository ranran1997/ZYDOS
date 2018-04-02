package com.hyy.view.vStoreUserCustomer;

import com.hyy.base.Service;

import javax.jws.WebService;

/**
 * Created by HengYanYan on 2016/4/30  16:03
 */
@WebService
public interface vStoreUserCustomerService extends Service<VStoreUserCustomer> {
    VStoreUserCustomer findByStoreId(Integer storeId);
}
