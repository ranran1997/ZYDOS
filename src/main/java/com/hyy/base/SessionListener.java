package com.hyy.base;

import com.hyy.sys.login.UserPermission;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现单点登录,暂时不用
 * Created by HengYanYan on 2016/4/17  21:13
 */
public class SessionListener implements HttpSessionAttributeListener {

    public static final String LISENERNAME = "loginUser";
    //存放所有当前登录账户id及session
    private static Map<Integer,HttpSession> loginUsers = new HashMap<Integer, HttpSession>();

    public void attributeAdded(HttpSessionBindingEvent hsbe) {
        //若session中增添了用户登录，
        if(hsbe.getName().equals(LISENERNAME)){

            UserPermission userPermission = (UserPermission) hsbe.getValue();

            if(getLoginUsers().isEmpty()){
                loginUsers.put(userPermission.getCurrentUser().getId(),hsbe.getSession());
            }else{
                    attributeReplaced(hsbe);
            }
        }

    }

    //session失效时
    public void attributeRemoved(HttpSessionBindingEvent hsbe) {

        if(hsbe.getName().equals(LISENERNAME)){
            UserPermission userPermission = (UserPermission) hsbe.getValue();
            if(loginUsers.containsKey(userPermission.getCurrentUser().getId()))
                loginUsers.remove(userPermission.getCurrentUser().getId());
        }
    }



    public void attributeReplaced(HttpSessionBindingEvent hsbe) {

        if(hsbe.getName().equals(LISENERNAME)){
            UserPermission userPermission = (UserPermission) hsbe.getValue();
            if(loginUsers.containsKey(userPermission.getCurrentUser().getId()))
                loginUsers.replace(userPermission.getCurrentUser().getId(),hsbe.getSession());
        }

    }


    public Map<Integer, HttpSession> getLoginUsers() {
        return loginUsers;
    }

}
