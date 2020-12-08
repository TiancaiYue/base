package com.mola.cpp.push;

import java.io.Serializable;

/**
 * Created by base on 2020/6/17.
 */
public class MyMessage implements Serializable {
    private String pushType;
    private int id;  //待办id
    private String title;
    private String address;
    private long eventTime;// 待办时间
    private String memo; //备注
    private long callTime;
    private long beginTime;
    private long endTime;
    private int callTimeNum;

    public int getCallTimeNum() {   {}
        return callTimeNum;
    }

    public void setCallTimeNum(int callTimeNum) {
        this.callTimeNum = callTimeNum;
    }

    public String getJushType() {
        return pushType;
    }

    public void setJushType(String pushType) {
        this.pushType = pushType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getCallTime() {
        return callTime;
    }

    public void setCallTime(long callTime) {
        this.callTime = callTime;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "pushType='" + pushType + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", eventTime=" + eventTime +
                ", memo='" + memo + '\'' +
                ", callTime='" + callTime + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", callTimeNum=" + callTimeNum +
                '}';
    }
}
