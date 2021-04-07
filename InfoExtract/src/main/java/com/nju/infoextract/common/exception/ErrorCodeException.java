package com.nju.infoextract.common.exception;

/**
 * @author: songqiang
 * @since: 2020/12/4
 */
public class ErrorCodeException extends RuntimeException {
    private int code;

    public ErrorCodeException(int code, String msg){
        super(msg);
        this.code = code;
    }

    public ErrorCodeException(int errorCode, String message, Throwable t){
        super(message, t);
        this.code = errorCode;
    }
    public int getCode() { return code; }
}
