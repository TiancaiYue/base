package cn.base;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import cn.base.base_util.R;
import cn.utils.YZActivityUtil;
import cn.utils.YZFastClickUtil;
import cn.utils.YZKeyboardUtil;
import cn.utils.YZToastUtil;

/**
 * Created by base on 2020/01/15.
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    public static String TAG = BaseActivity.class.getSimpleName();
    public boolean mIsForeground;
    protected Context mContext;
    protected Activity mActivity;
    public RequestCallback mRequestCallback;
    public int MIN_DELAY_TIME = 500;
    protected float mLastY = 0;
    protected FragmentManager mBaseFragmentManager;
    protected static final int NO_ANIMATION = 0;

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("--->onResume()", TAG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("--->onCreate()", TAG);

        mContext = this;
        mActivity = this;
        mIsForeground = true;
        YZActivityUtil.addActivity(this);
        if (getLayoutId() != 0) {
            View mContentView = LayoutInflater.from(this).inflate(getLayoutId(), null);
            setContentView(mContentView);
            initView(savedInstanceState, mContentView);
            mBaseFragmentManager = getSupportFragmentManager();
        }
        initData();
        initEvent();
        setSystemColor(R.color.color_transparent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("--->onPause()", TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("--->onDestroy()", TAG);

        mIsForeground = false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.v("--->onBackPressed()", TAG);

        YZActivityUtil.finish(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            mLastY = ev.getY();
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (YZKeyboardUtil.isShouldHideInput(v, ev)) {
                YZKeyboardUtil.hideInputMethod(v);
            }

            if (YZFastClickUtil.isFastClick(MIN_DELAY_TIME) && (Math.abs(mLastY - ev.getY()) < 50)) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void replaceFragment(int layoutId, Fragment fragment, boolean addToBackStack) {
        replaceFragment(layoutId, fragment, NO_ANIMATION, NO_ANIMATION, addToBackStack);
    }

    /**
     * 调用 FragmentTransaction 的replace 方法，同时设置 Fragment 的入栈和出栈动画
     *
     * @param layoutId       replace 的 布局Id
     * @param fragment       替换的Fragment
     * @param enterAnimation 入栈的动画
     * @param exitAnimation  出栈的动画
     */
    protected void replaceFragment(int layoutId, Fragment fragment, int enterAnimation, int exitAnimation, boolean isAddToBack) {
        if (enterAnimation < 0) {
            enterAnimation = 0;
        }

        if (exitAnimation < 0) {
            exitAnimation = 0;
        }

        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction transaction = mBaseFragmentManager.beginTransaction();
        transaction.setCustomAnimations(enterAnimation, exitAnimation, enterAnimation, exitAnimation);
        transaction.replace(layoutId, fragment, tag);

        if (isAddToBack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mRequestCallback != null && grantResults.length != 0) {
            ArrayList<String> listGranted = new ArrayList<>();
            ArrayList<String> listDenied = new ArrayList<>();

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    listGranted.add(permissions[i]);

                } else {
                    listDenied.add(permissions[i]);
                }
            }
            if (listGranted.size() != 0) {
                mRequestCallback.requestPermissionGranted(requestCode, listGranted.toArray(new String[0]));
            }
            if (listDenied.size() != 0) {
                mRequestCallback.requestPermissionDenied(listDenied.toArray(new String[0]));
            }
            mRequestCallback = null;
        }
    }

    public abstract class RequestCallback {
        protected abstract void requestPermissionGranted(int requestCode, String... permissions);

        /**
         * 默认提供单个权限申请的Snackbar提示
         *
         * @param permissions
         */
        protected void requestPermissionDenied(String... permissions) {
            StringBuilder snackText = new StringBuilder(getString(R.string.A0A001));
            for (String permission : permissions) {
                switch (permission) {
                    case Manifest.permission.READ_EXTERNAL_STORAGE:
                        snackText.append(getString(R.string.A0A002));
                        break;
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        snackText.append(getString(R.string.A0A003));
                        break;
                    case Manifest.permission.RECORD_AUDIO:
                        snackText.append(getString(R.string.A0A004));
                        break;
                    case Manifest.permission.CAMERA:
                        snackText.append(getString(R.string.A0A005));
                        break;
                    case Manifest.permission.READ_CONTACTS:
                    case Manifest.permission.WRITE_CONTACTS:
                    case Manifest.permission.READ_PHONE_STATE:
                        snackText.append(getString(R.string.A0006));
                        break;
                    case Manifest.permission.CALL_PHONE:
                        snackText.append(getString(R.string.A0007));
                        break;
                    case Manifest.permission.READ_CALL_LOG:
                        snackText.append(getString(R.string.A0008));
                        break;
                    case Manifest.permission.READ_SMS:
                        snackText.append(getString(R.string.A0009));
                        break;
                }
            }
            snackText.append(getString(R.string.A0010));
            YZToastUtil.showMessage(mContext, snackText.toString());
        }
    }

    public void showFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(400).start();
    }

    public void hideFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(400).start();
    }

    public void setSystemColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mContext.getResources().getColor(color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}
