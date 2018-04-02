package com.hyy.sys.permission;

import com.hyy.base.WithOutModelAction;
import com.hyy.config.ViewConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by hengyanyan on 2017/3/13.
 */
@Controller
@RequestMapping("permission")
public class PermissionAction extends WithOutModelAction {

    @Autowired
    PermissionService service;

    @RequestMapping("list")
    public String list(Model model){
        List<Permission> permissions = service.findAll();
        model.addAttribute("permissions",permissions);
        return forward("list", ViewConfig.sysviews);
    }
}
