package com.example.baojiechang.myapplication;

import java.io.Serializable;

/**
 * @author
 *
 * 解析获取用户基本信息
 */
public class UserBaseInfo implements Serializable{

    //	 {"ret":"0","errcode":"0","msg":"接口调用成功","nickname":"erom","userhead":"/img/users/head/avatar.png",
//	"userid":"11653","email":"123456789@qq.com","role":"0"}
    private String uid;//用户id
    private String email;//用户邮件
    private String code;//请求状态码
    private String message;//错误信息

    private String privilege;//角色 是不是管理员

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
