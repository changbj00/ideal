package com.luxiaochun.multiselectiondialog;

import java.io.Serializable;

/**
 * ProjectName: MultiChooseDialog
 * PackageName: com.luxiaochun.multiselectiondialog
 * Author: jun
 * Date: 2018-08-21 11:19
 * Copyright: (C)HESC Co.,Ltd. 2016. All rights reserved.
 */
public enum DialogType implements Serializable{
    SINGLE,        //单级单选（最常见）
    SINGLE_BOTTOM, //单选多级（最后一级可点击）
    SINGLE_ALL,    //单选多级(任何一级都可点击选择)
    MULTI,         //多选单级
    MULTI_ALL,     //多选多级
    MULTI_ORDER,   //限制排序多选单级
}
