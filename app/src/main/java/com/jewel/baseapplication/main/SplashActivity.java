package com.jewel.baseapplication.main;

import android.os.Bundle;
import android.view.View;

import androidx.room.util.StringUtil;

import com.jewel.baseapplication.R;
import com.jewel.baseapplication.main.activity.MainActivity;

import cn.base.BaseActivity;
import cn.user_db.UserCache;
import cn.utils.YZActivityUtil;
import cn.utils.YZStringUtil;

public class SplashActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
    }

    @Override
    public void initData() {
        YZActivityUtil.skipActivity(mContext, MainActivity.class);
        SplashActivity.this.finish();
    }

    @Override
    public void initEvent() {
    }
}
