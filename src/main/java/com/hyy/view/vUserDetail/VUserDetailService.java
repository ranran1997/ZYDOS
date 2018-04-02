package com.hyy.view.vUserDetail;

import com.hyy.base.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/30  20:20
 */
@WebService
public interface VUserDetailService extends Service<VUserDetail> {

    List<VUserDetail> findUserByJobNum(String jobNumber);
}
