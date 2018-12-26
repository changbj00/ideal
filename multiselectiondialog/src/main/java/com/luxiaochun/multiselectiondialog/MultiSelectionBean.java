package com.luxiaochun.multiselectiondialog;

import com.luxiaochun.multiselectiondialog.base.Node;

import java.io.Serializable;
import java.util.List;

/**
 * ProjectName: MultiChooseDialog
 * PackageName: com.luxiaochun.multiselectiondialog
 * Author: jun
 * Date: 2018-08-21 09:51
 */
public class MultiSelectionBean implements Serializable {
    private String title;
    private DialogType type;
    private List<Node> mDatas;
    private boolean canceledOnTouchOutside;
    private int limited;
    private int mThemeColor;//主题颜色

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DialogType getType() {
        return type;
    }

    public void setType(DialogType type) {
        this.type = type;
    }

    public List<Node> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<Node> mDatas) {
        this.mDatas = mDatas;
    }

    public boolean isCanceledOnTouchOutside() {
        return canceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    public int getLimited() {
        return limited;
    }

    public void setLimited(int limited) {
        this.limited = limited;
    }

    public int getmThemeColor() {
        return mThemeColor;
    }

    public void setmThemeColor(int mThemeColor) {
        this.mThemeColor = mThemeColor;
    }
}
