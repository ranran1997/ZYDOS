package com.hyy.view.vdelivererdetail;

import com.hyy.base.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/30  20:20
 */
@WebService
public interface VDelivererDetailService extends Service<VDelivererDetail> {

    List<VDelivererDetail> findDelivererByJobNum(String jobNumber);
}
