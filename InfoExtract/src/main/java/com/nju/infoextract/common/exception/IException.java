package com.nju.infoextract.common.exception;

/**
 * @author: songqiang
 * @since: 2020/12/4
 */
public interface IException extends IResultEnum{
    default ErrorCodeException newException(){
        return new ErrorCodeException(this.getCode(), this.getMsg());
    }
}
