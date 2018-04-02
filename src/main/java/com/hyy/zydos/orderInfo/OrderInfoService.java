package com.hyy.zydos.orderInfo;

import com.hyy.base.Service;
import com.hyy.sys.user.User;

import javax.jws.WebService;
import java.util.Date;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/19  15:39
 */
@WebService
public interface OrderInfoService extends Service<OrderInfo> {

    List<OrderInfo> findUndoOrderInfoByUser(Integer userId);

    //查询当前用户及其下属所负责的所有未完成订单
    List<OrderInfo> findUndoOrderByUsers(List<User> users);

    //查询当前用户及其下属所负责的所有订单
    List<OrderInfo> findAllOrderInfoByUsers(List<User> users);

    //查询当前用户新增订单数
    Integer findNewAddedUndoOrder(Integer userId, Date from);

    //查询当前用户刚刚完成订单数
    Integer findDoneOrderNum(Integer userId, Date from);


    //查询当前用户完成的历史订单数
    Integer findAllHistoryOrder(Integer userId);

    List<OrderInfo> findOrderInfosByUserIds(List<Integer> userIds);
}
