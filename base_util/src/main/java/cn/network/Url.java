package cn.network;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.base.base_util.BuildConfig;
import cn.user_db.UserCache;
import cn.utils.YZStringUtil;

/**
 * Created by base on 2020/01/15.
 */
public class Url {
    private static final String DEBUG = "debug";
    private static final String RELEASE = "release";

    private static final int pomeloPort = 3080;

    private static final String DEBUG_SCHEME = "http://";
    private static final String DEBUG_BASE = "auth.yozodocs.com";
    private static final String DEBUG_YOUCLOUD = "www.yozodocs.com";
    private static final String DEBUG_POMELO = "mobi.yozodocs.com";
    private static final String DEBUG_URL_PDF = "pdf.yozodocs.com";
    private static final String DEBUG_URL_CMS = "cms.yozocloud.cn/info/image";
    private static final String DEBUG_URL_PERSON = "vip.yozodocs.com/h5";
    private static final String DEBUG_APK_UPDATE = "apk.yozodocs.com";

    private static final String RELEASE_SCHEME = "https://";
    private static final String RELEASE_BASE = "auth.yozocloud.cn";
    private static final String RELEASE_YOUCLOUD = "www.yozocloud.cn";
    private static final String RELEASE_POMELO = "mobi.yozocloud.cn";
    private static final String RELEASE_URL_PDF = "pdf.yozocloud.cn";
    private static final String RELEASE_URL_CMS = "cms.yozocloud.cn/info/image";
    private static final String RELEASE_URL_PERSON = "vip.yozocloud.cn/h5";
    private static final String RELEASE_APK_UPDATE = "apk.yozocloud.cn";

    private static final List<String> mUrlBuilder = new ArrayList<>();
    private static final List<String> mYoZoBuilder = new ArrayList<>();
    private static final List<String> mPOMELOUrlBuilder = new ArrayList<>();
    private static final List<String> mUrlPdf = new ArrayList<>();
    private static final List<String> mCmsBuilder = new ArrayList<>();
    private static final List<String> mPersonBuilder = new ArrayList<>();
    private static final List<String> mApkUpdateBuilder = new ArrayList<>();
    private static final List<String> mScheme = new ArrayList<>();

    static {
        mUrlBuilder.clear();
        mYoZoBuilder.clear();
        mPOMELOUrlBuilder.clear();
        mPersonBuilder.clear();
        mUrlPdf.clear();
        mApkUpdateBuilder.clear();
        mScheme.clear();

        switch (BuildConfig.BUILD_TYPE) {
            //开发环境
            case DEBUG:
                mScheme.add(DEBUG_SCHEME);
                mUrlBuilder.add(DEBUG_SCHEME);
                mUrlBuilder.add(DEBUG_BASE);
                mYoZoBuilder.add(DEBUG_SCHEME);
                mYoZoBuilder.add(DEBUG_YOUCLOUD);
                mPOMELOUrlBuilder.add(DEBUG_SCHEME);
                mPOMELOUrlBuilder.add(DEBUG_POMELO);
                mUrlPdf.add(DEBUG_SCHEME);
                mUrlPdf.add(DEBUG_URL_PDF);
                mPersonBuilder.add(DEBUG_SCHEME);
                mPersonBuilder.add(DEBUG_URL_PERSON);
                mApkUpdateBuilder.add(DEBUG_SCHEME);
                mApkUpdateBuilder.add(DEBUG_APK_UPDATE);
                break;
            //生产环境，即线上环境
            case RELEASE:
                mScheme.add(RELEASE_SCHEME);
                mUrlBuilder.add(RELEASE_SCHEME);
                mUrlBuilder.add(RELEASE_BASE);
                mYoZoBuilder.add(RELEASE_SCHEME);
                mYoZoBuilder.add(RELEASE_YOUCLOUD);
                mPOMELOUrlBuilder.add(RELEASE_SCHEME);
                mPOMELOUrlBuilder.add(RELEASE_POMELO);
                mUrlPdf.add(RELEASE_SCHEME);
                mUrlPdf.add(RELEASE_URL_PDF);
                mPersonBuilder.add(RELEASE_SCHEME);
                mPersonBuilder.add(RELEASE_URL_PERSON);
                mApkUpdateBuilder.add(DEBUG_SCHEME);
                mApkUpdateBuilder.add(RELEASE_APK_UPDATE);
                break;
            default:
                break;
        }
    }

