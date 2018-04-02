package com.hyy.sys.rank;

import com.hyy.base.Dao;
import com.hyy.base.ServiceSupport;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by HengYanYan on 2016/4/26  3:07
 */
@Service
public class RankServiceImpl extends ServiceSupport<Rank> implements RankService {
    @Autowired
    RankDao rankDao;

    @Override
    public Dao<Rank> getDao() {
        return rankDao;
    }

    public List<Integer> findChildRankById(Integer rankId) {
        List<Integer> rankIds = new ArrayList<Integer>();

        Rank rootRank = rankDao.findById(rankId);
        //采用深度优先搜索用栈（stack）策略遍历查询所有childRankId；
        Stack<Rank> rankStack = new Stack<Rank>();
        rankStack.push(rootRank);
        while (!rankStack.empty()) {
            Rank rank = rankStack.pop();
            rankIds.add(rank.getId());
            List<Rank> childRanks = this.findChildByPId(rank.getId());
            for (Rank childRank : childRanks) {
                rankStack.push(childRank);
            }
        }
        return rankIds;
    }

    private List<Rank> findChildByPId(Integer parentId) {
        Criterion criterion = Restrictions.eq("parentId", parentId);
        return rankDao.find(criterion);
    }
}
