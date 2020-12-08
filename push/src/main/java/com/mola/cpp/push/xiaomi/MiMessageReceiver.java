package com.mola.cpp.push.xiaomi;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.mola.cpp.push.MyMessage;
import com.mola.cpp.push.PushMain;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by base on 20/08/05.
 */
public class MiMessageReceiver extends PushMessageReceiver {
    public static final String TAG = "MiMessageReceiver";

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        Log.v(TAG, "[onReceivePassThroughMessage] " + message);
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        Log.v(TAG, "[onNotificationMessageClicked] " + message);
        try {
            PushMain.handleData(context);
        } catch (Throwable throwable) {
            Log.e(TAG, "[onNotifyMessageOpened] " + throwable.getMessage());
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        Log.v(TAG, "[onNotificationMessageArrived] " + message);
        Gson gson = new Gson();
        String extra = gson.toJson(message.getExtra());
        MyMessage myMessage = gson.fromJson(extra, MyMessage.class);
        if (myMessage == null) {
            return;
        }
        PushMain.setExtra(myMessage);
        EventBus.getDefault().post(myMessage);
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        Log.v(TAG, "[onCommandResult] " + message);
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        Log.v(TAG, "[onReceiveRegisterResult] " + message);
    }
}