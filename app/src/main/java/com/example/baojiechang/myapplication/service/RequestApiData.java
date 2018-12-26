package com.example.baojiechang.myapplication.service;

import android.util.Log;

import com.example.baojiechang.myapplication.utils.UrlConstance;

import java.util.HashMap;

public class RequestApiData {
    private static RequestApiData instance = null;
    private HttpResponeCallBack mCallBack = null;

    //创建接口对象
    public static RequestApiData getInstance() {
        if (instance == null) {
            instance = new RequestApiData();
        }
        return instance;
    }

    /**
     * 4.8登录用户接口
     * @param email 邮箱
     * @param password 密码

     * @param callback 回调
     * 特别要注意参数位置不能变要根据文档来
     * 请求方式：POST
     */
    public void getLoginData(String email ,String password,
                             HttpResponeCallBack callback) {
        mCallBack = callback;
        //这是每一个接口的唯一标示
        String tagUrl = UrlConstance.KEY_LOGIN_INFO;//登录接口
        HashMap<String, String> parameter = new HashMap<String, String>();
        parameter.put("email", email);
        parameter.put("password", password);

        //拼接参数信息，邮箱，密码，公钥，并用md5进行加密
        StringBuilder builder = new StringBuilder();
        builder.append(email);
        builder.append(password);
//        builder.append(UrlConstance.PUBLIC_KEY);

//        parameter.put(UrlConstance.ACCESSTOKEN_KEY,MD5Util.getMD5Str(builder.toString()));

        //请求数据接口
        RequestManager.post(UrlConstance.APP_URL,tagUrl, parameter, mCallBack);

    }
    //课程列表
    public  void getClassInfo(String uid, HttpResponeCallBack callback){
        mCallBack=callback;
        String tagurl=UrlConstance.KEY_CLASS_INFO;//获取课程信息
        HashMap<String, String> parameter = new HashMap<String, String>();
        parameter.put("uid", uid);
        StringBuilder builder = new StringBuilder();
        builder.append(uid);
        Log.e("request",String.valueOf(parameter));
        RequestManager.post(UrlConstance.APP_URL,tagurl,parameter,mCallBack);

    }
    //课程信息
    public void getClassBaseInfo(String classid,HttpResponeCallBack callBack){
        mCallBack=callBack;
        String tagurl=UrlConstance.KEY_CLASS_Details_INFO;//获取课程信息
        HashMap<String, String> parameter = new HashMap<String, String>();
        parameter.put("classid", classid);
        StringBuilder builder = new StringBuilder();
        builder.append(classid);
        Log.e("request",String.valueOf(parameter));
        RequestManager.post(UrlConstance.APP_URL,tagurl,parameter,mCallBack);
    }
    //报名
    public void SignIn(String classid,String uid,HttpResponeCallBack callBack){
        mCallBack=callBack;
        String tagurl=UrlConstance.KEY_SINGNIN_CLASS;//报名课程
        HashMap<String, String> parameter = new HashMap<String, String>();
        parameter.put("classid", classid);
        parameter.put("uid",uid);
        StringBuilder builder = new StringBuilder();
        builder.append(classid);
        builder.append(uid);
        Log.e("request",String.valueOf(parameter));
        RequestManager.post(UrlConstance.APP_URL,tagurl,parameter,mCallBack);
    }
    //取消报名
    public void SignOut(String classid,String uid,HttpResponeCallBack callBack){
        mCallBack=callBack;
        String tagurl=UrlConstance.KEY_SIGNOUT_CLASS;//取消报名课程
        HashMap<String, String> parameter = new HashMap<String, String>();
        parameter.put("classid", classid);
        parameter.put("uid",uid);
        StringBuilder builder = new StringBuilder();
        builder.append(classid);
        builder.append(uid);
        Log.e("request",String.valueOf(parameter));
        RequestManager.post(UrlConstance.APP_URL,tagurl,parameter,mCallBack);
    }
    //添加课程
    public void addClass(HashMap<String,String> parameter, HttpResponeCallBack callback){
        mCallBack=callback;
        String tagurl=UrlConstance.KEY_ADDCLASS_INFO;//增加课程
//
//        parameter.put("startTime",startTime);
//        parameter.put("endTime",endTime);
//        parameter.put("teacher",teacher);
//        parameter.put("title",title);
//        parameter.put("content",content);
//        parameter.put("address",address);

        StringBuilder builder = new StringBuilder();
        builder.append(parameter.get("startTime"));
        builder.append(parameter.get("endTime"));
        builder.append(parameter.get("teacher"));
        builder.append(parameter.get("title"));
        builder.append(parameter.get("content"));
        builder.append(parameter.get("address"));
        builder.append(parameter.get("base64"));

        //请求数据接口
        RequestManager.post(UrlConstance.APP_URL,tagurl, parameter, callback);

    }
    //修改课程
    public void editClass(HashMap<String,String> parameter, HttpResponeCallBack callback){
        mCallBack=callback;
        String tagurl=UrlConstance.KEY_EDITCLASS_INFO;//增加课程
//        HashMap<String,String> parameter=new HashMap<>();
//        parameter.put("classid",classid);
//        parameter.put("startTime",startTime);
//        parameter.put("endTime",endTime);
//        parameter.put("teacher",teacher);
//        parameter.put("title",title);
//        parameter.put("content",content);
//        parameter.put("address",address);

        StringBuilder builder = new StringBuilder();
        builder.append(parameter.get("classid"));
        builder.append(parameter.get("startTime"));
        builder.append(parameter.get("endTime"));
        builder.append(parameter.get("teacher"));
        builder.append(parameter.get("title"));
        builder.append(parameter.get("content"));
        builder.append(parameter.get("address"));
        builder.append(parameter.get("base64"));

        //请求数据接口
        RequestManager.post(UrlConstance.APP_URL,tagurl, parameter,callback);
    }
    //更新状态
    public void updateSignStatus(String classid,String uid,String status,HttpResponeCallBack callBack){
        mCallBack=callBack;
        String tagurl=UrlConstance.KEY_updateSign_Status;//更改出勤状态
        HashMap<String, String> parameter = new HashMap<String, String>();
        parameter.put("classid", classid);
        parameter.put("uid",uid);
        parameter.put("status",status);
        StringBuilder builder = new StringBuilder();
        builder.append(classid);
        builder.append(uid);
        builder.append(status);
        Log.e("request",String.valueOf(parameter));
        RequestManager.post(UrlConstance.APP_URL,tagurl,parameter,mCallBack);
    }
    //获取报名人数
    public void getWhoSign(String classid,HttpResponeCallBack callBack){
        mCallBack=callBack;
        String tagurl=UrlConstance.KEY_get_WhoSign;//获取已报名人员
        HashMap<String, String> parameter = new HashMap<String, String>();
        parameter.put("classid", classid);
        StringBuilder builder = new StringBuilder();
        builder.append(classid);
        Log.e("request",String.valueOf(parameter));
        RequestManager.post(UrlConstance.APP_URL,tagurl,parameter,mCallBack);
    }
}
