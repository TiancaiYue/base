package cn.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.base.base_util.R;

/**
 * ToastUtil
 * Created by base on 20/04/08.
 */
public class YZToastUtil {
    private static Toast mToast;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static int duration = Toast.LENGTH_SHORT;

    public static void showMessage(Context context, CharSequence message) {
        mContext = context;
        if (message == null || !YZStringUtil.isNotEmpty(message.toString(), true)) {
            return;
        }
        if (message.length() > 25) {
            duration = 5000;
        } else if (message.length() > 20) {
            duration = 3000;
        } else if (message.length() > 15) {
            duration = 2000;
        } else {
            duration = 1000;
        }
        showInMainThread(message);
    }

    public static void showMessage(Context context, CharSequence message, int mDuration) {
        mContext = context;
        duration = mDuration;
        showInMainThread(message);
    }

    private static void showInMainThread(final CharSequence message) {
        if (mContext == null || message.equals("")) return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(message);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    show(message);
                }
            });
        }
    }

    private static void show(final CharSequence content) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.mola_toast_view, null);
        TextView textView = layout.findViewById(R.id.tip_text);
        textView.setText(content);

        if (mToast != null) {
            mToast.setDuration(duration);
            mToast.setView(layout);
            mToast.show();
        } else {
            mToast = new Toast(mContext);
            mToast.setGravity(Gravity.BOTTOM, 0, 300);
            mToast.setDuration(duration);
            mToast.setView(layout);
            mToast.show();
        }
    }
}
