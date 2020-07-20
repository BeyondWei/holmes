package com.shuzheng.holmes.sdk.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.pool.PoolStats;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 配置HttpCliet 4.5的版本对http请求做封装
 *
 * @author 子华
 * @date 2018/5/21
 */
public final class HttpClientUtil {

    private static final PoolingHttpClientConnectionManager connManager;

    private static final CloseableHttpClient chc;

    private static final int S = 1000;

    private static final String charset = "UTF-8";

    private static final int httpOkCode = 200;

    private static final int maxTotal = 10000;

    private static final int maxPerRoute = 300;

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 私有构造，不让构造
     */
    private HttpClientUtil() {
    }

    static {
        // 配置socket连接工厂，用以支持HTTP和HTTPS两种
        Registry<ConnectionSocketFactory> registry = null;
        try {
            SSLContext sc = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustStrategy() {
                        // 所有的HTTPS都认证通过，用来解决不安全的HTTPS认证问题
                        @Override
                        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                            return true;
                        }
                    }).build();
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
            LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sc, new String[]{"TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", plainsf)
                    .register("https", sslsf)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // 线程池的配置
        connManager = new PoolingHttpClientConnectionManager(registry);
        // 配置每个请求的最大并发量
        connManager.setDefaultMaxPerRoute(maxPerRoute);
        // 总共支持的并发量
        connManager.setMaxTotal(maxTotal);

        //socket配置信息
        SocketConfig socketConfig = SocketConfig.custom()
                //是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
                .setTcpNoDelay(true)
                //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
                .setSoReuseAddress(true)
                //接收数据的等待超时时间，单位ms 15秒
                .setSoTimeout(12 * S)
                //关闭Socket时，要么发送完所有数据，要么等待6s后，就关闭连接，此时socket.close()是阻塞的
                .setSoLinger(3)
                //开启监视TCP连接是否有效
                .setSoKeepAlive(true)
                .build();

        // 请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                //连接超时时间 3秒
                .setConnectTimeout(3 * S)
                //读超时时间（等待数据超时时间） 12秒
                .setSocketTimeout(12 * S)
                //从池中获取连接超时时间 300毫秒
//                .setConnectionRequestTimeout(300)
                .setConnectTimeout(600)
                .build();

        // httpClient 构建
        chc = HttpClients.custom()
                //连接池配置
                .setConnectionManager(connManager)
                //默认socket配置
                .setDefaultSocketConfig(socketConfig)
                //默认请求配置
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();

