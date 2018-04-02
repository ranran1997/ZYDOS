package com.hyy.view.vstoredelivererclient;

import com.hyy.base.Service;

import javax.jws.WebService;

/**
 * Created by HengYanYan on 2016/4/30  16:03
 */
@WebService
public interface vStoreDelivererClientService extends Service<VStoreDelivererClient> {
    VStoreDelivererClient findByStoreId(Integer storeId);
}
