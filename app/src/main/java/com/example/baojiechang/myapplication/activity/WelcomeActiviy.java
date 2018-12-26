package com.example.baojiechang.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 欢迎界面
 */
public class WelcomeActiviy extends Activity implements HttpResponeCallBack {

    private ImageView iv;
    private Gson gson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_activiy);

        iv = (ImageView) this.findViewById(R.id.logo);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(2000);
        iv.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            /**
             * 动画结束时判断是否保存有登录的信息
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                //暂时用用户名密码登录
                String userAccount = UserPreference.read(KeyConstance.IS_USER_ACCOUNT, null);//软件还没有保持账号
                String userPassword = UserPreference.read(KeyConstance.IS_USER_PASSWORD, null);
                String userid = UserPreference.read(KeyConstance.IS_USER_ID, null);

                if (TextUtils.isEmpty(userAccount)) {//没有保存的登录信息跳转到登录界面
                    //空的，表示没有注册，或者清除数据
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActiviy.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();
                } else {
                    //用保存的信息直接登录
                    RequestApiData.getInstance().getLoginData(userAccount, userPassword,
                             WelcomeActiviy.this);

                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onResponeStart(String apiName) {

    }

    @Override
    public void onLoading(String apiName, long count, long current) {
        Toast.makeText(WelcomeActiviy.this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String apiName, String response) {
        //当前接口是否是获取用户的基本信息的接口
        Object object=gson.fromJson(response,UserBaseInfo.class);
        if (UrlConstance.KEY_CLASS_INFO.equals(apiName)) {

            if (object != null && object instanceof UserBaseInfo ) {


                UserBaseInfo info = (UserBaseInfo) object;
                ItLanBaoApplication.getInstance().setBaseUser(info);//把数据放入到Application里面，全局
                UserPreference.save(KeyConstance.IS_USER_ID, String.valueOf(info.getUid()));

                Intent intent = new Intent();
                intent.setClass(WelcomeActiviy.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                finish();

            } else {
                Toast.makeText(WelcomeActiviy.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        } else if (UrlConstance.KEY_LOGIN_INFO.equals(apiName)) {//当前接口是登录的接口
            //登录返回数据
            if (object != null && object instanceof UserBaseInfo) {
                UserBaseInfo info = (UserBaseInfo) object;
                if (Constant.KEY_SUCCESS.equals(info.getCode())) {

                    ItLanBaoApplication.getInstance().setBaseUser(info);//将用户信息保存在Application中
                    UserPreference.save(KeyConstance.IS_USER_ID, String.valueOf(info.getUid()));

                    Intent intent = new Intent();
                    intent.setClass(WelcomeActiviy.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right);
                    finish();

                } else {
                    Toast.makeText(WelcomeActiviy.this, info.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
        Toast.makeText(WelcomeActiviy.this, "Failure", Toast.LENGTH_SHORT).show();
    }
}
