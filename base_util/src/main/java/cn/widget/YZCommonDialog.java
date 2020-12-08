package cn.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.base.base_util.R;

public class YZCommonDialog {
    private Dialog dialog;
    private TextView tvTitle;
    private TextView warnning_contect;
    private TextView cancel_btn;
    private TextView true_btn;
    private View center_line;

    public YZCommonDialog(Context context) {
        dialog = new Dialog(context, R.style.dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉头
        dialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog

        //背景变暗
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        attributes.width = (int) (metrics.widthPixels * 0.9);
        attributes.height = (int) (metrics.heightPixels * 0.9);
        attributes.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributes.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(attributes);
        dialog.setContentView(R.layout.dialog_common);
        tvTitle = dialog.findViewById(R.id.tvTitle);
        warnning_contect = dialog.findViewById(R.id.warnning_contect);
        cancel_btn = dialog.findViewById(R.id.cancel_btn);
        true_btn = dialog.findViewById(R.id.true_btn);
        cancel_btn.setOnClickListener(v -> dismiss());
    }

    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }

    public void setContent(String string) {
        warnning_contect.setText(string);
    }

    public void setLeftBtnText(String lefttext) {
        cancel_btn.setText(lefttext);
    }

    public void setRightBtnText(String righttext) {
        true_btn.setText(righttext);
    }

    public void setRightBtnTextCorlorRes(int colorRes) {
        true_btn.setTextColor(colorRes);
    }

    public void setRightListener(View.OnClickListener listener) {
        true_btn.setOnClickListener(listener);
    }

    public void setLeftListener(View.OnClickListener listener) {
        cancel_btn.setOnClickListener(listener);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
