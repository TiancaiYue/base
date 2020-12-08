package cn.user_db.model;

import lombok.Data;

@Data
public class UserRightsResponse {
    /**
     * NetDrive_HighShare : false
     * NetDrive_Priveiw : 常规
     * NetDrive_EffectLength : 1
     * NetDrive_DownloadCount : 20
     * NetDrive_DownloadSize : 200
     * NetDrive_Roaming : true
     * NetDrive_HistoryVer : 5
     * NetDrive_SpaceSize : 1
     * NetDrive_FullSearch : false
     * NetDrive_SecurityFile : false
     * NetDrive_BatchDownload : true
     * NetDrive_TeamMemberNumber : 12
     * NetDrive_UploadSize : 12
     * NetDrive_TeamNumber : 6
     */

    private boolean NetDrive_HighShare;
    private String NetDrive_Priveiw;
    private int NetDrive_EffectLength;
    private int NetDrive_DownloadCount = 0;
    private int NetDrive_DownloadSize = 0;
    private boolean NetDrive_Roaming;
    private int NetDrive_HistoryVer;
    private int NetDrive_SpaceSize;
    private boolean NetDrive_FullSearch;
    private boolean NetDrive_SecurityFile;
    private boolean NetDrive_BatchDownload;
    private int NetDrive_TeamMemberNumber;
    private int NetDrive_UploadSize = 0;
    private int NetDrive_TeamNumber;

    @Override
    public String toString() {
        return "UserRightsResponse{" +
                "NetDrive_HighShare=" + NetDrive_HighShare +
                ", NetDrive_Priveiw='" + NetDrive_Priveiw + '\'' +
                ", NetDrive_EffectLength=" + NetDrive_EffectLength +
                ", NetDrive_DownloadCount=" + NetDrive_DownloadCount +
                ", NetDrive_DownloadSize=" + NetDrive_DownloadSize +
                ", NetDrive_Roaming=" + NetDrive_Roaming +
                ", NetDrive_HistoryVer=" + NetDrive_HistoryVer +
                ", NetDrive_SpaceSize=" + NetDrive_SpaceSize +
                ", NetDrive_FullSearch=" + NetDrive_FullSearch +
                ", NetDrive_SecurityFile=" + NetDrive_SecurityFile +
                ", NetDrive_BatchDownload=" + NetDrive_BatchDownload +
                ", NetDrive_TeamMemberNumber=" + NetDrive_TeamMemberNumber +
                ", NetDrive_UploadSize=" + NetDrive_UploadSize +
                ", NetDrive_TeamNumber=" + NetDrive_TeamNumber +
                '}';
    }
}
