package com.hyy.sys.userRole;

import com.hyy.base.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by hengyanyan on 2017/3/7.
 */
@WebService
public interface UserRoleService extends Service<UserRole> {

    List<UserRole> findByUserId(Integer userId);

    List<UserRole> findByRoleId(Integer roleId);

    List<UserRole> findChildUserRoles(Integer userId);
}
