package com.hyy.zydos.orderInfo;

import com.hyy.base.WithOutModelAction;
import com.hyy.base.ZydosResponse;
import com.hyy.config.ViewConfig;
import com.hyy.core.ZYDOS;
import com.hyy.sys.user.UserService;
import com.hyy.utils.StringUtils;
import com.hyy.validator.Validator;
import com.hyy.zydos.store.Store;
import com.hyy.zydos.store.StoreService;
import com.hyy.sys.user.User;
import com.hyy.validator.propertyValidator.Rule;
import com.hyy.validator.propertyValidator.ValidateException;
import com.hyy.view.vUserDetail.VUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by HengYanYan on 2016/4/21  17:22
 */
@Controller
@RequestMapping("orderInfo")
public class OrderInfoAction extends WithOutModelAction {

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    VUserDetailService vUserDetailService;

    Rule rule = Rule.required("storeId","请选择一个店铺");

    @Autowired
    StoreService storeService;

    @Autowired
    UserService userService;

     //订单列表
     //可能存在有些订单关联的店铺和配送员均失效的情况
    @RequestMapping("list")
    public String orderInfoList(Model model,Integer done){

        User user = this.getUserPermission().getCurrentUser();

        List<OrderInfo> orderInfos = orderInfoService.findOrderInfosByUserIds(user.getChildUserIds());

      /*  if(done !=null && done ==0){
            orderInfos = orderInfoService.findUndoOrderInfoByUser(user.getId());
        }
        orderInfos = orderInfoService.findAllOrderInfoByUsers(user.getUnderUsers());*/

        model.addAttribute("orderInfos",orderInfos);
        return forward("list",ViewConfig.mainviews);
    }

    //查询新增未完成待配送的订单数量
    @ResponseBody
    @RequestMapping("undoList")
    public ZydosResponse<Integer> undoList(){

        Date now = new Date();
        User user = this.getUserPermission().getCurrentUser();
        Integer newOrderNum = orderInfoService.findNewAddedUndoOrder(user.getId(), new Date(now.getTime() - 600000));
        Integer newDoneOrderNum = orderInfoService.findDoneOrderNum(user.getId(), new Date(now.getTime() - 600000));

        Integer num = newOrderNum-newDoneOrderNum;
        return ZydosResponse.successResponse(num);
    }

    //新增订单
    @RequestMapping("add")
    public String addOrderInfo(HttpServletRequest request,Model model){
        User user = this.getUserPermission().getCurrentUser();

        List<User> underUsers = user.getChildUsers();

        List<Store> stores = storeService.findAllStoreByUsers(underUsers);

        Map<Integer,String> underUserMap = new HashMap<Integer, String>();
        for(User underUser:underUsers){
            underUserMap.put(underUser.getId(),underUser.getName());
        }

        model.addAttribute("action","save.cc");
        model.addAttribute("stores",stores);
        model.addAttribute("users",underUserMap);
        return forward("add", ViewConfig.mainviews);
    }

    //新增--保存
    @RequestMapping("save")
    public String saveOrderInfo(OrderInfo orderInfo) throws ValidateException {

        //验证数据
        String msg = Validator.validate(orderInfo, rule);
        if(StringUtils.areNotEmpty(msg)) return error(msg);

        //判断storeId是否合法
        Store store = storeService.findById(orderInfo.getStoreId());
        if (store == null) return error("店铺不存在");

        //为避免重复下单，先判断该店铺是否已经存在未完成订单
        List<OrderInfo> undoOrderInfos = orderInfoService.findUndoOrderInfoByUser(store.getDelivererId());
        for(OrderInfo undoOrderInfo:undoOrderInfos){
            if(undoOrderInfo.getStoreId().equals(orderInfo.getStoreId())){
                return error("订单已存在，请勿重复下单");
            }
        }

        //数据补全
        orderInfo.setDelivererId(store.getDelivererId());
        orderInfo.setDelivererName(userService.findById(store.getDelivererId()).getName());
        orderInfo.setStoreName(store.getStoreName());
        orderInfo.setCreater(this.getUserPermission().getCurrentUser().getName());
        orderInfo.setAddTime(new Date(System.currentTimeMillis()));
        orderInfo.setOrderStatus(ZYDOS.OrderStatus.UNDONE);

        orderInfoService.saveOrUpdate(orderInfo);
        return redirect("/orderInfo/list.cc");
    }

    @RequestMapping("modify")
    public String modifyOrderInfo(Integer id,Model model){
        if(id != null){
            OrderInfo orderInfo = orderInfoService.findById(id);
            if(orderInfo != null){
                model.addAttribute("orderInfo",orderInfo);
                model.addAttribute("action","update.cc");
                return forward("edit",ViewConfig.mainviews);
            }
        }
        return error("操作失败，请重试！");
    }

    @RequestMapping("update")
    public String updateOrderInfo(OrderInfo orderInfo){
        //test
//        orderInfo.setUserName("111");
        //redirect是否要写绝对路径
        return redirect("list.cc");
    }

    @RequestMapping("cancel")
    public String cancelOrderInfo(Integer id){
        //判断订单号是否合法
        if(id != null){
            OrderInfo orderInfo = orderInfoService.findById(id);
            if(orderInfo != null){
                orderInfo.setOrderStatus(ZYDOS.OrderStatus.CANCLED);
                orderInfoService.saveOrUpdate(orderInfo);
                return redirect("/orderInfo/list.cc");
            }
        }
        return error("操作失败！请重试！");
    }

    @RequestMapping("done")
    public String doneOrder(Integer id){
        //判断订单号是否合法
        if(id != null){
            OrderInfo orderInfo = orderInfoService.findById(id);
            if(orderInfo != null){
                orderInfo.setOrderStatus(ZYDOS.OrderStatus.DONE);
//                orderInfo.setUserTime(new Date(System.currentTimeMillis()));
                orderInfoService.saveOrUpdate(orderInfo);
                return redirect("/orderInfo/list.cc");
            }
        }
        return error("操作失败！请重试！");
    }
}
