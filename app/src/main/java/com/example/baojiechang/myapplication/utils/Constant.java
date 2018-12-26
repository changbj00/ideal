/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.baojiechang.myapplication.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.baojiechang.myapplication.activity.MainActivity;

/**
 *
 * 常量类，用于定义一些常量
 */
public class Constant {


    //请求成功0
    public static final String KEY_SUCCESS = "100";
    //请求失败
    public static final String KEY_FAILURE = "101";
    //邮箱不存在
    public static final String KEY_NO_EMAIL = "102";
    //邮箱已存在
    public static final String KEY_EXIST_EMAIL = "103";
    //已报名该课程
    public static final String KEY_APPLY_CLASS = "104";

    public void toast(Context mcontext, String code,String message){
        if (code.equals(KEY_SUCCESS)){
            Toast.makeText(mcontext, message, Toast.LENGTH_LONG).show();
        }else if (code.equals(KEY_FAILURE)){
            Toast.makeText(mcontext, message, Toast.LENGTH_LONG).show();
        }else if (code.equals(KEY_NO_EMAIL)){
            Toast.makeText(mcontext, message, Toast.LENGTH_LONG).show();
        }else if (code.equals(KEY_EXIST_EMAIL)){
            Toast.makeText(mcontext, message, Toast.LENGTH_LONG).show();
        }else if (code.equals(KEY_APPLY_CLASS)){
            Toast.makeText(mcontext, message, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(mcontext, "未知错误", Toast.LENGTH_LONG).show();
        }
    }
}

