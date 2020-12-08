package cn.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class YZYoZoWebView extends WebView {
    private WebSettings settings;
    private ValueCallback<Uri[]> mUploadMessageAboveL;

    public YZYoZoWebView(Context context) {
        super(context);
        initWebview(context);
    }

    public YZYoZoWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebview(context);
    }

    public YZYoZoWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initWebview(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initWebview(Context context) {
        initWebviewSetting();
        setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadMessageAboveL = filePathCallback;
                YZChooseImageUtil.openPhoto((Activity) context, 1);
                return true;
            }
        });
        setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_UP:
                    if (!v.hasFocus()) {
                        v.requestFocus();
                    }
                    break;
            }
            return false;
        });
    }

    public WebSettings initWebviewSetting() {
        settings = getSettings();
        settings.setJavaScriptEnabled(true);    //能够执行Javascript脚本
        settings.setDefaultTextEncodingName("UTF-8");   //设置默认的文本编码名称，以便在解码html页面时使用
        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);   //设置页面默认缩放密度
        settings.setDomStorageEnabled(true);    //设置是否启用了DOM storage
        settings.setJavaScriptCanOpenWindowsAutomatically(true);    //通过js打开新的窗口
        settings.setAppCacheEnabled(true);  //设置是否启用应用缓存
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);    //设置渲染优先级
        settings.setSupportZoom(true);  //设置可以支持缩放
        settings.setUseWideViewPort(true);  //支持标签的viewport属性
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //设置WebView底层的布局算法
        settings.setDisplayZoomControls(false); //不显示缩放控件
        settings.setLoadWithOverviewMode(true); // body宽度超出自动缩放
        settings.setDatabaseEnabled(true);  //设置是否开启数据库存储API权限
        settings.setAllowFileAccess(true);  //支持以 file:/// 打开本地文件,file:///android_asset 是默认被允许的
        settings.setLoadsImagesAutomatically(true); //设置WebView是否应该载入图像资源
        settings.setGeolocationEnabled(true);   //设置是否开启定位功能
        settings.setSaveFormData(false);    //设置WebView是否保存表单数据
        settings.setAllowContentAccess(true);   //启动或禁用WebView内的内容URL访问
        settings.setBuiltInZoomControls(false); //设置WebView是否应该使用其内置的缩放机制
        settings.setAllowFileAccessFromFileURLs(false); // 本地文件能否通过ajax访问别的本地文件
        settings.setAllowUniversalAccessFromFileURLs(true); // 本地文件能否通过ajax跨域访问http/https
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);   //当一个安全站点企图加载来自一个不安全站点资源时WebView的行为
        }
        requestFocus();
        setVerticalScrollBarEnabled(false);
        setVerticalScrollbarOverlay(false);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        return settings;
    }

    public void webviewCancel() {
        loadUrl("javascript:close()");
        // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
        // destory()
        ViewParent parent = getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(this);
        }
        setWebChromeClient(null);
        setWebViewClient(null);
        clearCache(true);
        getSettings().setJavaScriptEnabled(false);
        clearHistory();
        clearView();
        removeAllViews();
        destroy();
    }

    public ValueCallback<Uri[]> getmUploadMessageAboveL() {
        return mUploadMessageAboveL;
    }

    public void setmUploadMessageAboveL(ValueCallback<Uri[]> mUploadMessageAboveL) {
        this.mUploadMessageAboveL = mUploadMessageAboveL;
    }
}
