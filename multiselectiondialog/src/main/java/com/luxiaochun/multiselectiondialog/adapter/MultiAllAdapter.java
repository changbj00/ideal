package com.luxiaochun.multiselectiondialog.adapter;

import android.util.TypedValue;
import android.view.View;

import com.luxiaochun.multiselectiondialog.R;
import com.luxiaochun.multiselectiondialog.base.Node;
import com.luxiaochun.multiselectiondialog.viewholder.RVBaseViewHolder;

import java.util.List;


/**
 * ProjectName: JiuZhou
 * PackageName: com.example.jun.jiuzhou.MultiTreeListView
 * Author: jun
 * Date: 2018-03-14 16:59
 */
public class MultiAllAdapter extends AbsTreeRecyclerAdapter {
    public MultiAllAdapter(List<Node> datas, int iconExpand, int iconNoExpand) {
        super(datas, iconExpand, iconNoExpand, null);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.multi_selection_item;
    }

    @Override
    public void onBindViewHolder(final Node node, final RVBaseViewHolder holder, final int position) {
        holder.setText(R.id.id_treenode_label, node.getName());
        holder.getCheckBox(R.id.cb_select_tree).setVisibility(View.VISIBLE);
        if (node.getIcon() == -1) {
            holder.getImageView(R.id.tree_img).setVisibility(View.INVISIBLE);
        } else {
            holder.getImageView(R.id.tree_img).setVisibility(View.VISIBLE);
            holder.getImageView(R.id.tree_img).setImageResource(node.getIcon());
        }
        if (node.isChecked()) {
            holder.getCheckBox(R.id.cb_select_tree).setChecked(true);
        } else {
            holder.getCheckBox(R.id.cb_select_tree).setChecked(false);
        }
        if (position == 0) {
            Node.textSize = holder.getTextView(R.id.id_treenode_label).getTextSize();
        }

        if (Node.textSize > 0) {
            holder.getTextView(R.id.id_treenode_label).setTextSize(TypedValue.COMPLEX_UNIT_PX, Node.textSize - 4 * node.getLevel());
        }
        holder.getCheckBox(R.id.cb_select_tree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(node, holder.getCheckBox(R.id.cb_select_tree).isChecked());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
            }
        });
    }
}
