package com.hyy.sys.permission;

import com.hyy.base.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/26  3:26
 */
@WebService
public interface PermissionService extends Service<Permission> {

    List<Permission> findByRankIds(List<Integer> rankIds);

    List<Permission> getPermissionInOrder(List<Permission> permissionList);

    List<Permission> findByRoleId(Integer roleId);

}
