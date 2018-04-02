package com.hyy.sys.role;

import com.hyy.base.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/26  2:55
 */
@WebService
public interface RoleService extends Service<Role>{
    List<Role> getRoleTree();
    List<Role> findByUserId(Integer userId);
}
