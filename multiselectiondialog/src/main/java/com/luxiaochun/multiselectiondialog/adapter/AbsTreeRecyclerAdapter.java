package com.luxiaochun.multiselectiondialog.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.luxiaochun.multiselectiondialog.base.Node;
import com.luxiaochun.multiselectiondialog.listener.OnItemClickListener;
import com.luxiaochun.multiselectiondialog.viewholder.RVBaseViewHolder;

import java.util.List;

/**
 * Created by jun on 2017-12-21.
 */
public abstract class AbsTreeRecyclerAdapter extends TreeRecyclerAdapter {
    protected OnItemClickListener onItemClickListener;

    public AbsTreeRecyclerAdapter(List<Node> datas, int iconExpand, int iconNoExpand,
                                  OnItemClickListener onItemClickListener) {
        super(datas, iconExpand, iconNoExpand);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }
}
