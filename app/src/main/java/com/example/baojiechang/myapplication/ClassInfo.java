package com.example.baojiechang.myapplication;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 课程信息
 */
public class ClassInfo {
    public Integer classid;//医生ID
    public String imgUrl;//头像
    public String title;//姓名
    public String teacher;//工号
    public String address;//电话
    public String startTime;//地址
    public String endTime;//地址
    public String content;//科室名称
    public String SignCount;//已报名


    public ClassInfo(Integer classid, String imgUrl, String title, String teacher, String address, String startTime, String endTime, String content, String SignCount) {
        this.classid = classid;
        this.imgUrl = imgUrl;
        this.title = title;
        this.teacher = teacher;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
        this.SignCount = SignCount; }

    public static ClassInfo sectionInfoData(JSONObject json){
        try {
            return new ClassInfo( json.getInt("classid"),
                    json.getString("imgUrl"),
                    json.getString("title"),
                    json.getString("teacher"),
                    json.getString("address"),
                    json.getString("startTime"),
                    json.getString("endTime"),
                    json.getString("content"),
                    json.getString("SignCount")); }
        catch (JSONException e)
        { e.printStackTrace(); }
        return null;
    }
}
