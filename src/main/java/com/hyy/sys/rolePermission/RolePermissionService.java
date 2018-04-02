package com.hyy.sys.rolePermission;

import com.hyy.base.Service;
import com.hyy.sys.permission.Permission;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by hengyanyan on 2017/3/7.
 */
@WebService
public interface RolePermissionService extends Service<RolePermission> {

    List<RolePermission> findByUserId(Integer userId);

    List<RolePermission> findByRoleId(Integer roleId);
}
