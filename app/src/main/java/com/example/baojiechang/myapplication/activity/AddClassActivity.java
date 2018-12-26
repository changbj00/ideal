package com.example.baojiechang.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.baojiechang.myapplication.ClassInfo;
import com.example.baojiechang.myapplication.ImagePickerAdapter;
import com.example.baojiechang.myapplication.R;
import com.example.baojiechang.myapplication.service.HttpResponeCallBack;
import com.example.baojiechang.myapplication.service.RequestApiData;
import com.example.baojiechang.myapplication.utils.Constant;
import com.example.baojiechang.myapplication.utils.Dialog;
import com.example.baojiechang.myapplication.utils.GlideImageLoader;
import com.example.baojiechang.myapplication.utils.SelectDialog;
import com.example.baojiechang.myapplication.utils.UrlConstance;
import com.example.baojiechang.myapplication.utils.Utils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

public class AddClassActivity extends Activity implements ImagePickerAdapter.OnRecyclerViewItemClickListener,HttpResponeCallBack {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private EditText title;
    private EditText teacher;
    private EditText address;
    private EditText content;
    private Button startTime;
    private Button endTime;
    private Button add_class;
    private ImageView back;
    private RecyclerView recyclerView;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 1;//允许选择图片最大数
    private ArrayList<ImageItem> images;
    private HashMap<String,String> parameter=new HashMap<>();
    private String image;
    private String type;
    private String classid;
    private ClassInfo classInfo;
    private Constant constant=new Constant();
    private Utils utils=new Utils();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        Bundle bundle=getIntent().getExtras();
        type=bundle.getString("type");

