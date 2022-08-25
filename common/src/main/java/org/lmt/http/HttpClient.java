package org.lmt.http;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.lmt.GlobalConstant;
import org.lmt.ResultCode;
import org.lmt.json.JsonUtils;
import org.lmt.req.IReq;
import org.lmt.req.Req;
import org.lmt.resp.Resp;

import java.io.IOException;

/**
 * @author: LiaoMingtao
 * @date: 2021/2/5
 */
@Slf4j
public class HttpClient extends AbstractHttpSdkClient {

    private String baseUrl;

    public HttpClient() {
    }

    public HttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public <T> Resp<T> invokeGet(String apiUrl, Req req, TypeReference<Resp<T>> typeReference) {
        try {
            String retStr = doHttpGet(apiUrl, req);
            return JsonUtils.fromJson(retStr, typeReference);
        } catch (Exception e) {
            log.error("{} invoke fail:{}", apiUrl, e);
            return buildFailResp(ResultCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    public <V> Resp<V> invokePost(String apiUrl, IReq req, TypeReference<Resp<V>> typeReference) {
        try {
            String retStr = doHttpPost(apiUrl, req);
            return JsonUtils.fromJson(retStr, typeReference);
        } catch (Exception e) {
            log.error("{} invoke fail:{}", apiUrl, e);
            return buildFailResp(ResultCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    @Override
    protected <T extends IReq> String doHttpGet(String path, T params, Header... headers) throws IOException {
        String uir = getURI(path);
        return sentHttpGet(uir, params, headers);
    }

    @Override
    protected <T extends IReq> String doHttpPost(String path, T params, Header... headers) throws Exception {
        String uri = getURI(path);
        return sentHttpPost(uri, params, headers);
    }

    public String getURI(String path) {
        String uri = path;
        if (null == baseUrl) {
            return uri;
        } else if (baseUrl.endsWith(GlobalConstant.SLASH) && !path.startsWith(GlobalConstant.SLASH)) {
            uri = baseUrl + GlobalConstant.SLASH + path;
        } else {
            uri = baseUrl + path;
        }
        return uri;
    }

    protected <T> Resp<T> buildFailResp(ErrorType code, String message) {
        return Resp.fail(code, message);
    }
}
