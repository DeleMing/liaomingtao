package org.lmt.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.lmt.ResultCode;
import org.lmt.exception.BaseException;
import org.lmt.http.ErrorType;

import java.time.Instant;

/**
 * @author googe (<a href="mailto:gujunling@zorkdata.com.cn">gujunling@zorkdata.com.cn</a>)
 * @author zhuzhigang (<a href="mailto:zhuzhigang@zorkdata.com.cn">zhuzhigang@zorkdata.com.cn</a>)
 * @date 2019/12/26
 * <p>
 * 请求的返回模型，所有rest正常都返回该类的对象
 * </p>
 */
@Getter
@Setter
public class Resp<T> implements BaseResp<T> {
    /**
     * 0 表示成功
     */
    private Integer code;

    /**
     * true表示成功
     */
    private Boolean result;

    /**
     * 错误描述
     */
    private String message;

    private Instant time;

    /**
     * API返回的数据
     * <p>
     * 此项当有值时才会返回
     */
    private T data;

    @JsonIgnore
    private ErrorType resultCode;


    private static <T> Resp<T> createEmptyResult() {
        return new Resp<>();
    }

    public static <T> Resp success() {
        Resp<T> r = createEmptyResult();
        r.setResultCode(ResultCode.SUCCESS);
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setResult(true);
        return r;
    }

    public static <T> Resp<T> success(T data) {
        Resp<T> r = createEmptyResult();
        r.setResultCode(ResultCode.SUCCESS);
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setResult(true);
        r.setData(data);
        return r;
    }

    public static <T> Resp<T> success(String message) {
        Resp<T> resp = createEmptyResult();
        resp.setResultCode(ResultCode.SUCCESS);
        resp.setCode(ResultCode.SUCCESS.getCode());
        resp.setResult(true);
        resp.setMessage(message);
        return resp;
    }

    public static <T> Resp<T> success(T data, String message) {
        Resp<T> resp = success(message);
        resp.setData(data);
        return resp;
    }

    public static <T> Resp<T> fail() {
        Resp<T> r = createEmptyResult();
        r.setResultCode(ResultCode.SYSTEM_ERROR);
        r.setCode(ResultCode.SYSTEM_ERROR.getCode());
        r.setResult(false);
        return r;
    }

    public static <T> Resp<T> fail(ErrorType resultCode) {
        Resp<T> r = createEmptyResult();
        r.setResultCode(resultCode);
        r.setCode(resultCode.getCode());
        r.setResult(false);
        return r;
    }

    public static <T> Resp<T> fail(ErrorType resultCode, String message) {
        Resp<T> r = createEmptyResult();
        r.setResultCode(resultCode);
        r.setCode(resultCode.getCode());
        r.setResult(false);
        r.setMessage(message);
        return r;
    }

    public static <T> Resp<T> fail(BaseException exception) {
        Resp<T> r = createEmptyResult();
        r.setResultCode(exception.getErrorCode());
        r.setCode(exception.getErrorCode().getCode());
        r.setMessage(exception.getMessage());
        r.setResult(false);
        return r;
    }

    public static <T> Resp<T> fail(BaseException exception, String message) {
        Resp<T> r = createEmptyResult();
        r.setResultCode(exception.getErrorCode());
        r.setCode(exception.getErrorCode().getCode());
        r.setMessage(message);
        r.setResult(false);
        return r;
    }

    public boolean hasSuccess() {
        if (this.resultCode == null) {
            return ResultCode.SUCCESS.getCode().equals(this.code);
        }
        return ResultCode.SUCCESS == this.resultCode;
    }

    public boolean hasFail() {
        return !hasSuccess();
    }
}
