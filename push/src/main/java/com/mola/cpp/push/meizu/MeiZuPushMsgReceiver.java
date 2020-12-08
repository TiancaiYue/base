package com.mola.cpp.push.meizu;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

public class MeiZuPushMsgReceiver extends MzPushMessageReceiver {
    public static final String TAG = "MyPushMsgReceiver";

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        Log.e(TAG, "onPushStatus " + pushSwitchStatus);
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        Log.e(TAG, "onRegisterStatus " + registerStatus);
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        Log.e(TAG, "onUnRegisterStatus =" + unRegisterStatus);

    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
        Log.e(TAG, "onSubTagsStatus =" + subTagsStatus);

    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        Log.e(TAG, "onSubAliasStatus =" + subAliasStatus);

    }

    @Override
    public void onNotifyMessageArrived(Context context, String message) {
        super.onNotifyMessageArrived(context, message);
        Log.e(TAG, "onNotifyMessageArrived =" + message);
    }

    @Override
    public void onMessage(Context context, String message) {
        Log.d(TAG, "onMessage = " + message);
    }

    @Override
    public void onMessage(Context context, Intent intent) {

    }
}
