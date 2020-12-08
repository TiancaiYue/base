package cn.base;

import android.os.Bundle;
import android.view.View;

/**
 * Created by base on 2020-01-14.
 */
public interface IBaseView {

    /**
     * 绑定布局
     *
     * @return 布局Id
     */
    int getLayoutId();

    /**
     * 初始化view
     */
    void initView(final Bundle savedInstanceState, final View view);

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 点击事件
     */
    void initEvent();
}
