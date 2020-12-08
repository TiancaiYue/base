package cn.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.network.model.AMBaseDto;
import cn.network.model.AMBasePlusDto;
import cn.network.model.ErrorBody;
import cn.utils.YZConvertUtil;
import cn.utils.YZProgressDialogWork;
import cn.utils.YZStringUtil;
import cn.utils.YZToastUtil;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * http应答处理
 * Created by base on 20/01/16.
 */
public abstract class HttpNewsCallback<T> extends AbsCallback<T> {
    private Context mContext = null;
    private int mShowType; // -1:不显示dialog  0:showDialog  1:设置自定义title和message 2:设置自定义title和message以及是否可关闭
    private boolean mIsCloseToast; // 默认显示错误信息的toast （当服务器返回的message不是我们前台展示的信息的时候，设置此值为true）
    private String mTitle;
    private String mMessage;
    private boolean mCanceled;
    private int mIsNeedToken; // -1cookie和token都不要 0cookie和token都要 1要cookie 2要token

    public HttpNewsCallback(Context context, int isNeedToken, int showType) {
        mContext = context;
        mShowType = showType;
        mIsNeedToken = isNeedToken;
    }

    public HttpNewsCallback(Context context, int isNeedToken, int showType, boolean isCloseToast) {
        mContext = context;
        mShowType = showType;
        mIsCloseToast = isCloseToast;
        mIsNeedToken = isNeedToken;
    }

    public HttpNewsCallback(Context context, int isNeedToken, int showType, boolean isCloseToast, String title, String message) {
        mContext = context;
        mShowType = showType;
        mIsCloseToast = isCloseToast;
        mTitle = title;
        mMessage = message;
        mIsNeedToken = isNeedToken;
    }

    public HttpNewsCallback(Context context, int isNeedToken, int showType, boolean isCloseToast, String title, String message, boolean canceled) {
        mContext = context;
        mShowType = showType;
        mIsCloseToast = isCloseToast;
        mTitle = title;
        mMessage = message;
        mCanceled = canceled;
        mIsNeedToken = isNeedToken;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        switch (mShowType) {
            case 0:
                YZProgressDialogWork.getInstance(mContext).showProgressDialog("提示", "加载中...");
                break;
            case 1:
                YZProgressDialogWork.getInstance(mContext).showProgressDialog(mTitle, mMessage);
                break;
            case 2:
                YZProgressDialogWork.getInstance(mContext).showProgressDialog(mTitle, mMessage, mCanceled);
                break;
        }
        NetworkUtil.setHead(request, mIsNeedToken);
    }

    @Override
    public T convertResponse(Response response) {
        //以下代码是通过泛型解析实际参数,泛型必须传
        Type genType = getClass().getGenericSuperclass();
        assert genType != null;
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        // 服务器是否有问题
        ErrorBody errorBody = NetworkUtil.networkCodeCallback(response);
        if (errorBody.getCode() != 0) {
            throw new IllegalStateException(YZConvertUtil.toJson(errorBody));
        }
        ResponseBody body = response.body();
        if (body == null || body.charStream() == null) {
            return null;
        }
        T successResponse;
        if (!(type instanceof ParameterizedType)) {
            successResponse = YZConvertUtil.fromJson(response, type);
            if (successResponse instanceof AMBaseDto) {
                if (NetworkUtil.responseCodeCallback((AMBaseDto) successResponse).getCode() != 0) {
                    throw new IllegalStateException(YZConvertUtil.toJson(NetworkUtil.responseCodeCallback((AMBaseDto) successResponse)));
                }
            }
        } else {
            successResponse = YZConvertUtil.fromJson(response, type);
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType == AMBasePlusDto.class) {
                if (NetworkUtil.responseCodeCallback((AMBaseDto) successResponse).getCode() != 0) {
                    throw new IllegalStateException(YZConvertUtil.toJson(NetworkUtil.responseCodeCallback((AMBaseDto) successResponse)));
                }
            }
        }
        response.close();
        return successResponse;
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Throwable throwable = response.getException();
        if (throwable != null) throwable.printStackTrace();

        if (throwable instanceof UnknownHostException || throwable instanceof ConnectException) {
            Log.e("NewsCallback", "UnknownHostException========" + mContext.getClass().getSimpleName());

            if (!mIsCloseToast) {
                YZToastUtil.showMessage(mContext, "网络连接失败，请连接网络~");
            }
        } else if (throwable instanceof SocketTimeoutException) {
            Log.e("NewsCallback", "SocketTimeoutException========" + mContext.getClass().getSimpleName());

            if (!mIsCloseToast) {
                YZToastUtil.showMessage(mContext, "网络请求超时~");
            }
        } else if (throwable instanceof HttpException) {
            Log.e("NewsCallback", "HttpException========" + mContext.getClass().getSimpleName());
        } else if (throwable instanceof StorageException) {
            Log.e("NewsCallback", "StorageException========" + mContext.getClass().getSimpleName());

            if (!mIsCloseToast) {
                YZToastUtil.showMessage(mContext, "没有存储权限");
            }
        } else if (throwable instanceof JsonIOException || throwable instanceof JsonSyntaxException) {
            Log.e("NewsCallback", "JsonIOException========" + mContext.getClass().getSimpleName());
        } else if (throwable instanceof IllegalStateException) {
            ErrorBody errorBody = YZConvertUtil.fromJson(throwable.getMessage(), ErrorBody.class);
            if (YZStringUtil.isNotEmpty(errorBody.getMessage(), true)) {
                if (!mIsCloseToast) {
                    YZToastUtil.showMessage(mContext, errorBody.getMessage());
                } else {
                    Log.v("PomeloNewsCallback", errorBody.getMessage());
                }
            }
        }
    }

    @Override
    public void onFinish() {
        if (mShowType != -1) {
            YZProgressDialogWork.getInstance().closeProgressDialog();
        }
    }
}
