package com.mola.cpp.push;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.HmsMessaging;
import com.meizu.cloud.pushsdk.PushManager;
import com.vivo.push.PushClient;
import com.vivo.push.ups.CodeResult;
import com.vivo.push.ups.TokenResult;
import com.vivo.push.ups.UPSRegisterCallback;
import com.vivo.push.ups.UPSTurnCallback;
import com.vivo.push.ups.VUpsManager;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.HashSet;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by base on 2020/08/05.
 */
public class PushMain {
    public static final String TAG = "PushMain";
    private static PushMain INST = new PushMain();
    private static String mApplicationId;
    private static MyMessage mExtra;
    private static String mToken;

    public static void setExtra(MyMessage extra) {
        PushMain.mExtra = extra;
    }

    public static void setToken(String mToken) {
        PushMain.mToken = mToken;
    }

    public static String getToken() {
        return mToken;
    }

    /**
     * 处理数据
     *
     * @param context
     */
    public static void handleData(Context context) {
        if (mExtra != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(TAG, mExtra);
            Log.v(TAG, "onNotifyMessageOpened:" + mExtra.toString());
//            showMainActivity(".main", context, bundle);
//            PushMain.setExtra(null);
        } else {
//            PushMain.showMainActivity(".main", context, null);
        }
    }

