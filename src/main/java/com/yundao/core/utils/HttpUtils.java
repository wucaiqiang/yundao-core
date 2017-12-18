package com.yundao.core.utils;

import com.yundao.core.log.Log;
import com.yundao.core.threadlocal.filter.RequestCommonParamsValidate;
import com.yundao.core.config.http.HttpConfigEnum;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.constant.HeaderConstant;
import com.yundao.core.constant.MethodConstant;
import com.yundao.core.exception.BaseException;
import com.yundao.core.http.config.HttpConfig;
import com.yundao.core.log.LogFactory;
import com.yundao.core.resolver.AbstractExceptionResolver;
import com.yundao.core.threadlocal.filter.RequestCommonParams;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * http工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class HttpUtils {

    private static final String DEFAULT_CHARSET = CommonConstant.UTF_8;
    private static Log log = LogFactory.getLog(HttpUtils.class);
    private static RequestConfig requestConfig;
    private static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    private static CloseableHttpClient httpClient = getHttpClient();

    static {
        try {
            connManager.setMaxTotal(NumberUtils.toInt(ConfigUtils.getValue(HttpConfigEnum.MAX_TOTAL_CONNECTIONS.getKey())));
            connManager.setDefaultMaxPerRoute(
                    NumberUtils.toInt(ConfigUtils.getValue(HttpConfigEnum.MAX_CONNECTIONS_PER_HOST.getKey())));

            RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT)
                    .setExpectContinueEnabled(true)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            requestConfig = RequestConfig.copy(defaultRequestConfig)
                    .setConnectTimeout(NumberUtils.toInt(ConfigUtils.getValue(HttpConfigEnum.CONNECTION_TIMEOUT.getKey())))
                    .setSocketTimeout(1000000).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
    }

    /**
     * 执行get请求
     *
     * @return
     * @throws BaseException
     * @author gjl
     */
    public static String get(String url) throws BaseException {
        return get(url, (Map) null, null);
    }

    /**
     * 执行get请求
     *
     * @param url
     * @param params
     * @return
     * @throws BaseException
     * @author gjl
     */
    public static String get(String url, Map<String, String> params) throws BaseException {
        return get(url, params, null);
    }

    /**
     * 执行get请求
     *
     * @param url
     * @param params
     * @param httpConfig
     * @return
     * @throws BaseException
     * @author gjl
     */
    public static String get(String url, Map<String, String> params, HttpConfig httpConfig) throws BaseException {
        RequestCommonParams requestParams = RequestCommonParams.newDefault(params);
        return get(url, requestParams, httpConfig);
    }

    /**
     * 执行get请求
     *
     * @param url
     * @param requestParams
     * @return
     * @throws BaseException
     */
    public static String get(String url, RequestCommonParams requestParams) throws BaseException {
        return get(url, null, requestParams);
    }

    /**
     * 执行get请求
     *
     * @param url
     * @param requestParams
     * @param httpConfig
     * @return
     * @throws BaseException
     */
    public static String get(String url, RequestCommonParams requestParams, HttpConfig httpConfig) throws BaseException {
        return get(url, null, requestParams, httpConfig);
    }

    /**
     * 执行get请求
     *
     * @param url
     * @param charset
     * @param requestParams
     * @return
     * @throws BaseException
     */
    public static String get(String url, String charset, RequestCommonParams requestParams) throws BaseException {
        HttpGet httpGet = getHttpGet(url, requestParams);
        return getResult(httpGet, charset);
    }

    /**
     * 执行get请求
     *
     * @param url
     * @param charset
     * @param requestParams
     * @param httpConfig
     * @return
     * @throws BaseException
     */
    public static String get(String url, String charset, RequestCommonParams requestParams, HttpConfig httpConfig) throws BaseException {
        HttpGet httpGet = getHttpGet(url, requestParams, httpConfig);
        return getResult(httpGet, charset);
    }

    /**
     * 执行post请求
     *
     * @param url
     * @param param
     * @return
     * @throws BaseException
     * @author jing.cui
     */
    public static String post(String url, Map<String, String> param) throws BaseException {
        return post(url, param, null);
    }

    /**
     * 执行post请求
     *
     * @param url
     * @param param
     * @param httpConfig
     * @return
     * @throws BaseException
     * @author jing.cui
     */
    public static String post(String url, Map<String, String> param, HttpConfig httpConfig) throws BaseException {
        RequestCommonParams requestParams = RequestCommonParams.newDefault(param);
        return post(url, requestParams, httpConfig);
    }

    /**
     * 执行post请求
     *
     * @param url
     * @param requestParams
     * @return
     * @throws BaseException
     */
    public static String post(String url, RequestCommonParams requestParams) throws BaseException {
        return post(url, null, requestParams);
    }

    /**
     * 执行post请求
     *
     * @param url
     * @param requestParams
     * @param httpConfig
     * @return
     * @throws BaseException
     */
    public static String post(String url, RequestCommonParams requestParams, HttpConfig httpConfig) throws BaseException {
        return post(url, null, requestParams, httpConfig);
    }

    /**
     * 执行post请求
     *
     * @param url
     * @param charset
     * @param requestParams
     * @return
     * @throws BaseException
     */
    public static String post(String url, String charset, RequestCommonParams requestParams) throws BaseException {
        return post(url, charset, requestParams, null);
    }

    /**
     * 执行post请求
     *
     * @param url
     * @param charset
     * @param requestParams
     * @param httpConfig
     * @return
     * @throws BaseException
     */
    public static String post(String url, String charset, RequestCommonParams requestParams, HttpConfig httpConfig) throws BaseException {
        HttpPost httpPost = getHttpPost(url, requestParams);
        return getResult(httpPost, charset);
    }

    /**
     * 执行post json请求
     *
     * @param url
     * @param requestParams
     * @return
     * @throws BaseException
     */
    public static Response postJson(String url, RequestCommonParams requestParams) throws BaseException {
        return postJson(url, null, requestParams);
    }

    /**
     * 执行post json请求
     *
     * @param url
     * @param requestParams
     * @return
     * @throws BaseException
     */
    public static Response postJson(String url, String charset, RequestCommonParams requestParams)
            throws BaseException {
        if (requestParams != null) {
            requestParams.addHeaderParams("Content-type", "application/json;charset=" + CommonConstant.UTF_8);
        }

        try {
            HttpPost post = HttpUtils.getHttpPost(url, null);
            if (requestParams != null) {
                setHeader(post, requestParams);
                String body = JsonUtils.objectToJson(requestParams.getMethodParams());
                if (!BooleanUtils.isBlank(body)) {
                    post.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
                }
            }
            CloseableHttpResponse response = HttpUtils.execute(post);
            Response result = HttpUtils.getResponse(response);
            return result;
        }
        catch (Exception e) {
            AbstractExceptionResolver.handleException(e);
            return null;
        }
    }

    /**
     * 执行post文件上传请求
     *
     * @param url
     * @param file
     * @param requestParams
     * @return
     * @throws BaseException
     */
    public static String postFiles(String url, UploadFileParams file, RequestCommonParams requestParams)
            throws BaseException {
        List<UploadFileParams> files = new ArrayList<UploadFileParams>(1);
        files.add(file);
        return postFiles(url, CommonConstant.UTF_8, files, requestParams);
    }

    /**
     * 执行post文件上传请求
     *
     * @param url
     * @param files
     * @param requestParams
     * @return
     * @throws BaseException
     */
    public static String postFiles(String url, List<UploadFileParams> files, RequestCommonParams requestParams)
            throws BaseException {
        return postFiles(url, CommonConstant.UTF_8, files, requestParams);
    }

    /**
     * 执行post文件上传请求
     *
     * @param url
     * @param files
     * @param requestParams
     * @return
     * @throws BaseException
     */
    public static String postFiles(String url, String charset, List<UploadFileParams> files,
                                   RequestCommonParams requestParams) throws BaseException {
        HttpPost httpPost = getHttpPost(url, requestParams);

        MultipartEntityBuilder multipartBuilder = MultipartEntityBuilder.create();
        multipartBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartBuilder.setCharset(Charset.forName(charset));

        // 设置文件参数
        int size = (files != null) ? files.size() : 0;
        for (int i = 0; i < size; i++) {
            UploadFileParams file = files.get(i);
            multipartBuilder.addBinaryBody(file.getParameterName(), file.getInputStream(), ContentType.DEFAULT_BINARY,
                    file.getFileName());
        }

        // 设置文本参数
        ContentType utf8 = ContentType.create(CommonConstant.TEXT_PLAIN, charset);
        Map<String, Object> params = null;
        if (requestParams != null && !BooleanUtils.isEmpty(params = requestParams.getMethodParams())) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                multipartBuilder.addTextBody(entry.getKey(), value == null ? "" : value.toString(), utf8);
            }
        }

        httpPost.setEntity(multipartBuilder.build());
        CloseableHttpResponse response = execute(httpPost);
        String result = getResult(response);
        return result;
    }

    /**
     * 执行方法
     *
     * @param httpMethod
     * @return
     * @throws BaseException
     */
    public static CloseableHttpResponse execute(HttpRequestBase httpMethod) throws BaseException {
        log.info("开始调用链接url=" + httpMethod.getURI());
        try {
            CloseableHttpResponse result = httpClient.execute(httpMethod);
            return result;
        }
        catch (Exception e) {
            AbstractExceptionResolver.handleException(e);
            return null;
        }
    }

    /**
     * 获取参数封装后的HttpGet
     *
     * @param url
     * @param requestParams
     * @return
     */
    public static HttpGet getHttpGet(String url, RequestCommonParams requestParams) {
        return getHttpGet(url, requestParams, null);
    }

    /**
     * 获取参数封装后的HttpGet
     *
     * @param url
     * @param requestParams
     * @param httpConfig
     * @return
     */
    public static HttpGet getHttpGet(String url, RequestCommonParams requestParams, HttpConfig httpConfig) {
        HttpGet get = new HttpGet(getUrl(url, requestParams));
        setCommonHttpMethod(get, httpConfig);
        setHeader(get, requestParams);
        return get;
    }

    /**
     * 获取参数封装后的HttpDelete
     *
     * @param url
     * @param requestParams
     * @return
     */
    public static HttpDelete getHttpDelete(String url, RequestCommonParams requestParams) {
        HttpDelete delete = new HttpDelete(getUrl(url, requestParams));
        setCommonHttpMethod(delete);
        setHeader(delete, requestParams);
        return delete;
    }

    /**
     * 获取参数封装后的HttpPost
     *
     * @param url
     * @param requestParams
     * @return
     */
    public static HttpPost getHttpPost(String url, RequestCommonParams requestParams) {
        return getHttpPost(url, requestParams, null);
    }

    /**
     * 获取参数封装后的HttpPost
     *
     * @param url
     * @param requestParams
     * @param httpConfig
     * @return
     */
    public static HttpPost getHttpPost(String url, RequestCommonParams requestParams, HttpConfig httpConfig) {
        HttpPost post = new HttpPost(url);
        setCommonHttpMethod(post, httpConfig);
        setHeader(post, requestParams);

        Map<String, Object> params = null;
        Charset charset = Charset.forName(DEFAULT_CHARSET);
        if (requestParams != null && !BooleanUtils.isEmpty(params = requestParams.getMethodParams())) {
            List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                nameValue.add(new BasicNameValuePair(entry.getKey(), value == null ? "" : value.toString()));
            }
            HttpEntity entity = new UrlEncodedFormEntity(nameValue, charset);
            post.setEntity(entity);
        }
        return post;
    }

    /**
     * 设置头部共用的参数
     *
     * @param httpMethod
     * @param requestParams
     */
    public static void setHeader(HttpRequestBase httpMethod, RequestCommonParams requestParams) {
        if (requestParams != null && httpMethod != null) {
            // 设置跟踪标识
            httpMethod.setHeader(HeaderConstant.REQUEST_TRACE_ID, requestParams.getTraceId());

            // 设置系统标识
            httpMethod.setHeader(HeaderConstant.REQUEST_ID, requestParams.getId());

            // 设置版本号
            httpMethod.setHeader(HeaderConstant.REQUEST_VERSION, requestParams.getVersion());

            // 设置请求时间
            httpMethod.setHeader(HeaderConstant.REQUEST_TIME, String.valueOf(requestParams.getTime()));

            // 设置加密类型
            httpMethod.setHeader(HeaderConstant.REQUEST_SIGN_TYPE, requestParams.getSignType());

            // 设置随机数
            httpMethod.setHeader(HeaderConstant.REQUEST_NONCE, requestParams.getNonce());

            // 设置ip
            httpMethod.setHeader(HeaderConstant.REQUEST_IP, requestParams.getIp());

            // 设置加密值
            String method = httpMethod.getMethod();
            if (MethodConstant.GET.equals(method)) {
                httpMethod.setHeader(HeaderConstant.REQUEST_SIGN, RequestCommonParamsValidate.getGetSign(requestParams));
            }
            else {
                httpMethod.setHeader(HeaderConstant.REQUEST_SIGN, RequestCommonParamsValidate.getSign(requestParams));
            }

            // 设置头部参数
            Map<String, String> headerParams = requestParams.getHeaderParams();
            if (!BooleanUtils.isEmpty(headerParams)) {
                for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                    String name = entry.getKey();
                    if (!HeaderConstant.REQUEST_IS_ASYNC.equals(name)) {
                        httpMethod.setHeader(entry.getKey(), entry.getValue());
                    }
                }
            }
            httpMethod.removeHeaders(HeaderConstant.CONTENT_LENGTH);

            // 设置是否异步
            httpMethod.setHeader(HeaderConstant.REQUEST_IS_ASYNC, String.valueOf(requestParams.isAsync()));
        }
    }

    /**
     * 获取返回值
     *
     * @param response
     * @return
     * @throws BaseException
     */
    public static String getResult(CloseableHttpResponse response) throws BaseException {
        return getResult(response, CommonConstant.UTF_8);
    }

    /**
     * 获取返回值
     *
     * @param response
     * @param charset
     * @return
     * @throws IOException
     */
    public static String getResult(CloseableHttpResponse response, String charset) throws BaseException {
        if (response == null) {
            return null;
        }
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                if (BooleanUtils.isBlank(charset)) {
                    charset = CommonConstant.UTF_8;
                }
                return getResult(entity, charset);
            }
        }
        finally {
            CloseableUtils.close(response);
        }
        return null;
    }

    /**
     * 获取响应结果
     *
     * @param response
     * @return
     * @throws IOException
     */
    public static Response getResponse(CloseableHttpResponse response) throws BaseException {
        try {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            Header header = entity.getContentType();
            String contentType = header != null ? header.getValue() : null;
            String content = getResult(entity, CommonConstant.UTF_8);
            return new Response(response.getStatusLine().getStatusCode(), contentType, content);
        }
        finally {
            CloseableUtils.close(response);
        }
    }

    /**
     * 关闭资源
     */
    public static void shutdown() {
        CloseableUtils.close(httpClient);
        CloseableUtils.close(connManager);
    }

    /**
     * 获取链接
     *
     * @param url
     * @param requestParams
     * @return
     */
    public static String getUrl(String url, RequestCommonParams requestParams) {
        StringBuilder result = new StringBuilder(url);
        Map<String, Object> params = null;
        if (requestParams != null && !BooleanUtils.isEmpty(params = requestParams.getMethodParams())) {
            List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                nameValue.add(new BasicNameValuePair(entry.getKey(), value == null ? "" : value.toString()));
            }
            if (url.indexOf(CommonConstant.QUESTION) != -1) {
                result.append(CommonConstant.AND);
            }
            else {
                result.append(CommonConstant.QUESTION);
            }
            String queryString = URLEncodedUtils.format(nameValue, DEFAULT_CHARSET);
            requestParams.setQueryString(queryString);
            requestParams.setMethod(MethodConstant.GET);
            result.append(queryString);
        }
        return result.toString();
    }

    private static String getResult(HttpEntity entity, String charsetName) throws BaseException {
        BufferedReader reader = null;
        try {
            Charset charset = Charset.forName(charsetName);
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
        catch (Exception e) {
            AbstractExceptionResolver.handleException(e);
            return null;
        }
        finally {
            CloseableUtils.close(reader);
        }
    }

    private static String getResult(HttpRequestBase httpMethod, String charset) throws BaseException {
        CloseableHttpResponse response = execute(httpMethod);
        return getResult(response, charset);
    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(connManager).build();
    }

    private static void setCommonHttpMethod(HttpRequestBase httpMethod) {
        setCommonHttpMethod(httpMethod, null);
    }

    private static void setCommonHttpMethod(HttpRequestBase httpMethod, HttpConfig httpConfig) {
        httpMethod.setHeader(HTTP.CONTENT_ENCODING, DEFAULT_CHARSET);
        if (httpConfig == null) {
            httpMethod.setConfig(requestConfig);
        }
        else {
            RequestConfig.Builder configBuilder = RequestConfig.copy(requestConfig);
            if (httpConfig.getConnectTimeout() != null) {
                configBuilder.setConnectionRequestTimeout(httpConfig.getConnectTimeout());
            }
            if (httpConfig.getSocketTimeout() != null) {
                configBuilder.setSocketTimeout(httpConfig.getSocketTimeout());
            }
            httpMethod.setConfig(configBuilder.build());
        }
    }

    /**
     * 上传文件参数
     */
    public static class UploadFileParams {

        /**
         * 参数名
         */
        private String parameterName;

        /**
         * 文件名
         */
        private String fileName;

        /**
         * 文件流
         */
        private InputStream inputStream;

        public String getParameterName() {
            return parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }
    }

    /**
     * 返回响应
     */
    public static class Response {

        /**
         * 编码
         */
        private int code;

        /**
         * 内容类型
         */
        private String contentType;

        /**
         * 内容
         */
        private String content;

        public Response(int code, String contentType, String content) {
            this.code = code;
            this.contentType = contentType;
            this.content = content;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}