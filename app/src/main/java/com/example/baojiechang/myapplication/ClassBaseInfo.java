package com.example.baojiechang.myapplication;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 课程列表信息
 */
public class ClassBaseInfo {
    public Integer type=0;
    public String titleText;
    public String classid;//医生ID
    public String imgurl;//头像
    public String title;//姓名
    public String teacher;//工号
    public String address;//电话
    public String startTime;//地址
    public String endTime;//科室名称
    public String alreadySign;

    public Integer getType() {
        return type;
    }

    public String getTitleText() {
        return titleText;
    }

    public ClassBaseInfo(String classid, String imgurl, String title, String teacher, String startTime, String endTime, String address,String alreadySign) {
        this.classid = classid;
        this.imgurl = imgurl;
        this.title = title;
        this.teacher = teacher;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.alreadySign=alreadySign;}

    public ClassBaseInfo(String classid, String imgurl, String title, String teacher, String startTime, String endTime, String address) {
        this.classid = classid;
        this.imgurl = imgurl;
        this.title = title;
        this.teacher = teacher;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime; }

    public ClassBaseInfo(int type,String titleText) {
        this.type=type;
        this.titleText=titleText;
    }

    public static ClassBaseInfo sectionInfoData(String type,JSONObject json){

        try {
            if (type.equals("oldClass")){
                return new ClassBaseInfo(
                        json.getString("classid"),
                        json.getString("imgurl"),
                        json.getString("title"),
                        json.getString("teacher"),
                        json.getString("startTime"),
                        json.getString("endTime"),
                        json.getString("address"));
            }else if (type.equals("newClass")){
                return new ClassBaseInfo(
                        json.getString("classid"),
                        json.getString("imgurl"),
                        json.getString("title"),
                        json.getString("teacher"),
                        json.getString("startTime"),
                        json.getString("endTime"),
                        json.getString("address"),
                        json.getString("alreadySign"));
            }
            }
        catch (JSONException e)
        { e.printStackTrace(); }
        return null;
    }
}
