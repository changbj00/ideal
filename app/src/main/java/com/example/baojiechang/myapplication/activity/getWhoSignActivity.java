package com.example.baojiechang.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.baojiechang.myapplication.CardTypeEnum;
import com.example.baojiechang.myapplication.R;
import com.example.baojiechang.myapplication.StudentAdapter;
import com.example.baojiechang.myapplication.StudentInfo;
import com.example.baojiechang.myapplication.service.HttpResponeCallBack;

import com.example.baojiechang.myapplication.service.RequestApiData;
import com.example.baojiechang.myapplication.utils.Constant;
import com.example.baojiechang.myapplication.utils.UrlConstance;
import com.luxiaochun.multiselectiondialog.DialogType;
import com.luxiaochun.multiselectiondialog.MultiSelectionDialogManager;
import com.luxiaochun.multiselectiondialog.base.Node;
import com.luxiaochun.multiselectiondialog.listener.OnClickListener;
import com.luxiaochun.multiselectiondialog.listener.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class getWhoSignActivity extends AppCompatActivity implements HttpResponeCallBack{

    private StudentAdapter studentAdapter;
    private List<StudentInfo> liststudents=new ArrayList<>();
    private ListView listStudent;
    private ImageView back;
    private List uid=new ArrayList();
    private Constant constant=new Constant();
    private String classid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_get_who_sign);
        Bundle bundle=getIntent().getExtras();
        classid=bundle.getString("classid");
        init();
        studentAdapter=new StudentAdapter(this,liststudents);
    }

    private void init() {
        listStudent = (ListView) findViewById(R.id.all_student);
        listStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new MultiSelectionDialogManager
                        .Builder()
                        //当前Activity
                        .setActivity(getWhoSignActivity.this)
                        .setTitle("考勤类型")
                        .setCanceledOnTouchOutside(true)
                        .setDatas(CardTypeEnum.getDatas())
                        .setType(DialogType.SINGLE)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onClick(Node node, int id) {
                                String status=String.valueOf(node.getId());
                                String Uid= String.valueOf(uid.get(position));
                                Log.e("xuanze", status);
                                RequestApiData.getInstance().updateSignStatus(classid,Uid,status,getWhoSignActivity.this);

                            }
                        })
                        .build().show();
            }
        });

        back=(ImageView) findViewById(R.id.doctor_iv_return);
        back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED,(new Intent()).setAction(classid));
                finish();
            }
        });
        getdata();

    }

    private void getdata(){
        RequestApiData.getInstance().getWhoSign(classid,getWhoSignActivity.this);
    }

    @Override
    public void onResponeStart(String apiName) {

    }

    @Override
    public void onLoading(String apiName, long count, long current) {

    }

    @Override
    public void onSuccess(String apiName, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String code=jsonObject.getString("code");
            String message=jsonObject.getString("message");
        if (UrlConstance.KEY_get_WhoSign.equals(apiName)) {
                if (code.equals(Constant.KEY_SUCCESS)){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    liststudents.clear();
                    uid.clear();
                    if (jsonArray.length()==0){
                        Log.i("data","数据为空！");
                        Toast.makeText(getWhoSignActivity.this, "暂无数据！", Toast.LENGTH_LONG).show();
                    }else {
                        for (int i = 0; i < jsonArray.length(); i++){
                            StudentInfo novels=StudentInfo.sectionInfoData(jsonArray.getJSONObject(i));
                            if (novels!=null){
                                Log.i("data","数据不为空！");
                                uid.add(novels.uid);
                                liststudents.add(novels);
                            }else {
                                Log.i("data","数据为空！");
                                Toast.makeText(getWhoSignActivity.this, "暂无数据！", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                }else {
                    constant.toast(this,code,message);
                }

            }else if (UrlConstance.KEY_updateSign_Status.equals(apiName)){
            if (code.equals(Constant.KEY_SUCCESS)){
                getdata();
                constant.toast(this,code,message);
            }else {
                constant.toast(this,code,message);
            }

        }
            listStudent.setAdapter(studentAdapter);
            studentAdapter.notifyDataSetChanged();
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
        Toast.makeText(getWhoSignActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
}
