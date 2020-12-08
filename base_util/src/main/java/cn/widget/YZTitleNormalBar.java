package cn.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import cn.base.base_util.R;
import cn.utils.YZKeyboardUtil;
import cn.utils.YZActivityUtil;

/**
 * Created by base on 2020/01/16.
 * 标题栏
 */
public class YZTitleNormalBar extends LinearLayout implements View.OnClickListener {
    private LinearLayout mLeftLl;
    private LinearLayout mRightLl;
    private TextView mTitleTv;
    private TextView mLeftTextTv;
    private TextView mRightTextTv;
    private ImageView mLeftIconIv;
    private ImageView mRightIconIv;
    private ConstraintLayout mTitleBarCon;
    private LeftBarClickListener leftBarClickListener;
    private RightBarClickListener rightBarClickListener;
    private RightMoreBarClickListener rightMoreBarClickListener;
    private LeftTextClickListener leftTextClickListener;
    private RightTextClickListener rightTextClickListener;
    private Context mContext;
    private ImageView mRightMoreIv;
    private LinearLayout mRightMoreLl;
    private View mView;


    public YZTitleNormalBar(Context context) {
        this(context, null);
        mContext = context;
    }

    public YZTitleNormalBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public YZTitleNormalBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs, defStyleAttr);
    }

    private void init(@Nullable AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(mContext).inflate(R.layout.layout_title_normal, this);
        initView(attrs, defStyleAttr);
    }

    public ImageView getmRightIconIv() {
        return mRightIconIv;
    }

    @Override
    public void onClick(View v) {
        YZKeyboardUtil.hideInputMethod(v);
        int id = v.getId();
        if (id == R.id.ll_back) {
            if (leftBarClickListener != null) {
                leftBarClickListener.onLeftBarClick();
            } else {
                YZActivityUtil.finish(mContext);
            }
        } else if (id == R.id.ll_right) {
            if (rightBarClickListener != null) {
                rightBarClickListener.onRightBarClick();
            }
        } else if (id == R.id.tv_left_text) {
            if (leftTextClickListener != null) {
                leftTextClickListener.onLeftTextClick();
            }
        } else if (id == R.id.tv_right_text) {
            if (rightTextClickListener != null) {
                rightTextClickListener.onRightTextClick();
            }
        } else if (id == R.id.ll_right_more) {
            if (rightMoreBarClickListener != null) {
                rightMoreBarClickListener.onRightMoreBarClick();
            }
        }
    }

    /**
     * 设置默认View
     */
    private void initView(@Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.YZTitleNormalBar, defStyleAttr, 0);
        boolean tbTitleIsBack = array.getBoolean(R.styleable.YZTitleNormalBar_tb_title_is_back, true);
        boolean tbTitleIsLine = array.getBoolean(R.styleable.YZTitleNormalBar_tb_title_is_line, false);
        String tbTitle = array.getString(R.styleable.YZTitleNormalBar_tb_title);
        String tbTitleLeft = array.getString(R.styleable.YZTitleNormalBar_tb_title_left);
        String tbTitleRight = array.getString(R.styleable.YZTitleNormalBar_tb_title_right);
        int tbTitleColor = array.getColor(R.styleable.YZTitleNormalBar_tb_title_color, mContext.getResources().getColor(R.color.color_black_text));
        int tbTitleLeftColor = array.getColor(R.styleable.YZTitleNormalBar_tb_title_left_color, mContext.getResources().getColor(R.color.color_black_text));
        int tbTitleRightColor = array.getColor(R.styleable.YZTitleNormalBar_tb_title_right_color, mContext.getResources().getColor(R.color.color_black_text));
        int tbBackgroundColor = array.getColor(R.styleable.YZTitleNormalBar_tb_title_background, mContext.getResources().getColor(R.color.color_white));
        Drawable tbLeftIcon = array.getDrawable(R.styleable.YZTitleNormalBar_tb_left_icon);
        Drawable tbRightIcon = array.getDrawable(R.styleable.YZTitleNormalBar_tb_right_icon);
        Drawable tbRightMoreIcon = array.getDrawable(R.styleable.YZTitleNormalBar_tb_right_more_icon);
        array.recycle();

        mLeftLl = findViewById(R.id.ll_back);
        mRightLl = findViewById(R.id.ll_right);
        mTitleTv = findViewById(R.id.tv_title);
        mLeftTextTv = findViewById(R.id.tv_left_text);
        mRightTextTv = findViewById(R.id.tv_right_text);
        mLeftIconIv = findViewById(R.id.iv_back);
        mRightIconIv = findViewById(R.id.iv_right_icon);
        mRightMoreIv = findViewById(R.id.iv_right_more);
        mRightMoreLl = findViewById(R.id.ll_right_more);
        mTitleBarCon = findViewById(R.id.con_title_bar);
        mView = findViewById(R.id.view);

        mLeftLl.setOnClickListener(this);
        mRightLl.setOnClickListener(this);
        mRightMoreLl.setOnClickListener(this);
        mLeftTextTv.setOnClickListener(this);
        mRightTextTv.setOnClickListener(this);

        if (tbLeftIcon != null) { //左侧返回按钮的图标
            mLeftLl.setVisibility(View.VISIBLE);
            setLeftICon(tbLeftIcon);
        } else {
            if (tbTitleIsBack) { // 默认需要返回
                mLeftLl.setVisibility(View.VISIBLE);
                mLeftIconIv.setImageResource(R.mipmap.icon_back);
            } else {
                mLeftLl.setVisibility(View.GONE);
            }
        }


        if (tbTitleIsLine) {
            mView.setVisibility(View.VISIBLE);
        } else {
            mView.setVisibility(View.GONE);
        }

        if (tbRightIcon != null) { //右侧按钮的图标
            mRightLl.setVisibility(View.VISIBLE);
            setRightICon(tbRightIcon);
        } else {
            mRightLl.setVisibility(View.GONE);
        }

        if (tbRightMoreIcon != null) { //右侧第一个按钮的图标
            mRightMoreLl.setVisibility(View.VISIBLE);
            setRightMoreICon(tbRightMoreIcon);
        } else {
            mRightMoreLl.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(tbTitle)) { //标题
            mTitleTv.setVisibility(VISIBLE);
            setText(tbTitle);
        } else {
            mTitleTv.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(tbTitleLeft)) { //左标题
            mLeftTextTv.setVisibility(VISIBLE);
            setLeftTitle(tbTitleLeft);
        } else {
            mLeftTextTv.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(tbTitleRight)) { //右侧标题
            mRightTextTv.setVisibility(VISIBLE);
            setRightTitle(tbTitleRight);
        } else {
            mRightTextTv.setVisibility(GONE);
        }

        setTextColor(tbTitleColor); //侧标题颜色
        setLeftTextColor(tbTitleLeftColor); //左侧标题颜色
        setRightTextColor(tbTitleRightColor);//右侧标题颜色
        setTitleBackgroundColor(tbBackgroundColor); //背景颜色
    }

    /**
     * 第标题
     */
    public void setText(String title) {
        if (!TextUtils.isEmpty(title)) { //标题
            mTitleTv.setVisibility(VISIBLE);
            mTitleTv.setText(title == null ? "" : title);
        } else {
            mTitleTv.setVisibility(GONE);
        }
    }

    public String getText() {
        if (mTitleTv.getText() != null && !TextUtils.isEmpty(mTitleTv.getText().toString())) {
            return mTitleTv.getText().toString();
        } else {
            return "";
        }
    }

    /**
     * 左侧标题
     */
    public void setLeftTitle(String title) {
        if (!TextUtils.isEmpty(title)) { //标题
            mLeftTextTv.setVisibility(VISIBLE);
            mLeftTextTv.setText(title == null ? "" : title);
        } else {
            mLeftTextTv.setVisibility(GONE);
        }
    }

    public String getLeftTitle() {
        if (mLeftTextTv.getText() != null && !TextUtils.isEmpty(mLeftTextTv.getText().toString())) {
            return mLeftTextTv.getText().toString();
        } else {
            return "";
        }
    }

    /**
     * 右侧标题
     */
    public void setRightTitle(String title) {
        if (!TextUtils.isEmpty(title)) { //标题
            mRightTextTv.setVisibility(VISIBLE);
            mRightTextTv.setText(title == null ? "" : title);
        } else {
            mRightTextTv.setVisibility(GONE);
        }
    }

    public String getRightTitle() {
        if (mRightTextTv.getText() != null && !TextUtils.isEmpty(mRightTextTv.getText().toString())) {
            return mRightTextTv.getText().toString();
        } else {
            return "";
        }
    }

    public void setmRightTitleDrawable(Drawable drawable){
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mRightTextTv.setCompoundDrawables(null,null,drawable,null);
    }

    public TextView getmRightTextTv() {
        return mRightTextTv;
    }

    /**
     * 左侧图标
     */
    public void setLeftICon(Drawable resId) {
        if (resId != null) {
            mLeftLl.setVisibility(VISIBLE);
            mLeftIconIv.setImageDrawable(resId);
        } else {
            mLeftLl.setVisibility(GONE);
        }
    }

    /**
     * 右侧图标
     */
    public void setRightICon(Drawable resId) {
        if (resId != null) {
            mRightLl.setVisibility(VISIBLE);
            mRightIconIv.setImageDrawable(resId);
        } else {
            mRightLl.setVisibility(GONE);
        }
    }

    /**
     * 右侧第一个图标
     */
    public void setRightMoreICon(Drawable resId) {
        if (resId != null) {
            mRightMoreLl.setVisibility(VISIBLE);
            mRightMoreIv.setImageDrawable(resId);
        } else {
            mRightMoreLl.setVisibility(GONE);
        }
    }

    /**
     * 第标题颜色
     */
    public void setTextColor(int resId) {
        mTitleTv.setTextColor(resId);
    }

    /**
     * 左侧标题颜色
     */
    public void setLeftTextColor(int resId) {
        mLeftTextTv.setTextColor(resId);
    }

    /**
     * 右侧标题颜色
     */
    public void setRightTextColor(int resId) {
        mRightTextTv.setTextColor(resId);
    }

    /**
     * 背景颜色
     */
    public void setTitleBackgroundColor(int resId) {
        mTitleBarCon.setBackgroundColor(resId);
    }

    /**
     * 左侧按钮的监听
     */
    public interface LeftBarClickListener {
        void onLeftBarClick();
    }


    /**
     * 右侧按钮的监听
     */
    public interface RightBarClickListener {
        void onRightBarClick();
    }

    /**
     * 右侧按钮的监听
     */
    public interface RightMoreBarClickListener {
        void onRightMoreBarClick();
    }


    /**
     * 右侧按钮的监听
     */
    public interface LeftTextClickListener {
        void onLeftTextClick();
    }

    /**
     * 右侧字体的监听
     */
    public interface RightTextClickListener {
        void onRightTextClick();
    }

    public void setLeftBarClickListener(LeftBarClickListener leftBarClickListener) {
        this.leftBarClickListener = leftBarClickListener;
    }

    public void setRightBarClickListener(RightBarClickListener rightBarClickListener) {
        this.rightBarClickListener = rightBarClickListener;
    }

    public void setRightMOreBarClickListener(RightMoreBarClickListener rightMoreBarClickListener) {
        this.rightMoreBarClickListener = rightMoreBarClickListener;
    }

    public void setLeftTextClickListener(LeftTextClickListener leftTextClickListener) {
        this.leftTextClickListener = leftTextClickListener;
    }

    public void setRightTextClickListener(RightTextClickListener rightTextClickListener) {
        this.rightTextClickListener = rightTextClickListener;
    }

    /**
     * 获取控件
     */
    public View getView(int resId) {
        if (resId != 0) {
            return findViewById(resId);
        } else {
            return null;
        }
    }
}