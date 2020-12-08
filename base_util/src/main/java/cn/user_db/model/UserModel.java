package cn.user_db.model;

import lombok.Data;

/**
 * Created by base on 2018/10/2.
 */
@Data
public class UserModel {
    private String auth;
    private boolean isStartUpload;
    private boolean isKeepUploadChoose;
    private boolean isShowLocalTip = true;
    private boolean isEnterprise;
    private String bitmap;
    private String loginUserType;
    private String userEmail;
    private String phone;
    private String password;
    private String account;
    private String closeAdvTime;
    private String quotaInfo;
    private String userRightsResponse;
    private String currentUser;
    private String supportAudioFileFormat;
    private String supportVideoFileFormat;
    private String sid;
    private String yozoCookieModel;
    private String cookieModel;
    private String yozoCookie;
    private String ssoCookie;
    private String cookie;

    public UserModel() {
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "auth='" + auth + '\'' +
                ", isStartUpload=" + isStartUpload +
                ", isKeepUploadChoose=" + isKeepUploadChoose +
                ", isShowLocalTip=" + isShowLocalTip +
                ", isEnterprise=" + isEnterprise +
                ", bitmap='" + bitmap + '\'' +
                ", loginUserType='" + loginUserType + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", account='" + account + '\'' +
                ", closeAdvTime='" + closeAdvTime + '\'' +
                ", quotaInfo='" + quotaInfo + '\'' +
                ", userRightsResponse='" + userRightsResponse + '\'' +
                ", currentUser='" + currentUser + '\'' +
                ", supportAudioFileFormat='" + supportAudioFileFormat + '\'' +
                ", supportVideoFileFormat='" + supportVideoFileFormat + '\'' +
                ", sid='" + sid + '\'' +
                ", yozoCookieModel='" + yozoCookieModel + '\'' +
                ", yozoCookie='" + yozoCookie + '\'' +
                ", ssoCookie='" + ssoCookie + '\'' +
                '}';
    }
}
