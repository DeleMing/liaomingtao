package org.lmt.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;

/**
 * 接口请求参数协议定义
 *
 * @author: LiaoMingtao
 * @date: 2019/12/14
 */
public interface IReq {

    /**
     * 生成urlParams
     *
     * @return 实体转url编码
     */
    default String toUrlParams() {
        StringBuilder urlString = new StringBuilder(512);
        char c = '?';
        Class<?> aClass = getClass();
        while (aClass != null) {
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field : declaredFields) {

                String key;
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                if (annotation != null) {
                    key = annotation.value();
                } else {
                    key = field.getName();
                }
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                    try {
                        Object b = field.get(this);
                        if (b != null) {
                            urlString.append(c).append(key).append('=').append(urlEncode(b.toString()));
                        }
                    } catch (IllegalAccessException ignored) {
                    } finally {
                        field.setAccessible(false);
                    }
                }
                if (c == '?') {
                    c = '&';
                }
            }
            aClass = aClass.getSuperclass();
        }
        return urlString.toString();
    }


    /**
     * 进行URL编码
     *
     * @param str 原始字符串
     * @return url编码后字符串
     */
    default String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encode failed");
        }
    }

}
