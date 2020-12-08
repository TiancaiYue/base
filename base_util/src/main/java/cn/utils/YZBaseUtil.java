package cn.utils;

import android.content.res.Configuration;
import android.os.Looper;

import cn.BaseSetting;

/**
 * Created by base on 2020/9/30.
 */
public class YZBaseUtil {
    public static String getPhoneNameString() {
        String model = android.os.Build.MODEL; // 手机型号
        String namePre = "babelANphone";
        if (isTablet()) {
            namePre = "babelANpad";
        }
        return namePre + "(" + model + ")";
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet() {
        return (BaseSetting.getInstance().getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
