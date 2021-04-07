package com.nju.infoextract.common.exception;

/**
 * @author: songqiang
 * @since: 2020/12/4
 */
public enum ResultCode implements IException {
    SUCCESS(0, "success")
    ;
    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
