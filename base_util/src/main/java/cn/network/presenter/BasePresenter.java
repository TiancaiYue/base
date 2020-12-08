package cn.network.presenter;

import cn.network.presenter.view.IMvpView;

/**
 * Created by base on 2020-01-14.
 */
public class BasePresenter<IVIEW extends IMvpView> {
    private IVIEW mMvpView;

    public BasePresenter() {
    }

    public void attachView(IVIEW view) {
        mMvpView = view;
    }

    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public IVIEW getMvpView() {
        return mMvpView;
    }
}
