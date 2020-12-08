package cn.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import cn.base.base_util.R;

/**
 * 加载框
 * Created by base on 2020/4/24.
 */
public class YZProgressDialogWork {
    @SuppressLint("StaticFieldLeak")
    private static YZProgressDialogWork instance;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static Activity mActivity;
    private ProgressDialog progressDialog;

    private YZProgressDialogWork(Context context) {
        mContext = context;
        mActivity = (Activity) mContext;
    }

    /**
     * showProgressDialog
     *
     * @param context
     * @return
     */
    public static YZProgressDialogWork getInstance(Context context) {
        if (instance != null) {
            instance = null;
        }
        return instance = new YZProgressDialogWork(context);
    }

    /**
     * closeProgressDialog
     *
     * @return
     */
    public static YZProgressDialogWork getInstance() {
        return instance;
    }

    public void showProgressDialog() {
        if (instance == null) return;
        if (progressDialog == null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (mActivity != null && !mActivity.isFinishing()) {
                        progressDialog = new ProgressDialog(mContext);
                        progressDialog.setTitle(mContext.getString(R.string.A0011));
                        progressDialog.setMessage(mContext.getString(R.string.A0012));
                        progressDialog.show();
                    }
                }
            });
        }
    }

    public void showProgressDialog(final boolean canceled) {
        if (instance == null) return;
        if (progressDialog == null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (mActivity != null && !mActivity.isFinishing()) {
                        progressDialog = new ProgressDialog(mContext);
                        progressDialog.setTitle(mContext.getString(R.string.A0011));
                        progressDialog.setMessage(mContext.getString(R.string.A0012));
                        progressDialog.setCanceledOnTouchOutside(canceled);
                        progressDialog.show();
                    }
                }
            });
        }
    }

    public void showProgressDialog(final String title, final String message) {
        if (instance == null) return;
        if (progressDialog == null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (mActivity != null && !mActivity.isFinishing()) {
                        progressDialog = new ProgressDialog(mContext);
                        progressDialog.setTitle(title);
                        progressDialog.setMessage(message);
                        progressDialog.show();
                    }
                }
            });
        }
    }

    public void showProgressDialog(final String title, final String message, final boolean canceled) {
        if (instance == null) return;
        if (progressDialog == null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (mActivity != null && !mActivity.isFinishing()) {
                        progressDialog = new ProgressDialog(mContext);
                        progressDialog.setTitle(title);
                        progressDialog.setMessage(message);
                        progressDialog.setCanceledOnTouchOutside(canceled);
                        progressDialog.show();
                    }
                }
            });
        }
    }

    public void closeProgressDialog() {
        if (instance == null) {
            return;
        }
        if (progressDialog == null) {
            return;
        }
        progressDialog.dismiss();
        progressDialog = null;
    }
}
