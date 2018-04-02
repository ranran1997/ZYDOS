package com.hyy.sys.layout;

import com.hyy.base.WithOutModelAction;
import com.hyy.config.ViewConfig;
import com.hyy.sys.user.User;
import com.hyy.zydos.orderInfo.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by HengYanYan on 2016/4/16  17:25
 */
@Controller
@RequestMapping("layout")
public class LayoutAction extends WithOutModelAction{

    @Autowired
    OrderInfoService orderInfoService;

    @RequestMapping("main")
    public String main(HttpServletRequest request,Integer first,Model model){

        User user = this.getUserPermission().getCurrentUser();

        model.addAttribute("model",user);
        if(first != null){
            model.addAttribute("firstLogin",first);
        }
        //获取对应未完成订单
        //Integer num = orderInfoService.findNewAddedUndoOrder(user.getId(),new Date(0));
//        List<OrderInfo> undoOrders = orderInfoService.findUndoOrderInfoByDeliver(user.getId());
//        model.addAttribute("undoOrders",undoOrders);
        return forward("main", ViewConfig.sysviews);
    }
}
