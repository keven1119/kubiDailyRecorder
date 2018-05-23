package com.keven.kubi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.keven.kubi.R;
import com.keven.kubi.bean.UserBean;
import com.keven.kubi.dao.LoginDao;
import com.keven.kubi.utils.AssetsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by keven-liang on 2018/4/27.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edittext_name)
    EditText edittextName;
    @BindView(R.id.edittext_pwd)
    EditText edittextPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        UserBean userLogined = new LoginDao().isUserLogined();
        if(userLogined != null){
            Intent intent = new Intent(this, FunctionSelectedActivity.class);
            startActivity(intent);
            finish();
        }else {
            edittextName.setText(new LoginDao().getLoginUserName());
        }

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String login = AssetsUtils.getJson("login", this);
        UserBean userBean = JSON.parseObject(login, UserBean.class);

        String name = edittextName.getText().toString().trim();
        String pwd = edittextPwd.getText().toString().trim();

        if(name.equals(userBean.getUserName()) && pwd.equals(userBean.getUserPwd())){

            UserBean userBean1 = new UserBean();
            userBean1.setUserName(name);
            userBean1.setUserPwd(pwd);

            new LoginDao().saveLoginUser(userBean1);
            Intent intent = new Intent(this, FunctionSelectedActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this,R.string.login_int_fail_tips, Toast.LENGTH_LONG).show();
        }
    }
}
