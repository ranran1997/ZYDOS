package com.hyy.sys.user;

import com.hyy.base.WithOutModelAction;
import com.hyy.config.ViewConfig;
import com.hyy.sys.permission.Permission;
import com.hyy.sys.role.RoleService;
import com.hyy.zydos.orderInfo.OrderInfoService;
import com.hyy.zydos.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 出于安全性考虑，不将用户密码查询出来
 * Created by HengYanYan on 2016/4/15  1:46
 */
@Controller
@RequestMapping("user")
public class UserAction extends WithOutModelAction{
    @Autowired
    UserService service;

    @Autowired
    RoleService roleService;

    @Autowired
    StoreService storeService;

    @Autowired
    OrderInfoService orderInfoService;

//    @Autowired
//    VUserDetailService vDelivererDetailService;

 /*   Rule[] rules = {
            Rule.required("name","员工姓名不能为空！"),
            Rule.length("name", 2, 16, "员工姓名长度在2到16之间"),
            Rule.required("sex","请指定员工性别"),
            Rule.required("mobile","员工手机号不能为空"),
            Rule.length("mobile",11,11,"手机号长度为11位"),
            Rule.required("roleId","请选择员工职位"),
            Rule.required("jobNumber","请设置员工登录账号！"),
            Rule.required("password","密码不能为空"),
            Rule.length("jobNumber",2,16,"工号长度在2到16位之间"),
            Rule.length("password", 5, 16, "密码长度在5到16位之间")
    };*/

    @RequestMapping("list")
     public String delivererList(HttpServletRequest request, Model model){

        List<Integer> childIds = this.getUserPermission().getCurrentUser().getChildUserIds();

        List<User> childUsers = service.findByIds(childIds);
        List<Permission> childPermissions = this.getchildPermission(request.getRequestURI());

        model.addAttribute("childUsers",childUsers);
        model.addAttribute("childPermissions",childPermissions);

        return  forward("list", ViewConfig.sysviews);
    }

    //modified checked
  /*  @RequestMapping("add")
    public String addDeliverer(Model model){
        //获取角色列表
        List<Role> roleList = roleService.getRoleTree();
        model.addAttribute("roles",roleList);
        model.addAttribute("action","save.cc");
        return  forward("edit", ViewConfig.mainviews);
    }

    @RequestMapping("save")
    public String saveDeliverer(HttpServletRequest request,User user) throws ValidateException {
        //后台数据校验
        String msg = Validator.validate(user,rules);
        if(StringUtils.isNotEmpty(msg)) return error(msg);

        //验证数据合法性 roleId
        if(roleService.findById(user.getRoleId()) == null) return error("数据有误，请返回重试！");

        //Todo 判断账号唯一性，应在页面即时判断
        if(vDelivererDetailService.findDelivererByJobNum(user.getJobNumber()).size() > 0) return error("该账号已被占用");

        //数据补全
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setJoinTime(new Date(System.currentTimeMillis()));

        service.saveOrUpdate(user);

        //Todo modified unchecked
        //因为我将登陆者下属已存放在session中，此时更新session
        User currentUser = this.getUserPermission().getCurrentUser();
        List<Permission> permissionList = this.getUserPermission().getPermissions();
        request.getSession().setAttribute(UserPermission.LOGIN_SESSION_INFO,new UserPermission(currentUser,permissionList));

        return  redirect("list.cc");
    }

    //Todo
    @RequestMapping("modify")
    public String modifyDeliverer(Model model){

        model.addAttribute("action","update.cc");
        return  forward("edit", ViewConfig.mainviews);
    }

    //Todo
    @RequestMapping("update")
    public String updateDeliverer(User user){

        return  redirect("list.cc");
    }

    @RequestMapping("delete")
    public String deleteDeliverer(Integer id){

        if(id != null && service.findById(id) != null){
            //Todo 在删除之前，先将被删除的配送员负责的店铺的配送设为该配送员上级，或者当前管理员
            //service.delete(id);
            return  redirect("list.cc");
        }
        return  error("操作失败，请返回重试！");
    }

    @RequestMapping("profile")
    public String profile(Model model){

        User user = this.getUserPermission().getCurrentUser();
        List<Store> stores = storeService.findAllStoreByDeliverers(user.getUnderUsers());
        VDelivererDetail delivererDetail = vDelivererDetailService.findById(user.getId());

        model.addAttribute("deliverer",delivererDetail);
        model.addAttribute("storeNum",stores.size());
        model.addAttribute("orderNum",orderInfoService.findAllHistoryOrder(user.getId()));
        return  forward("profile",ViewConfig.mainviews);
    }*/
}
