package com.example.baojiechang.myapplication.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.baojiechang.myapplication.R;
import com.example.baojiechang.myapplication.activity.AddClassActivity;
import com.example.baojiechang.myapplication.activity.ItemActivity;
import com.example.baojiechang.myapplication.service.RequestApiData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Dialog {
    private DateType type;
    private Context mcontext;
    private String data;

    public Dialog(DateType type,Context mcontext){
        this.type=type;
        this.mcontext=mcontext;
    }

    public String showDatePickDialog(OnSureLisener lisener) {
        DatePickDialog dialog = new DatePickDialog(mcontext);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(type);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(lisener);
        dialog.show();
        return data;
    }

    private void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setMessage("请您再次确认！");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
