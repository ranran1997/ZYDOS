package com.hyy.view.vundoorderstore;

import com.hyy.base.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by HengYanYan on 2016/5/1  14:09
 */
@WebService
public interface VUndoOrderStoreService extends Service<VUndoOrderStore> {
    List<VUndoOrderStore> findUndoOrderInfoByDeliver(Integer delivererId);
}
