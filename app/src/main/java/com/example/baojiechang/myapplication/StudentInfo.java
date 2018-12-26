package com.example.baojiechang.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentInfo {
    public String name;
    public String status;
    public String uid;
    public Map<String,String> map=new HashMap<>();



    public StudentInfo(String name, String status, String uid) {
        this.name = name;
        this.status = status;
        this.uid = uid;
        map.put("0","正常出勤");
        map.put("1","迟到");
        map.put("2","早退");
        map.put("3","未出勤");
        map.put("4","待确认");
    }

    public static StudentInfo sectionInfoData(JSONObject json){

        try {
                return new StudentInfo(
                        json.getString("name"),
                        json.getString("status"),
                        json.getString("uid"));

        }
        catch (JSONException e)
        { e.printStackTrace(); }
        return null;
    }
}
