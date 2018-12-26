package com.luxiaochun.multiselectiondialog.adapter;

import android.os.Handler;
import android.view.View;

import com.luxiaochun.multiselectiondialog.R;
import com.luxiaochun.multiselectiondialog.base.Node;
import com.luxiaochun.multiselectiondialog.fragment.MultiSelectionDialogFragment;
import com.luxiaochun.multiselectiondialog.listener.OnItemClickListener;
import com.luxiaochun.multiselectiondialog.viewholder.RVBaseViewHolder;

import java.util.List;

/**
 * ProjectName: JiuZhou
 * PackageName: com.example.jun.jiuzhou.MultiTreeListView.adapter
 * Author: jun
 * Date: 2018-03-14 10:10
 * 单选(针对单列无子类的情况，也是最常用的一种情况)
 */
public class SingleAdapter extends AbsTreeRecyclerAdapter {
    private MultiSelectionDialogFragment fragment;
    //单选记忆解决方案
//    private boolean[] mChildrenFrozen;

    public SingleAdapter(MultiSelectionDialogFragment fragment, List<Node> datas, int iconExpand, int iconNoExpand, OnItemClickListener onItemClickListener) {
        super(datas, iconExpand, iconNoExpand, onItemClickListener);
        this.fragment = fragment;
//        mChildrenFrozen = new boolean[datas.size()];
//        Arrays.fill(mChildrenFrozen, false);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.single_selection_item;
    }

    @Override
    public void onBindViewHolder(final Node node, RVBaseViewHolder holder, final int position) {
        holder.setText(R.id.id_treenode_label, node.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (onItemClickListener != null) {
                            if (fragment.isShowing()) {
                                fragment.dismiss();
                                onItemClickListener.onClick(node, position);
                            }
                        }
                    }
                }, 60);
            }
        });
    }
}
