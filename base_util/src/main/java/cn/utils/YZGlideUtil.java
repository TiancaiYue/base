package cn.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.listener.OnImageCompleteCallback;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

import java.io.File;

import cn.base.base_util.R;
import cn.network.Url;
import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;

public class YZGlideUtil extends AppGlideModule implements ImageEngine {
    private YZGlideUtil() {
    }

    private static YZGlideUtil instance;

    public static YZGlideUtil createGlideEngine() {
        if (null == instance) {
            synchronized (YZGlideUtil.class) {
                if (null == instance) {
                    instance = new YZGlideUtil();
                }
            }
        }
        return instance;
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param obj
     * @param imageView
     * @describe 与没有context的方法相比 不易导致 内存泄漏问题，原因 activity销毁的时候 imageView 的上下文对象自然不存在
     */
    public static void loadAnyImage(Context context, Object obj, ImageView imageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path).apply(initOptions())
                    .skipMemoryCache(false)
                    .into(imageView);
        } else {
            Glide.with(context).load(obj).apply(initOptions())
                    .skipMemoryCache(false)
                    .into(imageView);
        }
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param obj
     * @param imageView
     * @describe 与没有context的方法相比 不易导致 内存泄漏问题，原因 activity销毁的时候 imageView 的上下文对象自然不存在
     */
    public static void loadAnyImage(Context context, Object obj, ImageView imageView, int loadingImage, int errorImageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path).apply(initOptions())
                    .skipMemoryCache(false)
                    .placeholder(loadingImage)
                    .error(errorImageView)
                    .into(imageView);
        } else {
            Glide.with(context).load(obj).apply(initOptions())
                    .skipMemoryCache(false)
                    .placeholder(loadingImage)
                    .error(errorImageView)
                    .into(imageView);
        }
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param imageView
     * @describe 加载圆形图
     */
    public static void loadCircleImage(Context context, Object obj, ImageView imageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context)
                    .load(path)
                    .apply(initOptions())
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(obj)
                    .apply(initOptions())
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param imageView
     * @describe 加载圆形图
     */
    public static void loadCircleImage(Context context, Object obj, ImageView imageView, int errorImageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions())
                    .skipMemoryCache(false)
                    .error(errorImageView)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions())
                    .skipMemoryCache(false)
                    .error(errorImageView)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @describe 加载高斯模糊大图
     */
    public static void loadTransformImage(Context mContext, String path, ImageView imageView) {
        if (path.startsWith("http://")) {
            path = path.replace("http://", Url.getScheme());
        }
        Glide.with(mContext).load(path)
                .skipMemoryCache(false)
                .apply(initOptions(new BlurTransformation(14, 3)))
                .into(imageView);
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param imageView
     * @describe 加载正方形图片
     */
    public static void loadSquareImage(Context context, Object obj, ImageView imageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions(new CropSquareTransformation()))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions(new CropSquareTransformation()))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param imageView
     * @describe 加载黑白图片
     */
    public static void loadGrayscaleImage(Context context, Object obj, ImageView imageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions(new GrayscaleTransformation()))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions(new GrayscaleTransformation()))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param imageView
     * @param radius    圆角
     * @describe 加载圆角图片  默认所有圆角
     */
    public static void loadGrayscaleImage(Context context, Object obj, ImageView imageView, int radius) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .circleCrop()
                    .skipMemoryCache(false)
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @param context    当前Activity的上下文对象
     * @param imageView
     * @param radius     圆角
     * @param cornerType 圆角类型
     * @describe 加载圆角图片
     */
    public static void loadGrayscaleImage(Context context, Object obj, ImageView imageView, int radius, RoundedCornersTransformation.CornerType cornerType) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions(new RoundedCornersTransformation(radius, 0, cornerType)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions(new RoundedCornersTransformation(radius, 0, cornerType)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @param context      当前Activity的上下文对象
     * @param imageView
     * @param width,height 圆角宽高
     * @param cropType     裁剪位置
     * @describe 自定义裁剪
     */
    public static void loadCropTransformationImage(Context context, Object obj, ImageView imageView, int width, int height, CropTransformation.CropType cropType) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions(new CropTransformation(width, height, cropType)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions(new CropTransformation(width, height, cropType)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @param context      当前Activity的上下文对象
     * @param imageView
     * @param width,height 圆角宽高
     * @describe 自定义裁剪 默认居中裁剪
     */
    public static void loadCropTransformationImage(Context context, Object obj, ImageView imageView, int width, int height) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions(new CropTransformation(width, height, CropTransformation.CropType.CENTER)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions(new CropTransformation(width, height, CropTransformation.CropType.CENTER)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @param context
     * @param obj
     * @param imageView
     * @describe 加载动图gif
     */
    public static void loadGifImage(Context context, Object obj, ImageView imageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).asGif().apply(initOptions())
                    .skipMemoryCache(false)
                    .load(path)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).asGif().apply(initOptions())
                    .skipMemoryCache(false)
                    .load(obj)
                    .circleCrop()
                    .into(imageView);
        }
    }


    /**
     * @param sizeMultiplier 如设置0.2f缩略
     * @describe 加载缩略图
     */
    public static void loadThumbnailImage(Context context, String path, ImageView imageView, float sizeMultiplier) {
        if (path.startsWith("http://")) {
            path = path.replace("http://", Url.getScheme());
        }
        Glide.with(context).load(path)
                .skipMemoryCache(false)
                .thumbnail(sizeMultiplier)//缩略的参数
                .apply(initOptions())
                .into(imageView);
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param imageView
     * @describe 设置滤镜 （陈旧）
     */
    public static void loadSepiaFilterTransformationImage(Context context, Object obj, ImageView imageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions(new SepiaFilterTransformation(1.0f)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions(new SepiaFilterTransformation(1.0f)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param imageView
     * @describe 设置滤镜 （亮度）
     */
    public static void loadBrightnessFilterTransformationImage(Context context, Object obj, ImageView imageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions(new BrightnessFilterTransformation(0.5f)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions(new BrightnessFilterTransformation(0.5f)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param imageView
     * @describe 设置滤镜 （马赛克）
     */
    public static void loadPixelationFilterTransformationImage(Context context, Object obj, ImageView imageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions(new PixelationFilterTransformation(20f)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions(new PixelationFilterTransformation(20f)))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * @param context   当前Activity的上下文对象
     * @param imageView
     * @describe 设置滤镜 （素描画）
     */
    public static void loadSketchFilterTransformationImage(Context context, Object obj, ImageView imageView) {
        if (obj instanceof String) {
            String path = (String) obj;
            if (path.startsWith("http://")) {
                path = path.replace("http://", Url.getScheme());
            }
            Glide.with(context).load(path)
                    .apply(initOptions(new SketchFilterTransformation()))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        } else {
            Glide.with(context).load(obj)
                    .apply(initOptions(new SketchFilterTransformation()))
                    .skipMemoryCache(false)
                    .circleCrop()
                    .into(imageView);
        }
    }

    /**
     * 加载完图片设置回调
     *
     * @param context
     * @param path
     * @param callback
     */
    public static void loadImageViewCallBack(Context context, String path, final GlideLoadBitmapCallback callback) {
        if (path.startsWith("http://")) {
            path = path.replace("http://", Url.getScheme());
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(path)
                .apply(options)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        callback.getBitmapCallback(resource);
                    }
                });
    }

    public interface GlideLoadBitmapCallback {
        void getBitmapCallback(Drawable resource);
    }

    /**
     * @param context   上下文
     * @param imageView 图片显示组件
     * @param path      加载中图
     * @param radius    圆角半径
     */
    //加载轮播广告
    public static void loadUrlForBGABannerImg(Context context, String path, ImageView imageView, int radius) {
        if (path.startsWith("http://")) {
            path = path.replace("http://", Url.getScheme());
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.banner_default_img)//加载中显示的图片
                .centerCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundedCorners(radius));

        Glide.with(context).load(path).apply(options).into(imageView);
    }

    /**
     * @return 这里默认设置全部为禁止缓存
     * @describe 设置缓存
     * Glide有两种缓存机制，一个是内存缓存，一个是硬盘缓存。
     * 内存缓存的主要作用是防止应用重复将图片数据读取到内存当中，
     * 而硬盘缓存的主要作用是防止应用重复从网络或其他地方重复下载和读取数据
     * @diskCacheStrategy参数 DiskCacheStrategy.NONE： 表示不缓存任何内容
     * DiskCacheStrategy.DATA： 表示只缓存原始图片
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）
     */
    private static RequestOptions initOptions(BitmapTransformation transformation) {
        return new RequestOptions()
                .transform(transformation)
                .onlyRetrieveFromCache(false)//是否只从缓存加载图片
                .diskCacheStrategy(DiskCacheStrategy.ALL);//禁止磁盘缓存
    }

    private static RequestOptions initOptions() {
        return new RequestOptions()
                .centerCrop()
                .onlyRetrieveFromCache(false)//是否只从缓存加载图片
                .diskCacheStrategy(DiskCacheStrategy.ALL);//禁止磁盘缓存
    }

    /**
     * 清理图片磁盘缓存
     */
    public static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    public static void clearImageMemoryCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清除图片所有缓存
     * 主要调用这个方法
     */
    public static void clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
    }

    /**
     * @param transformation
     * @return
     * @describe 设置加载的效果
     */
    private static RequestOptions bitmapTransform(BitmapTransformation transformation) {
        return new RequestOptions();
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        if (url.startsWith("http://")) {
            url = url.replace("http://", Url.getScheme());
        }
        Glide.with(context).load(url).apply(initOptions())
                .skipMemoryCache(false)
                .into(imageView);
    }

    /**
     * 加载网络图片适配长图方案
     * # 注意：此方法只有加载网络图片才会回调
     *
     * @param context
     * @param url
     * @param imageView
     * @param longImageView
     * @param callback      网络图片加载回调监听 {link after version 2.5.1 Please use the #OnImageCompleteCallback#}
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView, OnImageCompleteCallback callback) {
        if (url.startsWith("http://")) {
            url = url.replace("http://", Url.getScheme());
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new ImageViewTarget<Bitmap>(imageView) {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        if (callback != null) {
                            callback.onShowLoading();
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        if (callback != null) {
                            callback.onHideLoading();
                        }
                    }

                    @Override
                    protected void setResource(@Nullable Bitmap resource) {
                        if (callback != null) {
                            callback.onHideLoading();
                        }
                        if (resource != null) {
                            boolean eqLongImage = MediaUtils.isLongImg(resource.getWidth(), resource.getHeight());
                            longImageView.setVisibility(eqLongImage ? View.VISIBLE : View.GONE);
                            imageView.setVisibility(eqLongImage ? View.GONE : View.VISIBLE);
                            if (eqLongImage) {
                                // 加载长图
                                longImageView.setQuickScaleEnabled(true);
                                longImageView.setZoomEnabled(true);
                                longImageView.setPanEnabled(true);
                                longImageView.setDoubleTapZoomDuration(100);
                                longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                longImageView.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
                                longImageView.setImage(ImageSource.bitmap(resource),
                                        new ImageViewState(0, new PointF(0, 0), 0));
                            } else {
                                // 普通图片
                                imageView.setImageBitmap(resource);
                            }
                        }
                    }
                });
    }

    /**
     * 加载网络图片适配长图方案
     * # 注意：此方法只有加载网络图片才会回调
     *
     * @param context
     * @param url
     * @param imageView
     * @param longImageView
     * @ 已废弃
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView) {
        if (url.startsWith("http://")) {
            url = url.replace("http://", Url.getScheme());
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new ImageViewTarget<Bitmap>(imageView) {
                    @Override
                    protected void setResource(@Nullable Bitmap resource) {
                        if (resource != null) {
                            boolean eqLongImage = MediaUtils.isLongImg(resource.getWidth(), resource.getHeight());
                            longImageView.setVisibility(eqLongImage ? View.VISIBLE : View.GONE);
                            imageView.setVisibility(eqLongImage ? View.GONE : View.VISIBLE);
                            if (eqLongImage) {
                                // 加载长图
                                longImageView.setQuickScaleEnabled(true);
                                longImageView.setZoomEnabled(true);
                                longImageView.setPanEnabled(true);
                                longImageView.setDoubleTapZoomDuration(100);
                                longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                longImageView.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
                                longImageView.setImage(ImageSource.bitmap(resource),
                                        new ImageViewState(0, new PointF(0, 0), 0));
                            } else {
                                // 普通图片
                                imageView.setImageBitmap(resource);
                            }
                        }
                    }
                });
    }

    /**
     * 加载相册目录
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        if (url.startsWith("http://")) {
            url = url.replace("http://", Url.getScheme());
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .override(180, 180)
                .centerCrop()
                .sizeMultiplier(0.5f)
                .apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(8);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * 加载gif
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadAsGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        if (url.startsWith("http://")) {
            url = url.replace("http://", Url.getScheme());
        }
        Glide.with(context)
                .asGif()
                .load(url)
                .into(imageView);
    }

    /**
     * 加载图片列表图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        if (url.startsWith("http://")) {
            url = url.replace("http://", Url.getScheme());
        }
        Glide.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
                .apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder))
                .into(imageView);
    }
}