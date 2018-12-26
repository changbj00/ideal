package com.example.baojiechang.myapplication;



import com.example.baojiechang.myapplication.utils.Constant;

import java.util.List;

/**
 * Created by wuxin on 16/9/12.
 */
public class ClassResult {
    private String code = "100";
    private String message;
    private List<String> studentlist;

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

    public List<String> getStudentlist() {
        return studentlist;
    }

    public void setStudentlist(List<String> studentlist) {
        this.studentlist = studentlist;
    }

    public boolean isSuccess() {
        return this.code.equals(Constant.KEY_SUCCESS);
    }
}