        //定时关闭无效链接
        Function<Integer, ExecutorService> function = Executors::newFixedThreadPool;
        ExecutorService service = function.apply(1);
        service.execute(() -> {
            while (true) {
                HttpClientUtil.idleConnection();
                logger.debug("========定时任务关闭无效链接========");
                try {
                    Thread.sleep(3000 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 对参数url进行POST请求，并把参数form数据提交过去
     *
     * @param url post请求地址
     * @return
     */
    public static String sendPostForm(String url) {
        return sendPostForm(url, null);
    }

    /**
     * 对参数url进行POST请求，并把参数form数据提交过去
     *
     * @param url  post请求地址
     * @param form post请求参数,可以为null
     * @return
     */
    public static String sendPostForm(String url, Map<String, String> form) {
        return sendPostForm(url, form, null);
    }

    /**
     * 对参数url进行POST请求，并把参数form数据提交过去
     *
     * @param url     post请求地址
     * @param form    post请求参数,可以为null
     * @param headers http请求的消息头，可以为null
     * @return
     */
    public static String sendPostForm(String url, Map<String, String> form, Map<String, String> headers) {
        List<BasicNameValuePair> list = new ArrayList();
        if (null != form && form.size() > 0) {
            for (String key : form.keySet()) {
                //请求参数
                list.add(new BasicNameValuePair(key, form.get(key)));
            }
        }
        //构建post请求
        HttpPost post = new HttpPost(url);
        try {
            //设置字符集
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
            post.setEntity(entity);
        } catch (Exception e) {
            logger.info("设置post参数异常：{}", e);
        }
        return sendRequest(post, headers);
    }

    /**
     * 发送post json
     * @param url
     * @param form
     * @return
     */
    public static String sendPostJson(String url, Map<String, String> form) {
        return sendPostJson(url, form,null);
    }

    /**
     * 发送post json
     * @param url
     * @param form
     * @param headers
     * @return
     */
    public static String sendPostJson(String url, Map<String, String> form, Map<String, String> headers) {
        List<BasicNameValuePair> list = new ArrayList();
        if (null != form && form.size() > 0) {
            for (String key : form.keySet()) {
                //请求参数
                list.add(new BasicNameValuePair(key, form.get(key)));
            }
        }
        //构建post请求
        HttpPost post = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(JSONObject.toJSONString(form));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(entity);
        } catch (Exception e) {
            logger.info("设置post参数异常：{}", e);
        }
        return sendRequest(post, headers);
    }



    /**
     * 对参数URL进行get请求
     *
     * @param url get 请求地址
     * @return
     */
    public static String sendGet(String url) {
        return sendGet(url, null);
    }

    /**
     * 对参数URL进行get请求
     *
     * @param url     get 请求地址
     * @param headers http请求的消息头，可以为null
     * @return
     */
    public static String sendGet(String url, Map<String, String> headers) {
        HttpGet get = new HttpGet(url);
        return sendRequest(get, headers);
    }

    /**
     * 通过HTTP 发送soap协议的XML数据给服务器
     *
     * @param url        地址
     * @param requestXml soap协议的xml
     * @param headers    消息头
     * @return
     */
    public static String sendSoap(String url, String requestXml, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(requestXml, Charset.forName(charset));
        post.setEntity(entity);
        if (null == headers) {
            headers = new HashMap<>();
        }
        headers.put("Content-Type", "text/xml;charset=" + charset);
        return sendRequest(post, headers);
    }

    /**
     * 通过HTTP 发送soap协议的XML数据给服务器
     *
     * @param url        地址
     * @param requestXml soap协议的xml
     * @return
     */
    public static String sendSoap(String url, String requestXml) {
        return sendSoap(url, requestXml, null);
    }

    /**
     * 数据发送
     *
     * @param request
     * @param headers http请求的消息头，可以为null
     * @return
     */
    private static String sendRequest(HttpRequestBase request, Map<String, String> headers) {
        CloseableHttpResponse response = null;
        try {
            if (null != headers && headers.size() > 0) {
                for (String headerKey : headers.keySet()) {
                    request.setHeader(headerKey, headers.get(headerKey));
                }
            }

            request.setHeader("Connection", "close");
            // System.out.println("====send request info ===="+DateUtils.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss") +"-----"+request.getURI());
            response = chc.execute(request);

            if (response.getStatusLine().getStatusCode() == httpOkCode) {
                //获得返回的结果，并关闭流
                return EntityUtils.toString(response.getEntity(), Charset.forName(charset));
            } else {
                request.abort();
                throw new RuntimeException("提供者服务异常，状态码：" + response.getStatusLine().getStatusCode());
            }
        } catch (ConnectionPoolTimeoutException e) {// 从连接池获取资源超时
            throw new RuntimeException("提供者服务繁忙");
        } catch (ConnectTimeoutException e) {// 连接超时
            throw new RuntimeException("提供者服务连接超时");
        } catch (SocketTimeoutException e) {// 读数据超时
            throw new RuntimeException("提供者服务读数据超时");
        } catch (UnknownHostException e) {
            throw new RuntimeException("域名[" + e.getMessage() + "]无法解析");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("请求提供者服务发生未知异常");
        } finally {

            if (!request.isAborted()) {
                request.abort();
                // request.releaseConnection();
            }
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.info("关闭流异常：{}", e);
                }
            }
        }
    }

    /**
     * 返回连接池的使用情况
     *
     * @return
     */
    public static Map<String, Object> poolStatistics() {
        Map<String, Object> poolStats = new HashMap();
        poolStats.put("POOL_STATS", connManager.getTotalStats());
        Map<HttpHost, PoolStats> routPoolStats = new HashMap<>();
        Set<HttpRoute> httpRouteSet = connManager.getRoutes();
        for (HttpRoute hr : httpRouteSet) {
            routPoolStats.put(hr.getTargetHost(), connManager.getStats(hr));
        }
        poolStats.put("ROUT_POOL_STATS", routPoolStats);
        return poolStats;
    }

    /**
     * 关闭无效连接
     */
    public static void idleConnection() {
        synchronized (connManager) {
            connManager.closeExpiredConnections();
            connManager.closeIdleConnections(3, TimeUnit.SECONDS);
        }
    }


}

