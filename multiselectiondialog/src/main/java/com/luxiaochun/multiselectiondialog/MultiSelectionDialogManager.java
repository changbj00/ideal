package com.luxiaochun.multiselectiondialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.luxiaochun.multiselectiondialog.base.Node;
import com.luxiaochun.multiselectiondialog.fragment.MultiSelectionDialogFragment;
import com.luxiaochun.multiselectiondialog.listener.OnClickListener;
import com.luxiaochun.multiselectiondialog.listener.OnItemClickListener;

import java.util.List;

/**
 * ProjectName: MultiChooseDialog
 * PackageName: com.luxiaochun.multiselectiondialog
 * Author: jun
 * Date: 2018-08-21 09:38
 */
public class MultiSelectionDialogManager {
    public static final String TAG = MultiSelectionDialogManager.class.getSimpleName();
    /**
     * 四个个必填项
     */
    private Activity mActivity;
    private String title;//dialog标题
    private DialogType type;//dialog类型
    private List<Node> mDatas;//数据流

    private OnItemClickListener onItemClickListener;//选项点击事件
    private int mThemeColor;//主题颜色
    private boolean canceledOnTouchOutside;//是否外部取消
    private OnClickListener onClickListener;//按钮点击事件
    private int limited;//当排序选择时候的限制个数

    private MultiSelectionDialogManager(Builder builder) {
        mActivity = builder.getActivity();
        title = builder.getTitle();
        type = builder.getType();
        mDatas = builder.getDatas();
        onItemClickListener = builder.getOnItemClickListener();

        mThemeColor = builder.getmThemeColor();
        canceledOnTouchOutside = builder.isCanceledOnTouchOutside();
        onClickListener = builder.getOnClickListener();
        limited = builder.getLimited();
    }

    public Context getContext() {
        return mActivity;
    }


    /**
     * 跳转到更新页面
     */
    public void show() {
        if (mActivity != null && !mActivity.isFinishing()) {
            Bundle bundle = new Bundle();
            //添加信息
            MultiSelectionBean bean = fillDialogBean();
            bundle.putSerializable(TAG, bean);
            MultiSelectionDialogFragment fragment = MultiSelectionDialogFragment
                    .newInstance(bundle);
            if (onItemClickListener != null) {
                fragment.setOnItemClickListener(onItemClickListener);
            }
            if (onClickListener != null) {
                fragment.setOnClickListener(onClickListener);
            }
            fragment.show(((FragmentActivity) mActivity).getSupportFragmentManager(), "dialog");
        }
    }

    /**
     * @return 新版本信息
     */
    private MultiSelectionBean fillDialogBean() {
        MultiSelectionBean bean = new MultiSelectionBean();
        bean.setTitle(title);
        bean.setType(type);
        bean.setmDatas(mDatas);
        bean.setCanceledOnTouchOutside(canceledOnTouchOutside);
        bean.setLimited(limited);
        return bean;
    }

    public static class Builder {
        //必填
        private Activity mActivity;
        private String title;
        private DialogType type;
        private List<Node> mDatas;
        private boolean canceledOnTouchOutside = true;
        private int mThemeColor = -1;//主题颜色
        private OnItemClickListener onItemClickListener;
        private OnClickListener onClickListener;
        private int limited;

        public Activity getActivity() {
            return mActivity;
        }

        public Builder setActivity(Activity mActivity) {
            this.mActivity = mActivity;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public DialogType getType() {
            return type;
        }

        public Builder setType(DialogType type) {
            this.type = type;
            return this;
        }

        public List<Node> getDatas() {
            return mDatas;
        }

        public Builder setDatas(List<Node> mDatas) {
            this.mDatas = mDatas;
            return this;
        }

        public boolean isCanceledOnTouchOutside() {
            return canceledOnTouchOutside;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public OnItemClickListener getOnItemClickListener() {
            return onItemClickListener;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public OnClickListener getOnClickListener() {
            return onClickListener;
        }

        public Builder setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public int getLimited() {
            return limited;
        }

        public Builder setLimited(int limited) {
            this.limited = limited;
            return this;
        }

        public int getmThemeColor() {
            return mThemeColor;
        }

        public Builder setmThemeColor(int mThemeColor) {
            this.mThemeColor = mThemeColor;
            return this;
        }

        public MultiSelectionDialogManager build() {
            //校验
            if (getActivity() == null) {
                throw new NullPointerException("必要参数不能为空");
            }
            return new MultiSelectionDialogManager(this);
        }
    }
}