        if (type.equals("add")){
            Log.e("type",type);
            ButterKnife.bind(this);
            init();
            initImagePicker();
            initWidget();
        }else if (type.equals("edit")){
            Log.e("type",type);
            classid=bundle.getString("classid");
            ButterKnife.bind(this);
            getdata(classid);
            init();
            initImagePicker();
            initWidget();
        }

    }

    private void init() {

        final Dialog dialog=new Dialog(DateType.TYPE_ALL,this);
        title=(EditText) findViewById(R.id.title_text);
        teacher=(EditText) findViewById(R.id.teacher_text);
        address=(EditText) findViewById(R.id.address_text);
        content=(EditText) findViewById(R.id.content_text);
        startTime=(Button)findViewById(R.id.start_time);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.showDatePickDialog(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String mdata=sd.format(date);
                        startTime.setText(mdata);

                    }
                });

            }
        });
        endTime=(Button) findViewById(R.id.end_time);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.showDatePickDialog(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String mdata=sd.format(date);
                        endTime.setText(mdata);

                    }
                });

            }
        });
        add_class=(Button) findViewById(R.id.add_class);
        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_data=title.getText().toString();
                String teacher_data=teacher.getText().toString();
                String address_data=address.getText().toString();
                String content_data=content.getText().toString();
                String starttime_data=startTime.getText().toString();
                String endtime_data=endTime.getText().toString();
                image=adapter.base64;
                if (TextUtils.isEmpty(title_data)){
                    Toast.makeText(AddClassActivity.this, "请输入课程标题！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(teacher_data)){
                    Toast.makeText(AddClassActivity.this, "请输入课程讲师！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(address_data)){
                    Toast.makeText(AddClassActivity.this, "请输入课程地点！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(content_data)){
                    Toast.makeText(AddClassActivity.this, "请输入课程详情！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(starttime_data)){
                    Toast.makeText(AddClassActivity.this, "请输入开始时间！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(endtime_data)){
                    Toast.makeText(AddClassActivity.this, "请输入结束时间！", Toast.LENGTH_SHORT).show();
                }else if (image==null){
                    Toast.makeText(AddClassActivity.this, "请您更新图片！", Toast.LENGTH_SHORT).show();
                }else {
                    parameter.put("title",title_data);
                    parameter.put("teacher",teacher_data);
                    parameter.put("address",address_data);
                    parameter.put("content",content_data);
                    parameter.put("startTime",starttime_data);
                    parameter.put("endTime",endtime_data);
                    parameter.put("base64",image);
                    if (type.equals("add")){
                    RequestApiData.getInstance().addClass(parameter,AddClassActivity.this);
                    }else if (type.equals("edit")){
                        parameter.put("classid",classid);
                        RequestApiData.getInstance().editClass(parameter,AddClassActivity.this);
                    }
                }

            }
        });
        back=findViewById(R.id.doctor_iv_return);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED,(new Intent()).setAction("1"));
                finish();
            }
        });
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private void initWidget() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }
    private void getdata(String classid){
        RequestApiData.getInstance().getClassBaseInfo(classid,AddClassActivity.this);
    }
    private void setdata(final ClassInfo classInfo){
        TextView top=findViewById(R.id.tv_headline);
        top.setText("编辑课程");
        title.setText(classInfo.title);
        teacher.setText(classInfo.teacher);
        startTime.setText(classInfo.startTime);
        endTime.setText(classInfo.endTime);
        address.setText(classInfo.address);
        content.setText(classInfo.content);
//

    }
    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }
    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                /**
                                 * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                                 *
                                 * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                                 *
                                 * 如果实在有所需要，请直接下载源码引用。
                                 */
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(AddClassActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);

                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(AddClassActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);

                                break;
                            default:
                                break;
                        }

                    }
                }, names);


                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);

                }
                image=adapter.base64;
                Log.e("xxxx","DDD:"+adapter.base64);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);

                }
                image=adapter.base64;
            }
        }
    }

    @Override
    public void onResponeStart(String apiName) {
        if (UrlConstance.KEY_ADDCLASS_INFO.equals(apiName)||UrlConstance.KEY_ADDCLASS_INFO.equals(apiName)||UrlConstance.KEY_EDITCLASS_INFO.equals(apiName)) {
            Toast.makeText(AddClassActivity.this, "正在加载数据中", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoading(String apiName, long count, long current) {
        Toast.makeText(AddClassActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String apiName, String response) {
        Log.e("添加课程",response);
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(response);
            String code=jsonObject.getString("code");
            String message=jsonObject.getString("message");
            if (UrlConstance.KEY_ADDCLASS_INFO.equals(apiName)){
                if (code.equals(Constant.KEY_SUCCESS)){
                    constant.toast(this,code,message);
                    Bundle bundle=new Bundle();
                    bundle.putString("classid", classid);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(AddClassActivity.this, MainActivity.class);
                    startActivityForResult(intent,0);
                    finish();

                }else {
                    constant.toast(this,code,message);
                }

            }else if (UrlConstance.KEY_EDITCLASS_INFO.equals(apiName)){
                if (code.equals(Constant.KEY_SUCCESS)){
                    constant.toast(this,code,message);
                    Bundle bundle=new Bundle();
                    bundle.putString("classid", classid);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(AddClassActivity.this, MainActivity.class);
                    startActivityForResult(intent,0);
                    finish();

                }else {
                    constant.toast(this,code,message);
                }

            }else if (UrlConstance.KEY_CLASS_Details_INFO.equals(apiName)){
                if (code.equals(Constant.KEY_SUCCESS)){
                    Log.e("info",response);
                    JSONObject data=jsonObject.getJSONObject("data");
                    classInfo=ClassInfo.sectionInfoData(data);
                    Log.e("image",classInfo.imgUrl);

                        ImageItem imageItem =new ImageItem("",classInfo.imgUrl,1L,0,0,"",0L);
                        selImageList.add(imageItem);
                        Log.e("images", String.valueOf(selImageList));
                        if (selImageList!=null) {

                            adapter.setImages(selImageList);

                        }

                    setdata(classInfo);

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
        Toast.makeText(AddClassActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
