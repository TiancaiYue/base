package cn.base;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.base.base_util.R;
import cn.widget.YZYoZoWebView;

public class YoZoWebViewActivity extends BaseActivity {
    public static String UrlKey = "url";
    private YZYoZoWebView webView;

    public static void showActivity(Context context, String url) {
        Intent intent = new Intent(context, YoZoWebViewActivity.class);
        intent.putExtra(UrlKey, url);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_yozo_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        webView = findViewById(R.id.yozo_webview);
    }

    @Override
    public void initData() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//忽略ssl证书错误,继续加载网页
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(WebView.SCHEME_TEL) || url.startsWith("sms:") || url.startsWith(WebView.SCHEME_MAILTO)) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (ActivityNotFoundException ignored) {
                    }
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });

        Intent intent = getIntent();
        String url = intent.getStringExtra(UrlKey);
        webView.loadUrl(url);
    }

    @Override
    public void initEvent() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.webviewCancel();
    }
}
