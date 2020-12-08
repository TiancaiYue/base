package cn.network.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;

import cn.network.HttpNewsCallback;
import cn.network.http.DemoCloudHttp;
import cn.network.model.AMBasePlusDto;
import cn.network.model.Empty;
import cn.network.presenter.view.IDemoView;

/**
 * Created by base on 2020-02-28.
 */
public class DemoCloudPresenter extends ContextPresenter<IDemoView> {
    private DemoCloudHttp mDemoCloudHttp;

    public DemoCloudPresenter(Context appContext) {
        super(appContext);
        mDemoCloudHttp = DemoCloudHttp.getInstance();
    }

    /**
     * sso企业登录
     *
     * @param demo
     */
    public void setDemoHttp(String demo) {
        mDemoCloudHttp.setDemoHttp(getContext(), demo).execute(new HttpNewsCallback<AMBasePlusDto<Empty>>(getContext(), 1, 0) {
            @Override
            public void onSuccess(Response<AMBasePlusDto<Empty>> response) {

            }

            @Override
            public void onError(Response<AMBasePlusDto<Empty>> response) {
                super.onError(response);
            }
        });
    }
}
