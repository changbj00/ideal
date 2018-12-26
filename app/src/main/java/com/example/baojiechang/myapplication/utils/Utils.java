package com.example.baojiechang.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.baojiechang.myapplication.ImagePickerAdapter;
import com.example.baojiechang.myapplication.R;
import com.example.baojiechang.myapplication.activity.AddClassActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.baojiechang.myapplication.activity.AddClassActivity.IMAGE_ITEM_ADD;
import static com.example.baojiechang.myapplication.activity.AddClassActivity.REQUEST_CODE_SELECT;
import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * @author Administrator
 * 工具类
 */
public class Utils extends Activity{
    private int maxImgCount;
    private Context mContext;
    private ImagePickerAdapter adapter;
    private ImagePickerAdapter.OnRecyclerViewItemClickListener listener;
    private ArrayList<ImageItem> selImageList;
    public static boolean isEmail(String email){

        String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public Utils() {
    }

    public Utils(int maxImgCount, Context mContext,ImagePickerAdapter.OnRecyclerViewItemClickListener listener) {
        this.maxImgCount = maxImgCount;
        this.mContext = mContext;
        this.listener=listener;
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

    public void initImagePicker() {
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
    public void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        ImagePickerAdapter adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(listener);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
    private HashMap<String, String> init(final HashMap<String,String> parameter) {

        final Dialog dialog=new Dialog(DateType.TYPE_ALL,this);
        final EditText title=(EditText) findViewById(R.id.title_text);
        final EditText teacher=(EditText) findViewById(R.id.teacher_text);
        final EditText address=(EditText) findViewById(R.id.address_text);
        final EditText content=(EditText) findViewById(R.id.content_text);
        final Button startTime=(Button)findViewById(R.id.start_time);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.showDatePickDialog(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String mdata=sd.format(date);
                        startTime.setText(mdata);
                        parameter.put("startTime",mdata);

                    }
                });

            }
        });
        final Button endtTime=(Button) findViewById(R.id.end_time);
        endtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.showDatePickDialog(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String mdata=sd.format(date);
                        endtTime.setText(mdata);
                        parameter.put("endTime",mdata);

                    }
                });

            }
        });
        Button add_class=(Button) findViewById(R.id.add_class);
        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_data=title.getText().toString();
                String teacher_data=teacher.getText().toString();
                String address_data=address.getText().toString();
                String content_data=content.getText().toString();
                String starttime_data=startTime.getText().toString();
                String endtime_data=endtTime.getText().toString();
                String image=adapter.base64;
                if (TextUtils.isEmpty(title_data)){
                    Toast.makeText(mContext, "请输入课程标题！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(teacher_data)){
                    Toast.makeText(mContext, "请输入课程讲师！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(address_data)){
                    Toast.makeText(mContext, "请输入课程地点！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(content_data)){
                    Toast.makeText(mContext, "请输入课程详情！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(starttime_data)){
                    Toast.makeText(mContext, "请输入开始时间！", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(endtime_data)){
                    Toast.makeText(mContext, "请输入结束时间！", Toast.LENGTH_SHORT).show();
                }else if (image==null){
                    Toast.makeText(mContext, "请上传图片！", Toast.LENGTH_SHORT).show();
                }else {
                    parameter.put("title",title_data);
                    parameter.put("teacher",teacher_data);
                    parameter.put("address",address_data);
                    parameter.put("content",content_data);
                    parameter.put("base64",image);

                }

            }
        });
        ImageView action_image=findViewById(R.id.doctor_iv_return);
        action_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED,(new Intent()).setAction("1"));
                finish();
            }
        });
        return parameter;
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

    private void Switchs(int position){
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
                                Intent intent = new Intent(mContext, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);

                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(mContext, ImageGridActivity.class);
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
}