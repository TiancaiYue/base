package cn.network.presenter;

import android.content.Context;

import cn.network.presenter.view.IMvpView;

/**
 * Created by base on 2020-02-28.
 */
public class ContextPresenter<IVIEW extends IMvpView> extends BasePresenter<IVIEW> {
    private Context mContext;

    public ContextPresenter(Context appContext) {
        mContext = appContext;
    }

    public Context getContext() {
        return mContext;
    }
}
