package com.example.baojiechang.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.baojiechang.myapplication.activity.AddClassActivity;
import com.example.baojiechang.myapplication.utils.NewBase64;
import com.example.baojiechang.myapplication.utils.Utils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：ikkong （ikkong@163.com），修改 jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：微信图片选择的Adapter, 感谢 ikkong 的提交
 * ================================================
 */
public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.SelectedPicViewHolder> {
    private int maxImgCount;
    private Context mContext;
    private List<ImageItem> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;
    private boolean isAdded;   //是否额外添加了最后一个图片
    private NewBase64 newBase64=new NewBase64();

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setImages(List<ImageItem> data) {
        mData = new ArrayList<>(data);
        if (getItemCount() < maxImgCount) {
            mData.add(new ImageItem());
            isAdded = true;
        } else {
            isAdded = false;
        }
        notifyDataSetChanged();

    }

    public List<ImageItem> getImages() {
        //由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
        if (isAdded) return new ArrayList<>(mData.subList(0, mData.size() - 1));
        else return mData;
    }

    public ImagePickerAdapter(Context mContext, List<ImageItem> data, int maxImgCount) {
        this.mContext = mContext;
        this.maxImgCount = maxImgCount;
        this.mInflater = LayoutInflater.from(mContext);
        setImages(data);
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.list_item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public String base64;
    public  class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_img;
        private int clickPosition;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }

        public void bind(int position) {
            //设置条目的点击事件
            itemView.setOnClickListener(this);
            //根据条目位置设置图片
            ImageItem item = mData.get(position);
            if (isAdded && position == getItemCount() - 1) {
                iv_img.setImageResource(R.drawable.selector_image_add);
                clickPosition = AddClassActivity.IMAGE_ITEM_ADD;
            } else {

                if(item.path!=null&&item.path.startsWith("http")){

                    loadImage(item.path,iv_img);
                }else{
                    ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 0, 0);
                    clickPosition = position;
                    Log.e("item",item.path);
                    try {
                        base64= newBase64.base64(item.path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 0, 0);
//                clickPosition = position;



            }
        }
        public void loadImage(String picUrl,ImageView ivShow){
            Glide.with(mContext)
                    .load(picUrl)
                    //设置占位图
                    .placeholder(R.mipmap.ic_launcher)
                    //加载错误图
                    .error(R.mipmap.ic_launcher)
                    //磁盘缓存的处理
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivShow);
        }
        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, clickPosition);
        }

    }
}