package com.hyy.zydos.store;

import com.hyy.base.WithOutModelAction;
import com.hyy.config.ViewConfig;
import com.hyy.sys.user.User;
import com.hyy.sys.user.UserService;
import com.hyy.utils.StringUtils;
import com.hyy.validator.Validator;
import com.hyy.validator.propertyValidator.Rule;
import com.hyy.validator.propertyValidator.ValidateException;
import com.hyy.view.vStoreUserCustomer.vStoreUserCustomerService;
import com.hyy.zydos.customer.Customer;
import com.hyy.zydos.customer.CustomerService;
import com.hyy.zydos.customerStore.CustomerStore;
import com.hyy.zydos.customerStore.CustomerStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by HengYanYan on 2016/4/20  23:33
 */
@Controller
@RequestMapping("store")
public class StoreAction extends WithOutModelAction {

    @Autowired
    StoreService service;
    @Autowired
    UserService userService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerStoreService customerStoreService;
    @Autowired
    vStoreUserCustomerService vStoreUserCustomerService;

    private Rule[] storeRule = new Rule[]{
            Rule.required("storeName", "店铺名不能为空"),
            Rule.required("delivererId", "请选择商铺配送员"),
            Rule.required("address", "店铺地址不能为空"),
            Rule.required("lng", "地址解析错误"),
            Rule.required("lat", "地址解析错误"),
            Rule.length("storeName", 2, 32, "店铺名长度在2到32之间"),

//            Rule.is("ownerMobile", ValidType.MOBILE, "店主手机号格式错误"), Todo 手机号校验。前后台
    };
    private Rule[] customerRule = new Rule[]{
            Rule.required("name", "店主姓名不能为空"),
            Rule.required("mobile1", "店主手机号不能为空"),
            Rule.length("name", 2, 32, "店铺名长度在2到32之间"),
    };

    @RequestMapping("list")
    public String storeList(HttpServletRequest request, Model model) {

        //获取当前用户及其管辖的人负责的商铺列表
        User user = this.getUserPermission().getCurrentUser();

        List<Store> stores = service.findByUserIds(user.getChildUserIds());

        //数据补全 userName ownerName
        for (Store store : stores) {
//            CustomerStore customerStore = customerStoreService.findByStoreId(store.getId());
            Customer customer = customerService.findById(store.getOwnerId());
            if (customer != null)
                store.setOwnerName(customer.getName());

            User user1 = userService.findById(store.getDelivererId());
            if (user1 != null)
                store.setDelivererName(user1.getName());
        }
        model.addAttribute("stores", stores);
        return forward("list", ViewConfig.mainviews);
    }

    /**
     * 新增店铺，该功能为最高权限，直接 查询所有
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("add")
    public String addStore(HttpServletRequest request, Model model) {
        User user = this.getUserPermission().getCurrentUser();

        //获取所有配送员,客户
        List<Customer> customers = customerService.findAllCustomersName();
        List<User> users = user.getChildUsers();

        model.addAttribute("users", users);
        model.addAttribute("adminId", user.getId());
        model.addAttribute("adminName", user.getName());
        model.addAttribute("customers", customers);

        model.addAttribute("action", "save.cc");
        return forward("edit", ViewConfig.mainviews);
    }

    //新建商铺
    @RequestMapping("save")
    public String saveStore(HttpServletRequest request, Store store, Customer newCustomer) throws ValidateException {

        if (store.getOwnerId() == null && !StringUtils.isEmpty(newCustomer.getName())) {
            //后台数据格式校验
            String mes = Validator.validate(store, storeRule);
            if (StringUtils.areNotEmpty(mes)) return error(mes);

            String mes1 = Validator.validate(newCustomer, customerRule);
            if (StringUtils.areNotEmpty(mes)) return error(mes1);

            newCustomer.setAddress(null);
            Integer customerId = customerService.save(new Customer(newCustomer.getName(), newCustomer.getMobile1()));

            //字段补全，店主id，店铺电话
            if (StringUtils.isEmpty(store.getStorePhone()))
                store.setStorePhone(newCustomer.getMobile1());
            store.setOwnerId(customerId);

            service.saveOrUpdate(store);
            //保存进关系表
            customerStoreService.saveOrUpdate(new CustomerStore(customerId, store.getId()));

        } else if (store.getOwnerId() != null && StringUtils.isEmpty(newCustomer.getName())) {
            //后台数据格式校验
            String mes = Validator.validate(store, storeRule);
            if (StringUtils.areNotEmpty(mes)) return error(mes);
            if (store.getOwnerId() == null) return error("店主名不能为空");

            //保存至已有客户
            Customer customer = customerService.findById(store.getOwnerId());
            if (customer != null) {
                //字段补全,店铺电话
                if (StringUtils.isEmpty(store.getStorePhone()))
                    store.setStorePhone(customer.getMobile1());

                service.saveOrUpdate(store);
                //保存进关系表
                customerStoreService.save(new CustomerStore(customer.getId(), store.getId()));
            } else
                return error("店主不存在");
        } else
            return error("保存失败");
        return redirect("list.cc");
    }

    //unchecked
    @RequestMapping("modify")
    public String modifyStore(Integer id, Model model) {
        if (id != null) {
            Store store = service.findById(id);
            if (store != null) {
                model.addAttribute("store", store);
                model.addAttribute("action", "update.cc");
                return forward("edit", ViewConfig.mainviews);
            }
        }
        return error("该商铺不存在，请重试！");
    }

    //Todo unDone
    @RequestMapping("update")
    public String updateStore(Store store) {
        //test
        store.setDelivererName("111");
        return null;
    }

    //unchecked
    @RequestMapping("delete")
    public String deleteStore(Integer id) {
        //验证数据是否合法
        if (id != null && service.findById(id) != null) {
            //Todo 数据库内容实际未删除，why?
            service.delete(id);
            return redirect("list.cc");
        }
        return error("操作失败！请返回重试！");

    }
}
