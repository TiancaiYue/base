package cn.user_db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.BaseSetting;
import cn.network.model.UserCookie;
import cn.user_db.model.MolaUser;
import cn.user_db.model.QuotaInfo;
import cn.user_db.model.UserModel;
import cn.user_db.model.UserRightsResponse;
import cn.utils.YZConvertUtil;
import cn.utils.YZDateUtils;
import cn.utils.YZStringUtil;

public class UserCache {
    private static final String PREFS_NAME = "userCacheData";
    private static final String USER_KEY = "userInfo";
    public static final String USER_PASSAGE = "userPassage";

    public static void userCacheUpdate(UserModel user) {
        String userStr = YZConvertUtil.toJson(user);
        SharedPreferences settings = BaseSetting.getInstance().getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_KEY, userStr);
        editor.apply();
    }

    public static void userCacheClear() {
        SharedPreferences settings = BaseSetting.getInstance().getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(USER_KEY);
        editor.apply();
    }

    public static UserModel user() {
        SharedPreferences settings = BaseSetting.getInstance().getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userStr = settings.getString(USER_KEY, null);
        UserModel user = YZConvertUtil.fromJson(userStr, UserModel.class);
        if (user == null) {
            return new UserModel();
        } else {
            return user;
        }
    }

    public static void putUserInfo(String phone, String password) {
        SharedPreferences settings = BaseSetting.getInstance().getContext().getSharedPreferences(USER_PASSAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(phone, password);
        editor.apply();
    }

    public static void removeUserInfo(String phone) {
        SharedPreferences settings = BaseSetting.getInstance().getContext().getSharedPreferences(USER_PASSAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(phone);
        editor.apply();
    }

    public static String getSid() {
        return user().getSid();
    }

    public static void setSid() {
        UserModel userModel = user();
        userModel.setSid(YZDateUtils.getLongCurrentSystemTime() + "");
        userCacheUpdate(userModel);
    }

    public static String getSupportAudioFileFormat() {
        return user().getSupportAudioFileFormat();
    }

    public static void setSupportAudioFileFormat(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;
        UserModel userModel = user();
        userModel.setSupportAudioFileFormat(s);
        userCacheUpdate(userModel);
    }

    public static String getSupportVideoFileFormat() {
        return user().getSupportVideoFileFormat();
    }

    public static void setSupportVideoFileFormat(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;
        UserModel userModel = user();
        userModel.setSupportVideoFileFormat(s);
        userCacheUpdate(userModel);
    }

    public static String getYozoCookie() {
        return user().getYozoCookie();
    }

    public static void setYozoCookie(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;
        UserModel userModel = user();
        userModel.setYozoCookie(s);
        userCacheUpdate(userModel);
    }

    public static String getSsoCookie() {
        return user().getSsoCookie();
    }

    public static void setSsoCookie(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;

        UserModel userModel = user();
        userModel.setSsoCookie(s);
        userCacheUpdate(userModel);
    }

    public static String getCookie() {
        return user().getCookie();
    }

    public static void setCookie(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;

        UserModel userModel = user();
        userModel.setCookie(s);
        userCacheUpdate(userModel);
    }

    public static void setLoginUserType(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;

        UserModel userModel = user();
        userModel.setLoginUserType(s);
        userCacheUpdate(userModel);
    }

    public static String getLoginUserType() {
        return user().getLoginUserType();
    }

    public static void setShowLocalTip(boolean b) {
        UserModel userModel = user();
        userModel.setShowLocalTip(b);
        userCacheUpdate(userModel);
    }

    public static boolean getShowLocalTip() {
        return user().isShowLocalTip();
    }

    public static void setIsUpload(boolean b) {
        UserModel userModel = user();
        userModel.setStartUpload(b);
        userCacheUpdate(userModel);
    }

    public static boolean getIsUpload() {
        return user().isStartUpload();
    }

    public static void setIsEnterprise(boolean b) {
        UserModel userModel = user();
        userModel.setEnterprise(b);
        userCacheUpdate(userModel);
    }

    public static boolean getIsEnterprise() {
        return user().isEnterprise();
    }

    public static void setUserEmail(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;

        UserModel userModel = user();
        userModel.setUserEmail(s);
        userCacheUpdate(userModel);
    }

    public static String getUserEmail() {
        return user().getUserEmail();
    }

    public static void setPhone(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;

        UserModel userModel = user();
        userModel.setPhone(s);
        userCacheUpdate(userModel);
    }

    public static String getPhone() {
        return user().getPhone();
    }

    public static void setPassword(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;

        UserModel userModel = user();
        userModel.setPassword(s);
        userCacheUpdate(userModel);
    }

    public static String getPassword() {
        return user().getPassword();
    }

    public static void setAccount(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;

        UserModel userModel = user();
        userModel.setAccount(s);
        userCacheUpdate(userModel);
    }

    public static String getAccount() {
        return user().getAccount();
    }

    public static void setKeepUploadChoose(boolean b) {
        UserModel userModel = user();
        userModel.setKeepUploadChoose(b);
        userCacheUpdate(userModel);
    }

    public static boolean getKeepUploadChoose() {
        return user().isKeepUploadChoose();
    }

    public static void setCloseAdvTime(String s) {
        if (!YZStringUtil.isNotEmpty(s, true)) return;

        UserModel userModel = user();
        userModel.setCloseAdvTime(s);
        userCacheUpdate(userModel);
    }

    public static String getCloseAdvTime() {
        return user().getCloseAdvTime();
    }

    public static void setQuotaInfo(QuotaInfo quotaInfo) {
        if (quotaInfo == null) return;
        UserModel userModel = user();
        userModel.setQuotaInfo(YZConvertUtil.toJson(quotaInfo));
        userCacheUpdate(userModel);
    }

    public static QuotaInfo getQuotaInfo() {
        return YZConvertUtil.fromJson(user().getQuotaInfo(), QuotaInfo.class);
    }

    public static void setUserRightsResponse(String userRightsResponse) {
        if (userRightsResponse == null) return;
        UserModel userModel = user();
        userModel.setUserRightsResponse(userRightsResponse);
        userCacheUpdate(userModel);
    }

    public static UserRightsResponse getUserRightsResponse() {
        return YZConvertUtil.fromJson(user().getUserRightsResponse(), UserRightsResponse.class);
    }

    public static void setCurrentUser(MolaUser enterpriseInfo) {
        if (enterpriseInfo == null) return;

        UserModel userModel = user();
        if (UserCache.getCurrentUser() != null && !YZStringUtil.isEmpty(UserCache.getCurrentUser().getAvatarChange()) && YZStringUtil.isEmpty(enterpriseInfo.getAvatarChange())) {
            enterpriseInfo.setAvatarChange(UserCache.getCurrentUser().getAvatarChange());
        }
        userModel.setCurrentUser(YZConvertUtil.toJson(enterpriseInfo));
        userCacheUpdate(userModel);
    }

    public static MolaUser getCurrentUser() {
        return YZConvertUtil.fromJson(user().getCurrentUser(), MolaUser.class);
    }

    public static void setYozoCookieModel(UserCookie userCookie) {
        if (userCookie == null) return;

        UserModel userModel = user();
        userModel.setYozoCookieModel(YZConvertUtil.toJson(userCookie));
        userCacheUpdate(userModel);
    }

    public static UserCookie getYozoCookieModel() {
        return YZConvertUtil.fromJson(user().getYozoCookieModel(), UserCookie.class);
    }

    public static void setCookieModel(UserCookie userCookie) {
        if (userCookie == null) return;

        UserModel userModel = user();
        userModel.setCookieModel(YZConvertUtil.toJson(userCookie));
        userCacheUpdate(userModel);
    }

    public static UserCookie getCookieModel() {
        return YZConvertUtil.fromJson(user().getCookieModel(), UserCookie.class);
    }
}
