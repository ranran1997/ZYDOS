package com.hyy.sys.user;


import com.hyy.base.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/15  1:47
 */
@WebService
public interface UserService extends Service<User> {
    User userLoginCheck(String code, String password);

    List<Integer> findAdminIds();
    Boolean updateUser(String jobNumber, String password);
    List<User> findUserByRole(Integer roleId);

}
