package com.hyy.zydos.orderInfo;

import com.hyy.base.Dao;
import com.hyy.base.ServiceSupport;
import com.hyy.core.ZYDOS;
import com.hyy.sys.user.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/19  16:42
 */
@Service
public class OrderInfoServiceImpl extends ServiceSupport<OrderInfo> implements OrderInfoService {
    @Autowired
    OrderInfoDao dao;
    @Override
    public Dao<OrderInfo> getDao() {
        return dao;
    }

    /**
     *查询当前用户及其下属所负责的所有未完成订单
     * @param users
     * @return
     */
    public List<OrderInfo> findUndoOrderByUsers(List<User> users) {
        List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
        for(User user:users){
            orderInfos.addAll(this.findUndoOrderInfoByUser(user.getId()));
        }
        return orderInfos;
    }

    /**
     *根据配送员id查询未完成订单
     * @param userId
     * @return
     */
    public List<OrderInfo> findUndoOrderInfoByUser(Integer userId) {
        //已在数据库对应字段上建立索引，提高查询效率
        Criterion[] criterions = new Criterion[2];
        criterions[0] = Restrictions.eq("orderStatus", ZYDOS.OrderStatus.UNDONE);
        criterions[1] = Restrictions.eq("delivererId",userId);
        //按是否紧急和订单拖延时间进行排序
        return dao.find(criterions,Order.desc("isUrgent"),Order.asc("addTime"));
    }

    /**
     * 查询当前用户及其下属所负责的所有订单
     * @param users
     * @return
     */
    public List<OrderInfo> findAllOrderInfoByUsers(List<User> users) {
        List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

        //获取当前用户及其下属属性id集合
        List<Integer> ids = new ArrayList<Integer>();
        for(User user:users){
            ids.add(user.getId());
        }

        //Todo 使用in查询条件将使得数据库进行全表扫描，改用循环id查询？
        Criterion criterion = Restrictions.in("delivererId", ids);
        orderInfos.addAll(dao.find(criterion));

        return orderInfos;
    }

    public Integer findNewAddedUndoOrder(Integer userId, Date time) {
        Criterion[] criterions = new Criterion[3];
        criterions[0] = Restrictions.eq("orderStatus", ZYDOS.OrderStatus.UNDONE);
        criterions[1] = Restrictions.eq("delivererId",userId);
        criterions[2] = Restrictions.ge("addTime", time);

        return dao.find(criterions).size();
    }

    public Integer findDoneOrderNum(Integer userId, Date from) {
        Criterion[] criterions = new Criterion[3];
        criterions[0] = Restrictions.eq("orderStatus", ZYDOS.OrderStatus.DONE);
        criterions[1] = Restrictions.eq("delivererId",userId);
        criterions[2] = Restrictions.ge("deliverTime", from);
        return dao.find(criterions).size();
    }

    public Integer findAllHistoryOrder(Integer userId) {
        return this.findDoneOrderNum(userId,new Date(0));
    }

    public List<OrderInfo> findOrderInfosByUserIds(List<Integer> userIds) {
        return dao.find(Restrictions.in("delivererId",userIds));
    }


}
