package com.hyy.zydos.customer;

import com.hyy.base.WithOutModelAction;
import com.hyy.config.ViewConfig;
import com.hyy.sys.user.User;
import com.hyy.zydos.store.Store;
import com.hyy.zydos.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by HengYanYan on 2016/4/25  1:57
 */
@Controller
@RequestMapping("customer")
public class CustomerAction extends WithOutModelAction{

    @Autowired
    CustomerService customerService;

    @Autowired
    StoreService storeService;

    @RequestMapping("list")
    public String clientsList(HttpServletRequest request, Model model){

        User deliverer = this.getUserPermission().getCurrentUser();
        //查询
        List<Store> stores = storeService.findAllStoreByUsers(deliverer.getChildUsers());
        //店主与店铺是一对多关系，Set保证不重复
        Set<Integer> clientIds = new HashSet<Integer>();
        for(Store store:stores){
            clientIds.add(store.getOwnerId());
        }
        List<Customer> clients = customerService.findByIds(new ArrayList<Integer>(clientIds));

        model.addAttribute("clients",clients);
        return forward("list", ViewConfig.mainviews);
    }
    @RequestMapping("modify")
    public String modifyClient(Integer id,Model model){
        if(id != null){
            Customer client = customerService.findById(id);
            if(client != null){
                model.addAttribute("client",client);
                model.addAttribute("action","update.cc");
                return forward("edit",ViewConfig.mainviews);
            }
        }
        return error("操作失败，请重试！");
    }

    @RequestMapping("update")
    public String updateClient(Customer client){
        //Todo 已经在数据表上设置级联关系，被更新客户所对应的商铺将同时被更新
        client.setName("111");
        return null;
    }

    @RequestMapping("delete")
    public String deleteClient(Integer id){
        if(id != null && customerService.findById(id) != null){
            //Todo 已经在数据表上设置级联关系，被删除客户所对应的商铺将同时被删除
            customerService.delete(id);
            return redirect("list.cc");
        }else
        return error("操作失败，请返回重试！");
    }
}
