package cn;

import android.app.Application;
import android.content.Context;

/**
 * Created by base on 2020/9/30.
 */
public class BaseSetting {
    private Application context;

    public static BaseSetting getInstance() {
        return BaseSetting.BaseSettingHolder.holder;
    }

    private static class BaseSettingHolder {
        private static BaseSetting holder = new BaseSetting();
    }


    public BaseSetting init(Application app) {
        context = app;
        return this;
    }

    /**
     * 获取全局上下文
     */
    public Context getContext() {
        return context;
    }
}
