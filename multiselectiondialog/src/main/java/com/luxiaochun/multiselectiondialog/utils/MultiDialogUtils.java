package com.luxiaochun.multiselectiondialog.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Vector
 * on 2017/6/6 0006.
 */

public class MultiDialogUtils {
    public static int dip2px(int dip, Context context) {
        return (int) (dip * getDensity(context) + 0.5f);
    }

    public static float getDensity(Context context) {
        return getDisplayMetrics(context).density;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
