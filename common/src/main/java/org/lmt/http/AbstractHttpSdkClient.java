package org.lmt.http;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicHeader;
import org.lmt.json.JsonUtils;
import org.lmt.req.IReq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Http请求发送client
 *
 * @author: LiaoMingtao
 * @date: 2019/10/24
 */
@Slf4j
public abstract class AbstractHttpSdkClient implements ISdkClient {

    /**
     * 发起Http Get请求
     *
     * @param uri    接口地址，eg：http://localhost/user/getuser
     * @param params 接口参数
     * @param <T>
     * @return
     * @throws IOException
     */
    final protected <T extends IReq> String sentHttpGet(String uri, T params, Header... headers) throws IOException {
        long start = System.currentTimeMillis();
        String uriWithParas = uri + params.toUrlParams();
        String responseBody = HttpConPoolUtils.get(uriWithParas, buildReqHeaders(headers));
        log.debug("doHttpGet| url={}| params={}| time={}| model={}", uri, params, (System.currentTimeMillis() - start), responseBody);
        return responseBody;
    }

    /**
     * 发起Http Post请求
     *
     * @param uri    接口地址，eg：http://localhost/user/getuser
     * @param params 接口参数
     * @param <T>
     * @return
     * @throws Exception
     */
    final protected <T extends IReq> String sentHttpPost(String uri, T params, Header... headers) throws Exception {
        long start = System.currentTimeMillis();
        String responseBody = HttpConPoolUtils.post(uri, "UTF-8", JsonUtils.toJson(params), buildReqHeaders(headers));
        log.debug("doHttpPost, url={}| params={}| time={}| model={}", uri, params, (System.currentTimeMillis() - start), responseBody);
        return responseBody;
    }

    /**
     * application/x-www-form-urlencoded 形式请求接口
     *
     * @param uri    地址
     * @param params 参数
     * @param <T>    泛型
     * @return 泛型
     * @throws Exception 异常
     */
    final protected <T> String sentHttpPost(String uri, List<? extends NameValuePair> params) throws Exception {
        long start = System.currentTimeMillis();
        HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
        byte[] bytes = HttpConPoolUtils.post(uri, httpEntity, new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
        String responseBody = null == bytes ? null : new String(bytes, "UTF-8");
        log.debug("doHttpPost, url={}| params={}| time={}| model={}", new Object[]{uri, params, System.currentTimeMillis() - start, responseBody});
        return responseBody;
    }

    /**
     * http get请求
     *
     * @param path    接口路由path，eg：user/getuser
     * @param params  接口参数
     * @param headers
     * @param <T>
     * @return
     * @throws IOException
     */
    protected abstract <T extends IReq> String doHttpGet(String path, T params, Header... headers) throws IOException;

    /**
     * http post请求
     *
     * @param path    接口路由path，eg：user/getuser
     * @param params  接口参数
     * @param headers
     * @param <T>
     * @return
     * @throws Exception
     */
    protected abstract <T extends IReq> String doHttpPost(String path, T params, Header... headers) throws Exception;

    /**
     * 构建http head
     *
     * @param headers
     * @return
     */
    private Header[] buildReqHeaders(Header[] headers) {
        ArrayList<Header> headerList = new ArrayList<>();
        if (headers != null && headers.length > 0) {
            List tmpHeaderList = java.util.Arrays.asList(headers);
            headerList.addAll(tmpHeaderList);
        }
        headerList.add(new BasicHeader("content-Type", "application/json"));

        Header[] retHeaderArray = new Header[headerList.size()];
        return headerList.toArray(retHeaderArray);
    }

    /**
     * 进行URL编码
     *
     * @param str str
     */
    final protected String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encode failed");
        }
    }

    @SuppressWarnings("all")
    final protected String urlDecode(String encodeStr) {
        try {
            return URLDecoder.decode(encodeStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decode failed");
        }
    }
}
