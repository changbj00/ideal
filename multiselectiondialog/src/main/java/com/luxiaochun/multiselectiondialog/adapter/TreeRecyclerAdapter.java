package com.luxiaochun.multiselectiondialog.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.luxiaochun.multiselectiondialog.base.Node;
import com.luxiaochun.multiselectiondialog.base.TreeHelper;
import com.luxiaochun.multiselectiondialog.viewholder.RVBaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeRecyclerAdapter extends RecyclerView.Adapter<RVBaseViewHolder> {
    /**
     * 存储所有可见的Node
     */
    protected List<Node> mVisibleNodes;

    /**
     * 存储所有的Node
     */
    protected List<Node> mAllNodes;

    public static int iconExpand;
    public static int iconCollapse;

    public TreeRecyclerAdapter(List<Node> datas, int iconExpand, int iconCollapse) {
        this.iconExpand = iconExpand;
        this.iconCollapse = iconCollapse;
        /**
         * 对所有的Node进行排序
         */
        mAllNodes = TreeHelper.getSortedNodes(datas);
        /**
         *  对所有节点分级
         */
        TreeHelper.grading(mAllNodes);
        /**
         * 过滤出可见的Node
         */
        mVisibleNodes = TreeHelper.filterVisibleNode(mAllNodes);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, final int position) {
        Node node = mVisibleNodes.get(position);
        // 设置内边距
        holder.itemView.setPadding(node.getLevel() * 30, 3, 3, 3);
        /**
         * 设置节点点击时，可以展开以及关闭,将事件继续往外公布
         */
        Log.i("TreeRecyclerAdapter====", node.getName() + "====" + node.isChecked());
        onBindViewHolder(node, holder, position);
    }

    @Override
    public int getItemCount() {
        return mVisibleNodes.size();
    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(int position) {
        Node n = mVisibleNodes.get(position);
        if (n != null) { // 排除传入参数错误异常
            if (!n.isLeaf()) {
                boolean isExpand = n.isExpand();
                n.setExpand(!isExpand);
                //展开跟收缩不一样，展开是一级一级展开，而收缩是父类以下全部收缩
                //展开情况
                if (n.isExpand()) {
                    List<Node> list = n.getChildren();
                    for (Node node : list) {
                        node.setExpand(false);
                        node.setVisible(true);
                    }
                }
                //收缩情况
                else {
                    Collapse(n);
                }
                mVisibleNodes = TreeHelper.filterVisibleNode(mAllNodes);
            }
            notifyDataSetChanged();// 刷新视图
        }
    }

    //收缩情况
    private void Collapse(Node n) {
        List<Node> list = n.getChildren();
        if (list != null && list.size() > 0) {
            for (Node node : list) {
                node.setExpand(false);
                node.setVisible(false);
                Collapse(node);
            }
        }
    }

    /**
     * 设置多选
     *
     * @param node
     * @param checked
     */
    protected void setChecked(final Node node, boolean checked) {
        node.setChecked(checked);
        setChildChecked(node, checked);
        if (node.getParent() != null)
            setNodeParentChecked(node.getParent(), checked);
        notifyDataSetChanged();
    }

    /**
     * 设置子节点的选中状态，根节点被选中，则所有子节点都被选中；
     * 同理，若被取消，则所有子节点也被取消
     *
     * @param node
     * @param checked
     */
    public <T, B> void setChildChecked(Node<T, B> node, boolean checked) {
        if (!node.isLeaf()) {
            for (Node childrenNode : node.getChildren()) {
                childrenNode.setChecked(checked);
                setChildChecked(childrenNode, checked);
            }
        }
    }

    private void setNodeParentChecked(Node node, boolean checked) {
        if (checked) {
            List<Node> list = node.getChildren();
            boolean isParentChecked = false;
            for (Node n : list) {
                isParentChecked = checked && n.isChecked();
            }
            if (isParentChecked) {
                node.setChecked(checked);
            }
            if (node.getParent() != null)
                setNodeParentChecked(node.getParent(), checked);
        } else {
            List<Node> childrens = node.getChildren();
            boolean isChecked = true;
            for (Node children : childrens) {
                if (!children.isChecked()) {
                    isChecked = false;
                    break;
                }
            }
            //只要有一个子节点没有别选中，父节点就不选中
            if (!isChecked) {
                node.setChecked(checked);
            }
            if (node.getParent() != null)
                setNodeParentChecked(node.getParent(), checked);
        }
    }

    /**
     * 获取到所有选中的Node
     *
     * @return
     */
    public List<Node> getCheckedNodeList() {
        List<Node> list = new ArrayList<>();
        for (Node node : mAllNodes) {
            if (node.isChecked() && node.isLeaf()) {
                list.add(node);
            }
        }
        return list;
    }

    public abstract void onBindViewHolder(Node node, RVBaseViewHolder holder, final int position);
}
