package cn.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.widget.YZCommonDialog;
import io.reactivex.functions.Consumer;

public class YZPermissionUtil {
    public interface IPermissionSuccess {
        void onPermission();
    }

    //相机相册权限
    @SuppressLint("CheckResult")
    public static void photoAndCamraRxpermission(final Activity activity, final IPermissionSuccess i) {
        Log.i("PermissionUtil1", activity.getLocalClassName());
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_WIFI_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {//同意
                            i.onPermission();
                        } else {
                            YZCommonDialog commonDialog = new YZCommonDialog(activity);
                            commonDialog.setContent("使用该功能需要允许开启相机、存储权限。");
                            commonDialog.setLeftBtnText("取消");
                            commonDialog.setRightBtnText("设置");
                            commonDialog.setRightListener(v -> {
                                commonDialog.dismiss();
                                boolean hasrefuse1 = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA);
                                boolean hasrefuse2 = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                boolean hasrefuse3 = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
                                boolean hasrefuse4 = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION);
                                boolean hasrefuse5 = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_WIFI_STATE);
                                if (!hasrefuse1 || !hasrefuse2 || !hasrefuse3 || !hasrefuse4 || !hasrefuse5) {//不同意跳转到详情页
                                    YZPermissionPageUtils permissionPageUtils = new YZPermissionPageUtils(activity);
                                    permissionPageUtils.goIntentSetting();
                                } else
                                    photoRxpermission(activity, i);
                            });
                            commonDialog.show();
                        }
                    }
                });
    }

    //相机权限
    @SuppressLint("CheckResult")
    public static void photoRxpermission(final Activity activity, final IPermissionSuccess i) {
        Log.i("PermissionUtil1", activity.getLocalClassName());
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {//同意
                            i.onPermission();
                        } else {
                            YZCommonDialog commonDialog = new YZCommonDialog(activity);
                            commonDialog.setContent("使用该功能需要允许开启相机、存储权限。");
                            commonDialog.setLeftBtnText("取消");
                            commonDialog.setRightBtnText("设置");
                            commonDialog.setRightListener(v -> {
                                commonDialog.dismiss();
                                boolean hasrefuse1 = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA);
                                boolean hasrefuse2 = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                if (!hasrefuse1 || !hasrefuse2) {//不同意跳转到详情页
                                    YZPermissionPageUtils permissionPageUtils = new YZPermissionPageUtils(activity);
                                    permissionPageUtils.goIntentSetting();
                                } else
                                    photoRxpermission(activity, i);
                            });
                            commonDialog.show();
                        }
                    }
                });
    }

    //读写权限
    @SuppressLint("CheckResult")
    public static void readwriteRxpermission(final Activity activity, final IPermissionSuccess i) {
        Log.i("PermissionUtil2", activity.getLocalClassName());
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {//同意
                            i.onPermission();
                        } else {
                            YZCommonDialog commonDialog = new YZCommonDialog(activity);
                            commonDialog.setContent("为了正常浏览、保存文档，请打开设置并允许优云获取存储权限。");
                            commonDialog.setLeftBtnText("取消");
                            commonDialog.setRightBtnText("设置");
                            commonDialog.setRightListener(v -> {
                                commonDialog.dismiss();
                                boolean hasrefuse = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO);
                                if (!hasrefuse) {//不同意跳转到详情页
                                    YZPermissionPageUtils permissionPageUtils = new YZPermissionPageUtils(activity);
                                    permissionPageUtils.goIntentSetting();
                                } else
                                    readwriteRxpermission(activity, i);
                            });
                            commonDialog.show();
                        }
                    }
                });
    }

    //录音权限
    @SuppressLint("CheckResult")
    public static void recordAudioRxpermission(final Activity activity, final IPermissionSuccess i) {
        Log.i("PermissionUtil3", activity.getLocalClassName());
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {//同意
                            i.onPermission();
                        } else {
                            YZCommonDialog commonDialog = new YZCommonDialog(activity);
                            commonDialog.setContent("使用该功能需要麦克风权限进行声音录制，请允许开启录音权限。");
                            commonDialog.setLeftBtnText("取消");
                            commonDialog.setRightBtnText("设置");
                            commonDialog.setRightListener(v -> {
                                commonDialog.dismiss();
                                boolean hasrefuse = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO);
                                if (!hasrefuse) {//不同意跳转到详情页
                                    YZPermissionPageUtils permissionPageUtils = new YZPermissionPageUtils(activity);
                                    permissionPageUtils.goIntentSetting();
                                } else
                                    recordAudioRxpermission(activity, i);
                            });
                            commonDialog.show();
                        }
                    }
                });
    }
}
