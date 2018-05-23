package com.keven.kubi.dao;

import android.text.TextUtils;

import com.keven.kubi.bean.UserBean;
import com.keven.kubi.utils.SharedPresUtil;

/**
 * Created by keven-liang on 2018/4/30.
 */

public class LoginDao {

    public void saveLoginUser(UserBean userBean){
        SharedPresUtil.getDefault().putString("kubi_user_name", userBean.getUserName());
        SharedPresUtil.getDefault().putString("kubi_user_pwd", userBean.getUserPwd());
    }

    public UserBean isUserLogined(){
        String kubi_user_name = SharedPresUtil.getDefault().getString("kubi_user_name", "");
        String kubi_user_pwd = SharedPresUtil.getDefault().getString("kubi_user_pwd", "");

        if(TextUtils.isEmpty(kubi_user_name) || TextUtils.isEmpty(kubi_user_pwd)){
            return null;
        }

        UserBean userBean = new UserBean();
        userBean.setUserName(kubi_user_name);
        userBean.setUserPwd(kubi_user_pwd);

        return userBean;

    }

    public void clearLoginUser(){
        SharedPresUtil.getDefault().putString("kubi_user_pwd", "");
    }

    public String getLoginUserName(){
        return SharedPresUtil.getDefault().getString("kubi_user_name", "");
    }
}