    /**
     * 跳转首页
     *
     * @param context
     */
    public static void showMainActivity(String activityName, Context context, Bundle bundle) {
        String action = mApplicationId + activityName;
        Intent intent = new Intent(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    /**
     * 注册推送
     *
     * @param application
     * @param applicationId
     */
    public static void init(Application application, String applicationId) {
        PushMain.mApplicationId = applicationId;
        INST.initJPush(application);

        if (RomUtil.isMiui()) {
            INST.initXiaomi(application);
        } else if (RomUtil.isFlyme()) {
            INST.initMZ(application);
        } else if (RomUtil.isOppo()) {
        } else if (RomUtil.isVivo()) {
        } else if (RomUtil.isEmui()) {
        } else {
            INST.initJPush(application);
        }
    }

    /**
     * 注册设置别名
     *
     * @param context
     * @param currentUser
     */
    public static String addTag(Context context, String currentUser) {
        String type = "";
        Log.e(TAG, "currentUser=" + currentUser);

        if (RomUtil.isMiui()) {
            type = "mi";
            INST.setTagXiaomi(context, currentUser, true);
        } else if (RomUtil.isFlyme()) {
            type = "flyme";
            INST.setTagMZ(context, currentUser, true);
        } else if (RomUtil.isOppo()) {
            type = "oppo";
            INST.setTagOPPO(context, currentUser, true);
        } else if (RomUtil.isVivo()) {
            type = "vivo";
            INST.setTagVIVO(context, currentUser, true);
        } else if (RomUtil.isEmui()) {
            type = "huawei";
            INST.setTagHuaWei(context, currentUser, true);
        } else {
            type = "jpush";
            INST.setTagJPush(context, currentUser, true);
        }
        return type;
    }

    /**
     * 清除别名
     *
     * @param context
     */
    public static void clearPush(Context context, String currentUser) {
        if (RomUtil.isMiui()) {
            INST.setTagXiaomi(context, currentUser, false);
        } else if (RomUtil.isFlyme()) {
            INST.setTagMZ(context, currentUser, false);
        } else if (RomUtil.isOppo()) {
            INST.setTagOPPO(context, currentUser, false);
        } else if (RomUtil.isVivo()) {
            INST.setTagVIVO(context, currentUser, false);
        } else if (RomUtil.isEmui()) {
            INST.setTagHuaWei(context, currentUser, false);
        } else {
            INST.setTagJPush(context, currentUser, false);
        }
    }

    //*****************************************各个厂商推送的注册***************************************************/
    private void initJPush(Context context) {
        try {
            JPushInterface.init(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean initMZ(Context context) {
        String packageName = context.getPackageName();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                String appId = appInfo.metaData.get("MEIZU_PUSH_APP_ID") + "";
                String appKey = appInfo.metaData.getString("MEIZU_PUSH_APP_KEY");
                if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(appKey)) {
                    com.meizu.cloud.pushsdk.PushManager.register(context, appId, appKey);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    private boolean initXiaomi(Context context) {
        String packageName = context.getPackageName();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                String appid = appInfo.metaData.getString("MIPUSH_APPID").substring(7);
                String appkey = appInfo.metaData.getString("MIPUSH_APPKEY").substring(7);
                if (!TextUtils.isEmpty(appid) && !TextUtils.isEmpty(appkey)) {
                    if (shouldInitXiaomi(context)) {
                        MiPushClient.registerPush(context, appid, appkey);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean shouldInitXiaomi(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
    //*****************************************各个厂商推送的设置别名***************************************************/

    private void setTagJPush(Context context, String currentUser, boolean isSetTag) {
        try {
            if (isSetTag) {
                HashSet<String> tagSet = new HashSet();
                tagSet.add(currentUser);
                JPushInterface.addTags(context, 1101, tagSet);
            } else {
                JPushInterface.cleanTags(context, 1101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTagOPPO(Context context, String currentUser, boolean isSetTag) {
        try {
            if (isSetTag) {
                String packageName = context.getPackageName();
                try {
                    ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                    if (appInfo.metaData != null) {
                        String appSecret = "" + appInfo.metaData.get("OPPO_PUSH_SEC");
                        String appKey = appInfo.metaData.getString("OPPO_PUSH_KEY");
                        HeytapPushManager.init(context, true);
                        HeytapPushManager.register(context, appKey, appSecret, mPushCallback);
                        HeytapPushManager.requestNotificationPermission();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                HeytapPushManager.unRegister();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTagVIVO(Context context, String currentUser, boolean isSetTag) {
        if (isSetTag) {
            VUpsManager.getInstance().turnOnPush(context, codeResult -> Log.d(TAG, "初始化成功"));
            VUpsManager.getInstance().registerToken(context, "100678872", "14a8403f1dcb60cd2692f273a0085ed6", "1e4d29e1-3d55-412f-a852-0f6dd7607c18", tokenResult -> {
                if (tokenResult.getReturnCode() == 0) {
                    Log.v(TAG, "注册成功,注册的Token为" + tokenResult.getToken());
                } else {
                    Log.v(TAG, "注册失败");
                }
            });
        } else {
            VUpsManager.getInstance().unRegisterToken(context, tokenResult -> {
                if (tokenResult.getReturnCode() == 0) {
                    Log.d(TAG, "解注册成功");
                } else {
                    Log.d(TAG, "解注册失败");
                }
            });
            VUpsManager.getInstance().turnOffPush(context, codeResult -> {
                if (codeResult.getReturnCode() == 0) {
                    Log.d(TAG, "关闭成功");
                } else {
                    Log.d(TAG, "关闭失败");
                }
            });
        }
    }

    private void setTagMZ(Context context, String currentUser, boolean isSetTag) {
        String packageName = context.getPackageName();
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                String appId = appInfo.metaData.get("MEIZU_PUSH_APP_ID") + "";
                String appKey = appInfo.metaData.getString("MEIZU_PUSH_APP_KEY");
                if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(appKey)) {
                    if (isSetTag) {
                        com.meizu.cloud.pushsdk.PushManager.subScribeAlias(context, appId, appKey, PushManager.getPushId(context), currentUser);
                        Log.e(TAG, "注册成功:" + appId + "," + appKey + "," + PushManager.getPushId(context));
                    } else {
                        com.meizu.cloud.pushsdk.PushManager.unSubScribeAlias(context, appId, appKey, PushManager.getPushId(context), currentUser);
                        Log.e(TAG, "注销成功" + appId + "," + appKey);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setTagXiaomi(Context context, String currentUser, boolean isSetTag) {
        try {
            if (isSetTag) {
                MiPushClient.setAlias(context, currentUser, 1101 + "");
            } else {
                MiPushClient.unsetAlias(context, currentUser, 1101 + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTagHuaWei(Context context, String currentUser, boolean isSetTag) {
        if (isSetTag) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        String appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id");
                        String token = HmsInstanceId.getInstance(context).getToken(appId, "HCM");
                        Log.i(TAG, "get token:" + token);
                        if (!TextUtils.isEmpty(token)) {
                            sendRegTokenToServer(token);
                        }
                    } catch (ApiException e) {
                        Log.e(TAG, "get token failed, " + e);
                    }
                }
            }.start();
        } else {
            new Thread() {
                @Override
                public void run() {
                    try {
                        String appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id");
                        HmsInstanceId.getInstance(context).deleteToken(appId, "HCM");
                        Log.i(TAG, "deleteToken success.");
                    } catch (ApiException e) {
                        Log.e(TAG, "deleteToken failed." + e);
                    }
                }
            }.start();
        }
    }

    private void sendRegTokenToServer(String token) {
        Log.i(TAG, "sending token to server. token:" + token);
    }

    /**
     * 开的应用获取消息
     *
     * @param intent
     * @return
     */
    public static MyMessage getIntentData(Intent intent) {
        MyMessage myMessage = null;
        if (null != intent) {
            // 开发者可以通过如下三行代码获取的值做打点统计
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    String content = bundle.getString(key);
                    if (key.equals("extra")) {
                        try {
                            Gson gson = new Gson();
                            myMessage = gson.fromJson(content, MyMessage.class);
                        } catch (Exception e) {
                            Log.v(TAG, "Exception = " + e.getMessage());
                        }
                    }
                    Log.v(TAG, "receive data from push, key = " + key + ", content = " + content);
                }
            }
        } else {
            Log.v(TAG, "intent is null");
        }
        return myMessage;
    }

    private ICallBackResultService mPushCallback = new ICallBackResultService() {
        @Override
        public void onRegister(int code, String s) {
            if (code == 0) {
                Log.v(TAG, "注册成功registerId:" + s);
            } else {
                Log.v(TAG, "注册失败code=" + code + ",msg=" + s);
            }
        }

        @Override
        public void onUnRegister(int code) {
            if (code == 0) {
                Log.v(TAG, "注销成功code=" + code);
            } else {
                Log.v(TAG, "注销失败code=" + code);
            }
        }

        @Override
        public void onGetPushStatus(final int code, int status) {
            if (code == 0 && status == 0) {
                Log.v(TAG, "Push状态正常code=" + code + ",status=" + status);
            } else {
                Log.v(TAG, "Push状态错误code=" + code + ",status=" + status);
            }
        }

        @Override
        public void onGetNotificationStatus(final int code, final int status) {
            if (code == 0 && status == 0) {
                Log.v(TAG, "通知状态正常code=" + code + ",status=" + status);
            } else {
                Log.v(TAG, "通知状态错误code=" + code + ",status=" + status);
            }
        }

        @Override
        public void onSetPushTime(final int code, final String s) {
            Log.v(TAG, "SetPushTime,code=" + code + ",result:" + s);
        }
    };
}