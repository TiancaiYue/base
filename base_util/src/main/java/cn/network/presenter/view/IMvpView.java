package cn.network.presenter.view;

/**
 * Created by base on 2020-01-14.
 */
public interface IMvpView {
    void onException(String throwable);

    void onFinish();
}
