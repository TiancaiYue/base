package cn.network;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.network.interceptor.MyHttpLoggingInterceptor;
import cn.network.interceptor.SaveCookiesInterceptor;
import okhttp3.OkHttpClient;

/**
 * Created by base on 20/06/29.
 */
public class YoZoClient {
    private static YoZoClient instance = new YoZoClient();

    public static YoZoClient getInstance() {
        return instance;
    }

    public static OkHttpClient getOkHttpClient(Context context, boolean isNeedSaveCookie) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        MyHttpLoggingInterceptor loggingInterceptor = new MyHttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(MyHttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        if (isNeedSaveCookie) {
            builder.addInterceptor(new SaveCookiesInterceptor());
        }
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);

        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }
}
