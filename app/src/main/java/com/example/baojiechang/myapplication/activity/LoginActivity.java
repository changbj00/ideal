package com.example.baojiechang.myapplication.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baojiechang.myapplication.utils.Constant;
import com.example.baojiechang.myapplication.service.HttpResponeCallBack;
import com.example.baojiechang.myapplication.ItLanBaoApplication;
import com.example.baojiechang.myapplication.utils.KeyConstance;
import com.example.baojiechang.myapplication.R;
import com.example.baojiechang.myapplication.service.RequestApiData;
import com.example.baojiechang.myapplication.utils.UrlConstance;
import com.example.baojiechang.myapplication.UserBaseInfo;
import com.example.baojiechang.myapplication.UserPreference;
import com.google.gson.Gson;

public class LoginActivity extends Activity implements HttpResponeCallBack {

    private EditText loginAccount;//账号
    private EditText loginPassword;//密码
    private Button loginBtn;
    private Gson gson=new Gson();
    private Constant constant=new Constant();

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_login);
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        loginAccount = (EditText) findViewById(R.id.login_account);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
//        registerBtn = (Button) findViewById(R.id.register_btn);
        //点击登录按钮
        loginBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String account = loginAccount.getText().toString();//账号
                String password = loginPassword.getText().toString();//密码
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {

                    RequestApiData.getInstance().getLoginData(account, password,LoginActivity.this);
                } else {
                    Toast.makeText(LoginActivity.this, "账号或者密码有误", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        registerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                LoginActivity.this.startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    public void onResponeStart(String apiName) {
        // TODO Auto-generated method stub

        if (UrlConstance.KEY_LOGIN_INFO.equals(apiName)) {
            Toast.makeText(LoginActivity.this, "正在加载数据中", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoading(String apiName, long count, long current) {
        // TODO Auto-generated method stub
        Toast.makeText(LoginActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String apiName, String response) {
        // TODO Auto-generated method stub
        Object object=gson.fromJson(response,UserBaseInfo.class);
        if (UrlConstance.KEY_LOGIN_INFO.equals(apiName)) {
            //邮箱登录返回数据
            if (object != null && object instanceof UserBaseInfo) {
                UserBaseInfo info = (UserBaseInfo) object;
                if (info.getCode().equals(Constant.KEY_SUCCESS)) {

                    //登录成功，保存登录信息
                    ItLanBaoApplication.getInstance().setBaseUser(info);//保存到Application中

                    //保存到SP中
                    UserPreference.save(KeyConstance.IS_USER_ID, String.valueOf(info.getUid()));
                    UserPreference.save(KeyConstance.IS_USER_ROLE,String.valueOf(info.getPrivilege()));
                    UserPreference.save(KeyConstance.IS_USER_ACCOUNT, info.getEmail());
                    UserPreference.save(KeyConstance.IS_USER_PASSWORD, loginPassword.getText().toString());


                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right);
                    finish();

                } else {
                    Log.e("TAG", "info="+info.toString());
//                    if (info.getCode().equals(Constant.KEY_FAILURE)) {
//                        Toast.makeText(LoginActivity.this, info.getMessage(), Toast.LENGTH_SHORT).show();
//                    }else if(info.getCode().equals(Constant.KEY_NO_EMAIL)){
//                        Toast.makeText(LoginActivity.this, info.getMessage(), Toast.LENGTH_SHORT).show();
//                    }else if(info.getCode().equals(Constant.KEY_EXIST_EMAIL)){
//                        Toast.makeText(LoginActivity.this, info.getMessage(), Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(LoginActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
//                        Log.e("TAG", "info.getMsg()="+info.getMessage());
//                    }
                    constant.toast(this,info.getCode(),info.getMessage());
                }
            }
        }

    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo,
                          String strMsg) {
        // TODO Auto-generated method stub
        Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
    }
}
