package org.lmt.http;

/**
 * @author zhuzhigang (<a href="mailto:zhuzhigang@zorkdata.com.cn">zhuzhigang@zorkdata.com.cn</a>)
 * @date 2019\12\26 0026
 * <p>
 * 用于错误码扩展接口
 * </p>
 */
public interface ErrorType {

    /**
     * 返回code
     *
     * @return 错误码
     */
    Integer getCode();

    /**
     * 返回message
     *
     * @return 错误消息描述
     */
    String getMessage();

    /**
     * 获取枚举类型
     *
     * @param code 错误码
     * @return 对象本身
     */
    ErrorType getEnum(Integer code);
}
