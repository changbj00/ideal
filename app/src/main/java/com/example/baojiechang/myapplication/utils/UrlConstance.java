package com.example.baojiechang.myapplication.utils;

/**
 * @author itlanbao
 * 处理网络的参数常量类
 */
public class UrlConstance {


    public static final String APP_URL = "http://192.168.180.16:8044/ideal/";

    //登录用户接口
    public static final String KEY_LOGIN_INFO ="userLogin.do";

    //获取课程基本信息
    public static final String KEY_CLASS_INFO ="getClassList.do";

    //获取课程详细信息
    public static final String KEY_CLASS_Details_INFO ="getClassDetails.do";

    //添加课程接口
    public static final String KEY_ADDCLASS_INFO ="addClass.do";

    //修改课程接口
    public static final String KEY_EDITCLASS_INFO ="editClass.do";

    //更改出勤状态
    public static final String KEY_updateSign_Status ="updateSignStatus.do";

    //获取已报名人员
    public static final String KEY_get_WhoSign = "getWhoSign.do";

    //报名课程接口
    public static final String KEY_SINGNIN_CLASS ="userSignIn.do";

    //取消报名接口
    public static final String KEY_SIGNOUT_CLASS ="userSignOut.do";
}
