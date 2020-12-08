package cn.network.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by base on 2020-01-14.
 */
@Data
public class AMBaseDto implements Serializable {
    private boolean success;
    private int code;
    private int errorcode;
    private int errorCode;
    private String msg;
    private String message;
    private String errorMessage;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
