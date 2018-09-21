package com.huhaichao.framework.network.retrofit;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author HuHaiChao
 */

public interface ApiService {

    /**
     * GET请求
     *
     * @param path
     * @param params
     * @return
     */
    @GET
    Observable<JSONObject> get(@Url String path, @QueryMap Map<String, String> params);

    /**
     * POST请求
     *
     * @param path
     * @param baseForm
     * @return
     */
    @POST
    Observable<JSONObject> post(@Url String path, @Body JSONObject baseForm);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded;charset=UTF-8;")
    @POST
    Observable<JSONObject> post(@Url String path, @FieldMap Map<String, Object> map);

    @POST
    @Multipart
    Observable<JSONObject> testFileUpload2(@Url String path, @PartMap JSONObject baseForm, @Part MultipartBody.Part file);

    @GET
    Observable<ResponseBody> downloadApk(@Url String path);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String path);
}