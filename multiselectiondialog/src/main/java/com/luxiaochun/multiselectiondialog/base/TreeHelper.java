package com.luxiaochun.multiselectiondialog.base;

import com.luxiaochun.multiselectiondialog.adapter.TreeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jun on 2017-12-21.
 */
public class TreeHelper {

    /**
     * 传入node  返回排序后的Node
     *
     * @param datas
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static List<Node> getSortedNodes(List<Node> datas) {
        List<Node> result = new ArrayList<>();
        boolean isSortedList = false;//用来判断是否是已经排序过的数据,默认false（没有排序过）
        for (Node node : datas) {
            if (node.getChildren().size() > 0) {
                //如果根节点上已经有叶子节点了，说明已经做过排序，就不需要再做了
                isSortedList = true;
                break;
            }
        }
        List<Node> nodes;
        if (!isSortedList) {
            // 将数据转化成根节点关系
            nodes = convetData2Node(datas);
        } else {
            nodes = datas;
        }
        // 拿到根节点
        List<Node> rootNodes = getRootNodes(nodes);
        // 排序以及设置Node间关系
        for (Node node : rootNodes) {
            addNode(result, node);
        }
        return result;
    }

    /**
     * 过滤出所有可见的Node
     *
     * @param nodes
     * @return
     */
    public static List<Node> filterVisibleNode(List<Node> nodes) {
        List<Node> result = new ArrayList<>();
        for (Node node : nodes) {
            // 如果为根节点，或者上层目录为展开状态
            if (node.isVisible()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
     */
    private static List<Node> convetData2Node(List<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);
                if (m.getpId().equals(n.getId())) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId().equals(n.getpId())) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }
        return nodes;
    }

    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.isRoot())
                root.add(node);
        }
        return root;
    }

    /**
     * 把一个节点上的所有的内容都挂上去
     */
    private static <T, B> void addNode(List<Node> nodes, Node<T, B> node) {
        nodes.add(node);
        if (node.isLeaf())
            return;
        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(nodes, node.getChildren().get(i));
        }
    }

    /**
     * 设置节点的图标
     *
     * @param node
     */
    private static void setNodeIcon(Node node) {
        if (node.isLeaf()) {
            node.setIcon(-1);
        } else {
            if (node.isExpand()) {
                node.setIcon(TreeRecyclerAdapter.iconCollapse);
            } else {
                node.setIcon(TreeRecyclerAdapter.iconExpand);
            }
        }
    }

    /**
     * 对所有节点分级
     */
    public static void grading(List<Node> mAllNodes) {
        for (Node node : mAllNodes) {
            // 如果为根节点
            if (node.getParent() == null) {
                node.setLevel(0);
            } else {
                node.setLevel(node.getParent().getLevel() + 1);
            }
        }
    }
}
