package cn.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import cn.base.base_util.R;

/**
 * Created by base on 2020/03/20.
 */
public class NavigationButton extends FrameLayout {
    private Fragment mFragment = null;
    private Class<?> mClx;
    private ImageView mIconView;
    private TextView mTitleView;
    private View mDotView;
    private String mTag;
    private Context mContext;

    public NavigationButton(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public NavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public NavigationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NavigationButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_nav_item, this, true);

        mIconView = findViewById(R.id.nav_iv_icon);
        mTitleView = findViewById(R.id.nav_tv_title);
        mDotView = findViewById(R.id.nav_tv_dot);
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mIconView.setSelected(selected);
        mTitleView.setSelected(selected);
        if (selected) {
            mTitleView.setTextColor(mContext.getResources().getColor(R.color.c_5e6066));
        } else {
            mTitleView.setTextColor(mContext.getResources().getColor(R.color.c_B5BEC6));
        }
    }

    public void init(@DrawableRes int resId, @StringRes int strId, Class<?> clx) {
        mIconView.setImageResource(resId);
        mTitleView.setText(strId);
        mTitleView.setTextColor(mContext.getResources().getColor(R.color.c_B5BEC6));
        mClx = clx;
        mTag = mClx.getName();
    }

    public void setNum(boolean isSet) {
        if (isSet) {
            mDotView.setVisibility(VISIBLE);
        } else {
            mDotView.setVisibility(GONE);
        }
    }

    public Class<?> getClx() {
        return mClx;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    public String getTag() {
        return mTag;
    }
}
