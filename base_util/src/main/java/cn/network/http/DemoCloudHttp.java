package cn.network.http;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

import cn.network.YoZoClient;
import cn.network.model.AMBasePlusDto;
import cn.network.model.Empty;

/**
 * Created by base on 2020-01-15.
 */
public class DemoCloudHttp {
    private static DemoCloudHttp instance;

    private DemoCloudHttp() {
    }

    public static DemoCloudHttp getInstance() {
        if (instance == null) {
            synchronized (DemoCloudHttp.class) {
                return instance = new DemoCloudHttp();
            }
        }
        return instance;
    }

    /**
     * demo
     *
     * @param context
     * @return
     */
    public PostRequest<AMBasePlusDto<Empty>> setDemoHttp(Context context, String demo) {
        OkGo.getInstance().setOkHttpClient(YoZoClient.getOkHttpClient(context, true));
        PostRequest<AMBasePlusDto<Empty>> params = OkGo.<AMBasePlusDto<Empty>>post("demo_url")
                .tag(context)
                .params("demo", demo);
        return params;
    }
}
