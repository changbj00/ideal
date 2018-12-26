package com.example.baojiechang.myapplication;


import android.content.Context;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.util.List;


    public class ClassAdapter extends BaseAdapter {
        private Context mcontext;
        private ClassBaseInfo baseInfo;
        private List<ClassBaseInfo> mlist;
        private SectionInfo sectionInfo;
        private Holder holder;
        public static final int ITEM_TITLE = 0;
        public static final int ITEM_INTRODUCE = 1;

        public ClassAdapter(Context context, List<ClassBaseInfo> list) {
            mcontext = context;
            mlist = list;

        }

        public void setClassList(List list){
            mlist=list;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return mlist.get(position).getType();
        }

        //重写方法一：返回的是你有几种类型的样式
        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            if (convertView == null) {
                switch (type) {
                    case ITEM_INTRODUCE:
                        convertView = View.inflate(mcontext, R.layout.play_item_title, null);
                        holder = new Holder();
                        holder.holder_title = (TextView) convertView.findViewById(R.id.play_title);
                        holder.holder_title.setText(mlist.get(position).getTitleText());
                        Log.e("class", mlist.get(position).getTitleText());
                        convertView.setTag(holder);
                        break;
                    case ITEM_TITLE:
                        convertView = View.inflate(mcontext, R.layout.item_msg, null);
                        sectionInfo = new SectionInfo();
                        sectionInfo.doctor_lv_headPortrait = (ImageView) convertView.findViewById(R.id.doctor_lv_headPortrait);
                        sectionInfo.title = (TextView) convertView.findViewById(R.id.doctor_tv_doctorName);
                        sectionInfo.teacher = (TextView) convertView.findViewById(R.id.doctor_tv_sectionName);
                        sectionInfo.startTime = (TextView) convertView.findViewById(R.id.doctor_tv_sectionIntro);
                        sectionInfo.endTime = (TextView) convertView.findViewById(R.id.doctor_tv_sectionIntro2);
                        sectionInfo.address = (TextView) convertView.findViewById(R.id.doctor_tv_sectionIntro3);

                        ClassBaseInfo baseInfo = (ClassBaseInfo) getItem(position);
                        loadImage(baseInfo.imgurl, sectionInfo.doctor_lv_headPortrait);
                        sectionInfo.title.setText(baseInfo.title);
                        sectionInfo.teacher.setText(baseInfo.teacher);
                        sectionInfo.startTime.setText(baseInfo.startTime);
                        sectionInfo.endTime.setText(baseInfo.endTime);
                        sectionInfo.address.setText(baseInfo.address);

                        convertView.setTag(sectionInfo);
                        break;
                    default:
                        Log.d("baojie", "baojie");
                        break;
                }
            } else {
                switch (type) {
                    case ITEM_INTRODUCE:
                        holder = (Holder) convertView.getTag();
                        holder.holder_title.setText(mlist.get(position).getTitleText());
                        Log.e("class", mlist.get(position).getTitleText());
                        break;
                    case ITEM_TITLE:
                        sectionInfo = (SectionInfo) convertView.getTag();
                        ClassBaseInfo baseInfo = (ClassBaseInfo) getItem(position);
                        loadImage(baseInfo.imgurl, sectionInfo.doctor_lv_headPortrait);
                        sectionInfo.title.setText(baseInfo.title);
                        sectionInfo.teacher.setText(baseInfo.teacher);
                        sectionInfo.startTime.setText(baseInfo.startTime);
                        sectionInfo.endTime.setText(baseInfo.endTime);
                        sectionInfo.address.setText(baseInfo.address);

                        break;
                    default:
                        break;
                }
            }
            return convertView;
        }

        private class SectionInfo {
            ImageView doctor_lv_headPortrait;
            TextView title;
            TextView teacher;
            TextView startTime;
            TextView endTime;
            TextView address;

        }

        private class Holder {
            TextView holder_title;
        }

        private void loadImage(String picUrl, ImageView ivShow) {
            Glide.with(mcontext)
                    .load(picUrl)
                    //设置占位图
                    .placeholder(R.mipmap.ic_launcher)
                    //加载错误图
                    .error(R.mipmap.ic_launcher)
                    //磁盘缓存的处理
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivShow);
        }

    }