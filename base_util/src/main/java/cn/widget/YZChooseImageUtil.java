package cn.widget;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Environment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.base.base_util.R;
import cn.utils.YZGlideUtil;
import cn.utils.YZPermissionUtil;
import cn.utils.YZStringUtil;

public class YZChooseImageUtil {
    /**
     * 打开相册
     *
     * @param activity
     * @param maxNum
     */
    public static void openPicture(Activity activity, int maxNum) {
        YZPermissionUtil.photoAndCamraRxpermission(activity, () -> {
            PictureSelector.create(activity)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .imageEngine(YZGlideUtil.createGlideEngine())// 外部传入图片加载引擎，必传项
                    .setOutputCameraPath(Environment.getExternalStorageDirectory() +  File.separator + "yozocloud" + File.separator)
                    .maxSelectNum(maxNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .selectionMode(maxNum == 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选
                    .isCompress(true)// 是否压缩
                    .cutOutQuality(90)// 裁剪输出质量 默认100
                    .minimumCompressSize(100)// 小于多少kb的图片不压缩
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .isSingleDirectReturn(maxNum != 1)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        });
    }

    /**
     * 打开相机
     *
     * @param activity
     * @param maxNum
     */
    public static void openCarma(Activity activity, int maxNum) {
        YZPermissionUtil.photoAndCamraRxpermission(activity, () -> {
            PictureSelector.create(activity)
                    .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                    .imageEngine(YZGlideUtil.createGlideEngine())// 外部传入图片加载引擎，必传项
                    .setOutputCameraPath(Environment.getExternalStorageDirectory() +  File.separator + "yozocloud" + File.separator)
                    .maxSelectNum(maxNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .selectionMode(maxNum == 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选
                    .isCompress(true)// 是否压缩
                    .cutOutQuality(90)// 裁剪输出质量 默认100
                    .minimumCompressSize(100)// 小于多少kb的图片不压缩
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .isSingleDirectReturn(maxNum != 1)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        });
    }

    /**
     * 打开相册相机
     *
     * @param activity
     * @param maxNum
     * @param selectionData
     * @param listener
     */
    public static void openPhoto(Activity activity, int maxNum, List<LocalMedia> selectionData, OnResultCallbackListener listener) {
        YZPermissionUtil.photoAndCamraRxpermission(activity, () -> {
            PictureSelector.create(activity)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .imageEngine(YZGlideUtil.createGlideEngine())// 外部传入图片加载引擎，必传项
                    .setOutputCameraPath(Environment.getExternalStorageDirectory() +  File.separator + "yozocloud" + File.separator)
                    .isCamera(true)// 是否显示拍照按钮
                    .maxSelectNum(maxNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .selectionMode(maxNum == 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选
                    .isCompress(true)// 是否压缩
                    .cutOutQuality(90)// 裁剪输出质量 默认100
                    .minimumCompressSize(100)// 小于多少kb的图片不压缩
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .isSingleDirectReturn(maxNum != 1)
                    .selectionData(selectionData)
                    .forResult(listener);//结果回调onActivityResult code
        });
    }

    /**
     * 打开相册相机
     *
     * @param activity
     * @param maxNum
     */
    public static void openPhoto(Activity activity, int maxNum) {
        YZPermissionUtil.photoAndCamraRxpermission(activity, () -> {
            PictureSelector.create(activity)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .imageEngine(YZGlideUtil.createGlideEngine())// 外部传入图片加载引擎，必传项
                    .setOutputCameraPath(Environment.getExternalStorageDirectory() +  File.separator + "yozocloud" + File.separator)
                    .maxSelectNum(maxNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .selectionMode(maxNum == 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选
                    .isCompress(true)// 是否压缩
                    .cutOutQuality(90)// 裁剪输出质量 默认100
                    .minimumCompressSize(100)// 小于多少kb的图片不压缩
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .isSingleDirectReturn(maxNum != 1)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        });
    }

    /**
     * 打开视频
     *
     * @param activity
     * @param maxNum
     */
    public static void openVideo(Activity activity, int maxNum) {
        YZPermissionUtil.photoAndCamraRxpermission(activity, () -> {
            PictureSelector.create(activity)
                    .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .setOutputCameraPath(Environment.getExternalStorageDirectory() +  File.separator + "yozocloud" + File.separator)
                    .maxSelectNum(maxNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .selectionMode(maxNum == 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选
                    .isCompress(true)// 是否压缩
                    .cutOutQuality(90)// 裁剪输出质量 默认100
                    .minimumCompressSize(100)// 小于多少kb的图片不压缩
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .isSingleDirectReturn(maxNum != 1)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        });
    }


    /**
     * 打开预览
     *
     * @param activity
     * @param relPosition
     * @param selectList
     */
    public static void openPreview(Activity activity, int relPosition, List<LocalMedia> selectList) {
        PictureSelector.create(activity)
                .themeStyle(R.style.picture_default_style)
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .setOutputCameraPath(Environment.getExternalStorageDirectory() +  File.separator + "yozocloud" + File.separator)
                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                .openExternalPreview(relPosition, selectList);
    }

    /**
     * 获取需要上传的文件路径
     *
     * @param data
     * @return
     */
    public static List<String> getImagePathList(Intent data) {
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        List<String> pathList = new ArrayList<>();
        if (selectList != null && selectList.size() > 0) {
            for (int i = 0; i < selectList.size(); i++) {
                if (YZStringUtil.isEmpty(selectList.get(i).getRealPath())) {
                    pathList.add(selectList.get(i).getPath());
                } else {
                    pathList.add(selectList.get(i).getRealPath());
                }
            }
        }
        return pathList;
    }

    /**
     * 获取需要上传的文件路径
     *
     * @param data
     * @return
     */
    public static List<File> getFilePathList(Intent data) {
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        List<File> pathList = new ArrayList<>();
        if (selectList != null && selectList.size() > 0) {
            for (int i = 0; i < selectList.size(); i++) {
                if (YZStringUtil.isEmpty(selectList.get(i).getRealPath())) {
                    pathList.add(new File(selectList.get(i).getPath()));
                } else {
                    pathList.add(new File(selectList.get(i).getRealPath()));
                }
            }
        }
        return pathList;
    }
}
