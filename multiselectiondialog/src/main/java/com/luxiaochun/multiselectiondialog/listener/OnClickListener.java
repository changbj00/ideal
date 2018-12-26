package com.luxiaochun.multiselectiondialog.listener;

import com.luxiaochun.multiselectiondialog.base.Node;

import java.util.List;

/**
 * ProjectName: JiuZhou
 * PackageName: com.example.jun.jiuzhou.MultiTreeListView
 * Author: jun
 * Date: 2018-03-14 16:14
 */
public interface OnClickListener {
    void onPositive(List<Node> list);
    void onNegative();
}
