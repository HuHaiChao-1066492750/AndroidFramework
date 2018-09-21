package com.huhaichao.framework.network.retrofit;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.huhaichao.framework.utils.CacheManager;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Connection;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author HuHaiChao
 */

public class RetrofitFactory {
    private static final String TAG = "RetrofitFactory";
    private static String BASE_URL = "http://www.baidu.com/";
    private static final int READ_TIME_OUT = 30;
    private static final int WRITE_TIME_OUT = 30;
    private static final int CONNECT_TIME_OUT = 30;

    private static RetrofitFactory mRetrofitFactory;
    private static Retrofit mRetrofit;
    private static String cookie = "";
    private List<Cookie> cookieStore;

    //单例模式
    private RetrofitFactory() {
    }

    public static RetrofitFactory getInstance() {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null) {
                    mRetrofitFactory = new RetrofitFactory();
                }
            }
        }
        return mRetrofitFactory;
    }

    public <T> T create(final Class<T> cls) {
        if (mRetrofit == null) {
            File cacheFile = new File(CacheManager.getInstance().getPragmeCachePath());
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //连接失败后是否重新连接
//                    .retryOnConnectionFailure(true)
                    //设置超时时间
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    //设置读取超时时间
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    //设置写入超时时间
                    .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                    .cookieJar(new CookieJar() {

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore = cookies;
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            return cookieStore != null ? cookieStore : new ArrayList<Cookie>();
                        }
                    })
                    //添加头部拦截器，在response调用一次，拦截器的添加有先后顺序
//                    .addInterceptor(headerInterceptor)
                    //添加网络拦截器(缓存)，在request和response分别调用一次
                    .addNetworkInterceptor(cacheInterceptor)
                    //添加日记拦截器，在response调用一次，，拦截器的添加有先后顺序
                    .addInterceptor(loggerInterceptor)
                    .cache(cache)
                    .build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //请求结果转换为基本类型
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //添加GSON转换器
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加rxjava转换器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit.create(cls);
    }


    /**
     * 自定义Logger拦截器
     */
    static Interceptor loggerInterceptor = new Interceptor() {
        private final Charset UTF8 = Charset.forName("UTF-8");

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            RequestBody requestBody = request.body();
            boolean hasRequestBody = requestBody != null;

            Connection connection = chain.connection();
            String requestStartMessage = "--> "
                    + request.method()
                    + ' ' + request.url()
                    + (connection != null ? " " + connection.protocol() : "");
            if (hasRequestBody) {
                requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
            }
            LogUtils.dTag(TAG, requestStartMessage);


            if (hasRequestBody) {
                if (requestBody.contentType() != null) {
//                    LogUtils.dTag(TAG, "Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
//                    LogUtils.dTag(TAG, "Content-Length: " + requestBody.contentLength());
                }

                Headers headers = request.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    String name = headers.name(i);
                    if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
//                        LogUtils.dTag(TAG, name + ": " + headers.value(i));
                    }
                }

                if (!hasRequestBody) {
                    LogUtils.dTag(TAG, "--> END " + request.method());
                } else if (bodyHasUnknownEncoding(request.headers())) {
                    LogUtils.dTag(TAG, "--> END " + request.method() + " (encoded body omitted)");
                } else {
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);

                    Charset charset = UTF8;
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(UTF8);
                    }

                    if (isPlaintext(buffer)) {
                        LogUtils.json(TAG, buffer.readString(charset));
                        LogUtils.dTag(TAG, "--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)");
                    } else {
                        LogUtils.dTag(TAG, "--> END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)");
                    }
                }
            }

            long startNs = System.nanoTime();
            Response response;
            try {
                response = chain.proceed(request);
            } catch (Exception e) {
                LogUtils.dTag(TAG, "<-- HTTP FAILED: " + e);
                throw e;
            }
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            LogUtils.dTag(TAG, "<-- "
                    + response.code()
                    + (response.message().isEmpty() ? "" : ' ' + response.message())
                    + ' ' + response.request().url()
                    + " (" + tookMs + "ms)");

            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
//                LogUtils.dTag(TAG,headers.name(i) + ": " + headers.value(i));
            }

            if (!HttpHeaders.hasBody(response)) {
                LogUtils.dTag(TAG, "<-- END HTTP");
            } else if (bodyHasUnknownEncoding(response.headers())) {
                LogUtils.dTag(TAG, "<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.buffer();

                Long gzippedLength = null;
                if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                    gzippedLength = buffer.size();
                    GzipSource gzippedResponseBody = null;
                    try {
                        gzippedResponseBody = new GzipSource(buffer.clone());
                        buffer = new Buffer();
                        buffer.writeAll(gzippedResponseBody);
                    } finally {
                        if (gzippedResponseBody != null) {
                            gzippedResponseBody.close();
                        }
                    }
                }

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (!isPlaintext(buffer)) {
                    LogUtils.dTag(TAG, "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return response;
                }

                if (contentLength != 0) {
                    LogUtils.json(TAG, buffer.clone().readString(charset));
//                    LogUtils.dTag(TAG, buffer.clone().readString(charset));
                }

                if (gzippedLength != null) {
                    LogUtils.dTag(TAG, "<-- END HTTP (" + buffer.size() + "-byte, "
                            + gzippedLength + "-gzipped-byte body)");
                } else {
                    LogUtils.dTag(TAG, "<-- END HTTP (" + buffer.size() + "-byte body)");
                }
            }
            return response;
        }

        private boolean isPlaintext(Buffer buffer) {
            try {
                Buffer prefix = new Buffer();
                long byteCount = buffer.size() < 64 ? buffer.size() : 64;
                buffer.copyTo(prefix, 0, byteCount);
                for (int i = 0; i < 16; i++) {
                    if (prefix.exhausted()) {
                        break;
                    }
                    int codePoint = prefix.readUtf8CodePoint();
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false;
                    }
                }
                return true;
            } catch (EOFException e) {
                return false;
            }
        }

        private boolean bodyHasUnknownEncoding(Headers headers) {
            String contentEncoding = headers.get("Content-Encoding");
            return contentEncoding != null
                    && !"identity".equalsIgnoreCase(contentEncoding)
                    && !"gzip".equalsIgnoreCase(contentEncoding);
        }
    };

    /**
     * 自定义请求头部
     */
    static Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String sign = null;
            if (cookie != null && cookie.length() > 0) {
                request = request.newBuilder()
                        .addHeader("Cookie", cookie).build();
            }

            Response response = chain.proceed(request);
            if (cookie == null || "".equals(cookie)) {
                //所有的Cookie
                List<String> cookies = response.headers().values("Set-Cookie");
                for (String tmp : cookies) {
                    if (tmp.contains("xkmen_")) {
                        //自定义的Cookie
                        cookie = tmp;
                    }
                }
            }
            return response;
        }
    };

    /**
     * 自定义缓存拦截器
     */
    static Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            //没网强制从缓存读取
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            //有网从服务器取
            Response response = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                int maxAge = 0;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    };
}
