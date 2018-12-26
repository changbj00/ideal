package com.example.baojiechang.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.baojiechang.myapplication.ClassInfo;
import com.example.baojiechang.myapplication.R;
import com.example.baojiechang.myapplication.UserPreference;
import com.example.baojiechang.myapplication.service.HttpResponeCallBack;
import com.example.baojiechang.myapplication.service.RequestApiData;
import com.example.baojiechang.myapplication.utils.Constant;
import com.example.baojiechang.myapplication.utils.KeyConstance;
import com.example.baojiechang.myapplication.utils.UrlConstance;
import com.example.baojiechang.myapplication.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemActivity extends Activity implements HttpResponeCallBack{
    public String classid;
    public String alreadySign;
    public ClassInfo classInfo;
    private Constant constant=new Constant();
    private String userid = UserPreference.read(KeyConstance.IS_USER_ID, null);
    private String privilege=UserPreference.read(KeyConstance.IS_USER_ROLE,null);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Bundle bundle=getIntent().getExtras();
        classid=bundle.getString("classid");
        alreadySign=bundle.getString("alreadySign");
        Log.e("classone",classid);
        findData();

    }


    private void findData() {
        RequestApiData.getInstance().getClassBaseInfo(classid,ItemActivity.this);
    }


    public void inDate(final ClassInfo classInfo){
        ImageView back=(ImageView) findViewById(R.id.doctor_iv_return);
        ImageView imgUrl=(ImageView) findViewById(R.id.image);
        TextView title=(TextView) findViewById(R.id.doctor_tv_doctorName);
        TextView teacher=(TextView) findViewById(R.id.doctor_tv_sectionName);
        TextView startTime=(TextView) findViewById(R.id.doctor_tv_sectionIntro1);
        TextView endTime=(TextView) findViewById(R.id.doctor_tv_sectionIntro4);
        TextView address=(TextView) findViewById(R.id.doctor_tv_sectionIntro3);
        TextView content=(TextView) findViewById(R.id.doctor_tv_sectionIntro2);
        TextView SignCount=(TextView) findViewById(R.id.join);
        Button signin=(Button) findViewById(R.id.signin_btn);
        Button signout=(Button) findViewById(R.id.signout_btn);
        Button edit=(Button) findViewById(R.id.edit_btn);
        Button getWhoSign=(Button) findViewById(R.id.getWhoSign_btn);
        if (alreadySign.equals("0")&&privilege.equals("1")){
            signin.setVisibility(View.VISIBLE);
            signout.setVisibility(View.GONE);

        }else if (alreadySign.equals("1")&&privilege.equals("1")){
            signin.setVisibility(View.GONE);
            signout.setVisibility(View.VISIBLE);
        }else if (alreadySign.equals("")){

        }else {
            signin.setVisibility(View.GONE);
            signout.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            getWhoSign.setVisibility(View.VISIBLE);

        }
        back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED,(new Intent()).setAction(classid));
                finish();
            }
        });

        signin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog(1);
            }
        });

        signout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(2);
            }
        });
        edit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("type", "edit");
                bundle.putString("classid", classid);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(ItemActivity.this, AddClassActivity.class);
                startActivityForResult(intent,0);

            }
        });
        getWhoSign.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("classid", classid);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(ItemActivity.this, getWhoSignActivity.class);
                startActivityForResult(intent,0);

            }
        });
        loadImage(classInfo.imgUrl,imgUrl);
        title.setText(classInfo.title);
        teacher.setText(classInfo.teacher);
        startTime.setText(classInfo.startTime);
        endTime.setText(classInfo.endTime);
        address.setText(classInfo.address);
        content.setText(classInfo.content);
        SignCount.setText(classInfo.SignCount);


    }

    public void loadImage(String picUrl,ImageView ivShow){
        Glide.with(this)
                .load(picUrl)
                //设置占位图
                .placeholder(R.mipmap.ic_launcher)
                //加载错误图
                .error(R.mipmap.ic_launcher)
                //磁盘缓存的处理
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivShow);
    }

    private void dialog(final int flag){
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
        builder.setMessage("请您再次确认！");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (flag==1){
                    RequestApiData.getInstance().SignIn(classid,userid,ItemActivity.this);
                }else if (flag==2){
                    RequestApiData.getInstance().SignOut(classid,userid,ItemActivity.this);
                }

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
    @Override
    public void onResponeStart(String apiName) {
        if (UrlConstance.KEY_CLASS_Details_INFO.equals(apiName)) {
            Toast.makeText(ItemActivity.this, "正在加载数据中", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoading(String apiName, long count, long current) {
        Toast.makeText(ItemActivity.this, "Loading...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSuccess(String apiName, String response) {
        Log.e("课程信息",response);
        try {
            JSONObject jsonObject=new JSONObject(response);
            String code=jsonObject.getString("code");
            String message=jsonObject.getString("message");
        if (UrlConstance.KEY_CLASS_Details_INFO.equals(apiName)){
            if (code.equals(Constant.KEY_SUCCESS)){
                Log.e("info",response);
                JSONObject data=jsonObject.getJSONObject("data");
                classInfo=ClassInfo.sectionInfoData(data);
                inDate(classInfo);

            }else {
                constant.toast(this,code,message);
            }

        }else if (UrlConstance.KEY_SINGNIN_CLASS.equals(apiName)){
            if (code.equals(Constant.KEY_SUCCESS)){
                classInfo.SignCount= String.valueOf(Integer.parseInt(classInfo.SignCount)+1);
                alreadySign="1";
                inDate(classInfo);
                constant.toast(this,code,message);
            }else {
                constant.toast(this,code,message);
            }
        }else if (UrlConstance.KEY_SIGNOUT_CLASS.equals(apiName)){
            if (code.equals(Constant.KEY_SUCCESS)){
                classInfo.SignCount= String.valueOf(Integer.parseInt(classInfo.SignCount)-1);
                alreadySign="0";
                inDate(classInfo);
                constant.toast(this,code,message);
            }else {
                constant.toast(this,code,message);
            }
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
        Toast.makeText(ItemActivity.this, "获取失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode==0){

                Log.e("mess", "点击返回了");
                findData();
//                classAdapter=new ClassAdapter(this,listBasSectionInfoData);
//                classAdapter.setClassList(listBasSectionInfoData);

        }
    }
    /**
     * 监听Back键按下事件
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("按下了back键   onBackPressed()");
        setResult(RESULT_CANCELED,(new Intent()).setAction("1"));
        finish();
    }
/*
    *//* *//**//**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     *//*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            setResult(RESULT_CANCELED,(new Intent()).setAction(classid));
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("执行 onDestroy()");
    }*/
}
