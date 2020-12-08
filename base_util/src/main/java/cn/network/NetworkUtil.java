package cn.network;

import com.lzy.okgo.request.base.Request;

import cn.network.model.AMBaseDto;
import cn.network.model.ErrorBody;
import cn.user_db.UserCache;
import cn.utils.YZBaseUtil;
import cn.utils.YZConvertUtil;
import cn.utils.YZStringUtil;
import okhttp3.Response;

/**
 * Created by base on 2020/5/5.
 */
public class NetworkUtil {

    public static ErrorBody networkCodeCallback(Response response) {
        String errorMessage = ""; // 错误信息
        int errorCode = 0; // 错误码
        ErrorBody errorBody; // 错误的实体类
        switch (response.code()) {
            case 200:
                break;
            case 400:
                if (response.body() != null) {
                    errorBody = YZConvertUtil.fromJson(response, ErrorBody.class);
                    errorCode = errorBody.getCode();
                    if (YZStringUtil.isNotEmpty(errorBody.getMsg(), true)) {
                        errorMessage = errorBody.getMsg();
                    } else if (YZStringUtil.isNotEmpty(errorBody.getMessage(), true)) {
                        errorMessage = errorBody.getMessage();
                    } else if (YZStringUtil.isNotEmpty(errorBody.getErrorMessage(), true)) {
                        errorMessage = errorBody.getErrorMessage();
                    } else {
                        errorMessage = "服务器开小差了~";
                    }
                }
                break;
            case 403:
                errorCode = 403;
                errorMessage = "权限错误";
                break;
            case 502:
                errorCode = 502;
                errorMessage = "服务未就绪，请稍后再试";
                break;
            case 415:
                errorCode = 415;
                errorMessage = "服务器无法处理请求附带的媒体格式";
                break;
            case 501:
                errorCode = 501;
                errorMessage = "服务器连接超时";
                break;
            case 500:
                errorCode = 500;
                errorMessage = "服务器错误";
                break;
            default:
                errorCode = -1;
                errorMessage = "未知错误";
                break;
        }
        errorBody = new ErrorBody();
        errorBody.setCode(errorCode);
        errorBody.setMessage(errorMessage);
        return errorBody;
    }

    public static ErrorBody responseCodeCallback(AMBaseDto amBaseDto) {
        ErrorBody errorBody = new ErrorBody(); // 错误的实体类
        if (amBaseDto.getCode() != 0) {
            errorBody.setCode(amBaseDto.getCode());
        }
        if (amBaseDto.getErrorcode() != 0) {
            errorBody.setCode(amBaseDto.getErrorcode());
        }
        if (amBaseDto.getErrorCode() != 0) {
            errorBody.setCode(amBaseDto.getErrorCode());
        }
        if (errorBody.getCode() != 0) {
            if (YZStringUtil.isNotEmpty(amBaseDto.getMessage(), true)) {
                errorBody.setMessage(amBaseDto.getMessage());
            } else if (YZStringUtil.isNotEmpty(amBaseDto.getMsg(), true)) {
                errorBody.setMessage(amBaseDto.getMsg());
            } else if (YZStringUtil.isNotEmpty(amBaseDto.getErrorMessage(), true)) {
                errorBody.setMessage(amBaseDto.getErrorMessage());
            } else {
                errorBody.setMessage("服务器开小差了~");
            }
        }
        return errorBody;
    }

    /**
     * -1cookie和token都不要 0cookie和token都要 1要cookie 2要token
     *
     * @param request
     * @param mIsNeedToken
     */
    public static void setHead(Request request, int mIsNeedToken) {
        if (request.getUrl().contains(Url.getUrlYoZo(false))) {
            if (mIsNeedToken != -1 && mIsNeedToken != 2) {
                if (!YZStringUtil.isEmpty(UserCache.getYozoCookie())) {
                    request.headers("Cookie", UserCache.getYozoCookie());
                } else {
                    request.headers("Cookie", UserCache.getCookie());
                }
            }
            if (mIsNeedToken != -1 && mIsNeedToken != 1) {
                if (UserCache.getYozoCookieModel() != null) {
                    request.headers("ACCESS-TOKEN", UserCache.getYozoCookieModel().getAccess_token());
                    request.headers("REFRESH-TOKEN", UserCache.getYozoCookieModel().getRefresh_token());
                } else {
                    if (UserCache.getCookieModel() != null) {
                        request.headers("ACCESS-TOKEN", UserCache.getCookieModel().getAccess_token());
                        request.headers("REFRESH-TOKEN", UserCache.getCookieModel().getRefresh_token());
                    }
                }
            }
        } else {
            if (!YZStringUtil.isEmpty(UserCache.getCookie()) && mIsNeedToken != -1 && mIsNeedToken != 2) {
                request.headers("Cookie", UserCache.getCookie());
            }
            if (UserCache.getCookieModel() != null && mIsNeedToken != -1 && mIsNeedToken != 1) {
                request.headers("ACCESS-TOKEN", UserCache.getCookieModel().getAccess_token());
                request.headers("REFRESH-TOKEN", UserCache.getCookieModel().getRefresh_token());
            }
        }

        request.headers("User-Agent", YZBaseUtil.getPhoneNameString());
    }
}
