package com.mola.cpp.push.huawei;

import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.mola.cpp.push.PushMain;

public class HuaweiPushService extends HmsMessageService {
    private static final String TAG = "HuaweiPushService";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.v(TAG, "receive token:" + token);
        PushMain.setToken(token);
    }

    @Override
    public void onTokenError(Exception e) {
        super.onTokenError(e);
        Log.v(TAG, "onTokenError: " + e.getMessage());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // 透传消息
        if (remoteMessage.getData().length() > 0) {
            Log.v(TAG, "Message data payload: " + remoteMessage.getData());
        }
        // 通知栏消息
        if (remoteMessage.getNotification() != null) {
            Log.v(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onMessageSent(String s) {
        Log.v(TAG, "onMessageSent: " + s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        Log.v(TAG, "onSendError: " + e.getMessage());
    }

    @Override
    public void onMessageDelivered(String s, Exception e) {
        super.onMessageDelivered(s, e);
        Log.v(TAG, "onMessageDelivered: " + s + "," + e.getMessage());
    }
}