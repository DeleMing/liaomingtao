package org.lmt.exception;


import org.lmt.http.ErrorType;

/**
 * 异常处理基础类，系统所有异常都要继承此类
 *
 * @author: LiaoMingtao
 * @date: 2019/12/3
 */
public class BaseException extends Throwable {

    private static final long serialVersionUID = 3438322119670695654L;
    /**
     * 异常对应的错误类型
     */
    protected ErrorType errorCode;
    protected String errorMessage;


    public BaseException(Throwable cause) {
        super(cause);
        if (cause instanceof BaseException) {
            BaseException e = (BaseException) cause;
            this.errorMessage = e.errorMessage;
            this.errorCode = e.errorCode;
        }
    }

    /**
     * @param cause     原始的异常信息
     * @param errorCode 错误编码对应i18n的key
     */
    public BaseException(Throwable cause, ErrorType errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    /**
     * @param cause        原始的异常信息
     * @param errorCode    错误编码对应i18n的key
     * @param errorMessage 默认显示信息，即i18n配置文件无对应值时显示
     */
    public BaseException(Throwable cause, ErrorType errorCode, String errorMessage) {
        this(cause, errorCode);
        this.errorMessage = errorMessage;
    }

    /**
     * @param errorCode    错误编码对应i18n的key
     * @param errorMessage 默认显示信息，即i18n配置文件无对应值时显示
     */
    public BaseException(ErrorType errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * @param errorCode 错误编码对应i18n的key
     */
    public BaseException(ErrorType errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }


    @Override
    public String getMessage() {
        return this.errorMessage;
    }

    public ErrorType getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(ErrorType errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
