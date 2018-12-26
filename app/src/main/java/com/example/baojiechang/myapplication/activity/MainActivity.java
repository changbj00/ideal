package com.example.baojiechang.myapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baojiechang.myapplication.ClassAdapter;
import com.example.baojiechang.myapplication.ClassBaseInfo;
import com.example.baojiechang.myapplication.ClassResult;
import com.example.baojiechang.myapplication.utils.Constant;
import com.example.baojiechang.myapplication.service.HttpResponeCallBack;
import com.example.baojiechang.myapplication.utils.KeyConstance;
import com.example.baojiechang.myapplication.R;
import com.example.baojiechang.myapplication.service.RequestApiData;
import com.example.baojiechang.myapplication.utils.StringUtil;
import com.example.baojiechang.myapplication.utils.UrlConstance;
import com.example.baojiechang.myapplication.UserBaseInfo;
import com.example.baojiechang.myapplication.UserPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements HttpResponeCallBack{

    private Button addClassBtn;
    private ListView listClass;
    private ClassAdapter classAdapter;
    private String userid = UserPreference.read(KeyConstance.IS_USER_ID, null);
    private List<ClassBaseInfo> listBasSectionInfoData=new ArrayList<>();
    private List classid=new ArrayList();
    private List alreadySign=new ArrayList();
    private Constant constant=new Constant();
    int length=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        classAdapter=new ClassAdapter(this,listBasSectionInfoData);


    }

    /**
     * 初始化数据
     */
    private void init() {
        addClassBtn = (Button) findViewById(R.id.addclass_btn);
        listClass = (ListView) findViewById(R.id.all_class);
        showButton();
        getData();
        //点击添加课程
        addClassBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("type", "add");
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, AddClassActivity.class);
                startActivityForResult(intent,0);
            }
        });

        listClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("length", String.valueOf(length));
                if (position==0||position==length+1){
                    Toast.makeText(MainActivity.this, "我是标题", Toast.LENGTH_SHORT).show();
                }else if(position>0&&position<=length){

                    Bundle bundle=new Bundle();
                    Log.e("id", String.valueOf(classid.get(position-1)));
                    bundle.putString("classid", String.valueOf(classid.get(position-1)));
                    bundle.putString("alreadySign", String.valueOf(alreadySign.get(position-1)));
                    Intent intent=new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(MainActivity.this,ItemActivity.class);
                    startActivityForResult(intent,0);
                }else {
                    Bundle bundle=new Bundle();
                    Log.e("id", String.valueOf(classid.get(position-2)));
                    bundle.putString("classid", String.valueOf(classid.get(position-2)));
                    bundle.putString("alreadySign", "");
                    Intent intent=new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(MainActivity.this,ItemActivity.class);
                    startActivityForResult(intent,0);
                }

            }
        });
    }

    private void getData() {

        RequestApiData.getInstance().getClassInfo(userid,MainActivity.this);
    }

    private void showButton(){
        Log.e("case",UserPreference.read(KeyConstance.IS_USER_ROLE,null));
        if (UserPreference.read(KeyConstance.IS_USER_ROLE,null).equals("2")){
            Log.v("我是管理员",UserPreference.read(KeyConstance.IS_USER_ROLE,null));
            addClassBtn.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public void onResponeStart(String apiName) {
        if (UrlConstance.KEY_CLASS_INFO.equals(apiName)||UrlConstance.KEY_ADDCLASS_INFO.equals(apiName)||UrlConstance.KEY_EDITCLASS_INFO.equals(apiName)) {
            Toast.makeText(MainActivity.this, "正在加载数据中", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoading(String apiName, long count, long current) {
        Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String apiName, String response) {

        if (UrlConstance.KEY_CLASS_INFO.equals(apiName)) {
            try {
            JSONObject jsonObject = new JSONObject(response);

//            Log.e("返回数据", response);
            if (jsonObject.getString("code").equals(Constant.KEY_SUCCESS)) {

                    JSONObject newClass = jsonObject.getJSONObject("newClass");
                    JSONArray jsonArray = newClass.getJSONArray("data");
                    ClassBaseInfo newtitle=new ClassBaseInfo(1,"最新课程");
                    listBasSectionInfoData.clear();
                    classid.clear();
                    alreadySign.clear();
                    listBasSectionInfoData.add(newtitle);
                    length=0;
                    length+=jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ClassBaseInfo novels = ClassBaseInfo.sectionInfoData("newClass",jsonArray.getJSONObject(i));

                        if (novels != null) {
                            classid.add(novels.classid);
                            alreadySign.add(novels.alreadySign);
                            listBasSectionInfoData.add(novels);
                        } else {
                            Toast.makeText(MainActivity.this, "数据为空！", Toast.LENGTH_LONG).show();
                        }
                    }
                JSONObject oldClass=jsonObject.getJSONObject("oldClass");
                JSONArray jsonarray=oldClass.getJSONArray("data");
                ClassBaseInfo oldtitle=new ClassBaseInfo(1,"往期课程");
                listBasSectionInfoData.add(oldtitle);
                for (int i=0;i<jsonarray.length();i++){
                    ClassBaseInfo novels=ClassBaseInfo.sectionInfoData("oldClass",jsonarray.getJSONObject(i));
                    if (novels!=null) {
                        classid.add(novels.classid);
                        listBasSectionInfoData.add(novels);
                    }else {
                        Toast.makeText(MainActivity.this, "数据为空！", Toast.LENGTH_LONG).show();
                    }
                }
                }else {
                constant.toast(this,jsonObject.getString("code"),jsonObject.getString("message"));
            }
            } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        listClass.setAdapter(classAdapter);
        classAdapter.notifyDataSetChanged();

        }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
            Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode==0){
                Log.e("mess", "点击返回了");
                getData();
//                classAdapter=new ClassAdapter(this,listBasSectionInfoData);
                classAdapter.setClassList(listBasSectionInfoData);

        }
    }


}

