package com.luxiaochun.multiselectiondialog.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jun on 2017-12-21.
 */
public class Node<T, B> implements Serializable{

    /**
     * 传入的实体对象
     */
    public B bean;

    private T id;
    /**
     * 根节点pId为0
     */
    private T pId;
    private String name;
    /**
     * 当前的级别
     */
    private int level;
    /**
     * 是否可见
     */
    private boolean isVisible = false;
    /**
     * 是否展开
     */
    private boolean isExpand = false;
    private int icon = -1;
    /**
     * 下一级的子Node
     */
    private List<Node> children = new ArrayList<>();
    /**
     * 父Node
     */
    private Node parent;
    /**
     * 多选是否被checked选中
     */
    private boolean isChecked;
    /**
     * 根node的字体大小
     */
    public static float textSize;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }


    public Node(T id, T pId, String name) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
    }

    public Node(T id, T pId, String name, B bean) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.bean = bean;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public T getpId() {
        return pId;
    }

    public void setpId(T pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * 是否为跟节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }


    /**
     * 是否是叶子界点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 获取level
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public boolean isVisible() {
        if (parent == null)
            return true;
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}
