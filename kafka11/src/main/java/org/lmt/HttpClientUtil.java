package org.lmt;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http交互工具类
 */
public class HttpClientUtil {

    protected static final Logger                           logger                          = LoggerFactory.getLogger(HttpClientUtil.class);

    public static final String                              METHOD_POST                     = "POST";
    public static final String                              METHOD_GET                      = "GET";
    public static final String                              DEFAULT_CHARSET                 = "utf-8";
    public static final String                              DEFAULT_CONTENT_TYPE            = "application/json;charset=UTF-8";
    public static final int                                 DEFAULT_CONNECT_TIMEOUT         = 5000;
    public static final int                                 DEFAULT_READ_TIMEOUT            = 5000;
    public static final int                                 DEFAULT_CONNECT_REQUEST_TIMEOUT = 5000;

    private static final int                                MAX_TOTAL                       = 64;

    private static final int                                MAX_PER_ROUTE                   = 32;

    private static final RequestConfig                      requestConfig;

    private static final PoolingHttpClientConnectionManager connectionManager;

    private static final HttpClientBuilder                  httpBuilder;

    private static final CloseableHttpClient                httpClient;

    private static final CloseableHttpClient                httpsClient;

    private static SSLContext               				sslContext						 = null;

    static {

        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                @Override
                public boolean isTrusted(X509Certificate[] xcs, String string){
                    return true;
                }
            }).build();
        } catch (KeyStoreException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (KeyManagementException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    static {
        requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_READ_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT).build();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        httpBuilder = HttpClientBuilder.create();
        httpBuilder.setDefaultRequestConfig(requestConfig);
        httpBuilder.setConnectionManager(connectionManager);
        httpClient = httpBuilder.build();
        httpsClient = httpBuilder.build();
    }



    private HttpClientUtil() {

    }

    public static String get(String url, Map<String, String> headers) {
        HttpGet request = new HttpGet(url);
        try {
            wrapHeader(request, headers);// 设置请求头
            return execute(request,httpsClient);
        } catch (Exception e) {

            logger.error(e.getMessage(), e);
        } finally {
            request.releaseConnection();
        }
        return null;
    }

    public static String postBody(String url,
                                  String body,
                                  Map<String, String> headers) {
        HttpPost request = new HttpPost(url);
        try {
            wrapHeader(request, headers);// 设置请求头
            wrapStringEntity(request, body);// 设置body
            return execute(request,httpClient);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            request.releaseConnection();
        }
        return null;
    }

    public static String postForm(String url,
                                  Map<String, String> params,
                                  Map<String, String> headers) {
        HttpPost request = new HttpPost(url);
        try {
            wrapHeader(request, headers);// 设置请求头
            wrapFormEntity(request, params);
            return execute(request,httpClient);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            request.releaseConnection();
        }
        return null;
    }

    private static String execute(HttpRequestBase request, CloseableHttpClient httpClient) {
        String respJson = null;
        try (CloseableHttpResponse response = httpClient.execute(request);) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                respJson = EntityUtils.toString(httpEntity, DEFAULT_CHARSET);
                EntityUtils.consume(httpEntity);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return respJson;
    }

    private static void wrapHeader(HttpRequestBase request,
                                   Map<String, String> headers) {
        // 设置请求头
        if (null != headers) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void wrapStringEntity(HttpPost request,
                                         String body) {
        // 设置body
        if (body != null) {
            StringEntity entity = new StringEntity(body, DEFAULT_CHARSET);// 解决中文乱码问题
            entity.setContentEncoding(DEFAULT_CHARSET);
            request.setEntity(entity);
        }
    }

    private static void wrapFormEntity(HttpPost request,
                                       Map<String, String> params) throws UnsupportedEncodingException {
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET));
        }
    }

    public static void main(String[] args) {
        String url = "https://paas.zorkdata.com/api/c/compapi/v2/cc/search_host/";
        String param = "{\n" +
                "    \"bk_app_code\": \"zork-smartdata\",\n" +
                "    \"bk_app_secret\": \"c2217bcc-761e-491d-a525-a36cc29e0af9\",\n" +
                "    \"bk_username\": \"admin\",\n" +
                "    \"bk_biz_id\": 2\n" +
                "    \n" +
                "}";
        Map<String,String> content = new HashMap<>();
        content.put("Content-Type","application/json");
        String res = postBody(url,param,content);
        System.out.println(res);
    }
}

