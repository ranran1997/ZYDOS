package com.hyy.sys.rank;

import com.hyy.base.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/26  3:06
 */
@WebService
public interface RankService extends Service<Rank> {
    List<Integer> findChildRankById(Integer rankId);
}