    public static String getUrlBuilder(boolean isScheme) {
        if (isScheme) {
            return mUrlBuilder.get(0) + mUrlBuilder.get(1);
        } else {
            return mUrlBuilder.get(1);
        }
    }

    public static String getPOMELOUrlBuilder(boolean isScheme) {
        if (isScheme) {
            return mPOMELOUrlBuilder.get(0) + mPOMELOUrlBuilder.get(1);
        } else {
            return mPOMELOUrlBuilder.get(1);
        }
    }

    public static String getUrlPdf(boolean isScheme) {
        if (isScheme) {
            return mUrlPdf.get(0) + mUrlPdf.get(1);
        } else {
            return mUrlPdf.get(1);
        }
    }

    public static String getUrlYoZo(boolean isScheme) {
        String yoCloudUrl = mYoZoBuilder.get(1);
        if (UserCache.getIsEnterprise() && !TextUtils.isEmpty(UserCache.getCurrentUser().getCorp().getDomain())) {
            yoCloudUrl = yoCloudUrl.replace("www", UserCache.getCurrentUser().getCorp().getDomain());
        }
        if (isScheme) {
            return mYoZoBuilder.get(0) + yoCloudUrl;
        } else {
            return yoCloudUrl;
        }
    }

    public static String getPersonUrl(boolean isScheme) {
        if (isScheme) {
            return mPersonBuilder.get(0) + mPersonBuilder.get(1);
        } else {
            return mPersonBuilder.get(1);
        }
    }

    public static String getApkUpdateUrl(boolean isScheme) {
        if (isScheme) {
            return mApkUpdateBuilder.get(0) + mApkUpdateBuilder.get(1);
        } else {
            return mApkUpdateBuilder.get(1);
        }
    }

    public static String getCmsBuilder(boolean isScheme, String end) {
        mCmsBuilder.clear();

        switch (BuildConfig.BUILD_TYPE) {
            case DEBUG:
                mCmsBuilder.add(DEBUG_SCHEME);
                mCmsBuilder.add(DEBUG_URL_CMS);
                break;
            case RELEASE:
                mCmsBuilder.add(RELEASE_SCHEME);
                mCmsBuilder.add(RELEASE_URL_CMS);
                break;
        }
        switch (end) {
            case "init":
                mCmsBuilder.add("/infoListByAlbumName/yocloud_app_init");
                break;
            case "banner":
                mCmsBuilder.add("/infoListByAlbumName/yocloud_app_event");
                break;
            case "pop":
                mCmsBuilder.add("/infoListByAlbumName/yocloud_app_pop");
                break;
            case "member":
                mCmsBuilder.add("/infoListByAlbumName/yocloud_app_member");
                break;
        }

        if (!YZStringUtil.isEmpty(end)) {
            if (BuildConfig.BUILD_TYPE.equals(RELEASE)) {
                if (isScheme) {
                    return mCmsBuilder.get(0) + mCmsBuilder.get(1) + mCmsBuilder.get(2);
                } else {
                    return mCmsBuilder.get(1) + mCmsBuilder.get(2);
                }
            } else {
                mCmsBuilder.add("_test");
                if (isScheme) {
                    return mCmsBuilder.get(0) + mCmsBuilder.get(1) + mCmsBuilder.get(2) + mCmsBuilder.get(3);
                } else {
                    return mCmsBuilder.get(1) + mCmsBuilder.get(2) + mCmsBuilder.get(3);
                }
            }
        } else {
            if (isScheme) {
                return mCmsBuilder.get(0) + mCmsBuilder.get(1);
            } else {
                return mCmsBuilder.get(1);
            }
        }
    }

    public static int getPomeloPort() {
        return pomeloPort;
    }

    public static String getScheme() {
        return mScheme.get(0);
    }
}
