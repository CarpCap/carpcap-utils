package com.carpcap.utils.http;

import okhttp3.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 高性能 OkHttp 工具类（线程安全、连接池可复用）
 * @author CarpCap
 */
public class CHttpUtil {

    // 全局复用的 OkHttpClient
    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.SECONDS)   // 连接超时
                .readTimeout(2, TimeUnit.SECONDS)      // 读取超时
                .writeTimeout(1, TimeUnit.SECONDS)     // 写入超时
                .callTimeout(3, TimeUnit.SECONDS)      // 总体超时
                .connectionPool(new ConnectionPool(200, 5, TimeUnit.MINUTES)) // 最大100连接，空闲5分钟
                // ⚙️ 自动重试与重定向
                .retryOnConnectionFailure(true)
                .followRedirects(true)
                // ⚙️ 异步DNS解析优化（防止DNS阻塞）
                .dns(Dns.SYSTEM)
                .build();
    }



    /**
     * 自定义okHttpClient
     *
     * @param client
     * @author CarpCap
     * @since 2025/12/19 17:17
     */
    public void resetClient(OkHttpClient client){
        CHttpUtil.client = client;
    }

    /**
     * 同步 GET 请求
     */
    public static String get(String url, Map<String, String> headers) throws IOException {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
        try (Response response = client.newCall(builder.build()).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }
            return response.body() != null ? response.body().string() : null;
        }
    }

    /**
     * 异步 GET 请求（非阻塞）
     */
    public static void getAsync(String url, Map<String, String> headers, Callback callback) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
        client.newCall(builder.build()).enqueue(callback);
    }

    /**
     * 同步 POST JSON 请求
     */
    public static String postJson(String url, String json, Map<String, String> headers) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request.Builder builder = new Request.Builder().url(url).post(body);
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
        try (Response response = client.newCall(builder.build()).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }
            return response.body() != null ? response.body().string() : null;
        }
    }

    /**
     * 异步 POST JSON 请求（推荐用于提升QPS）
     */
    public static void postJsonAsync(String url, String json, Map<String, String> headers, Callback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request.Builder builder = new Request.Builder().url(url).post(body);
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
        client.newCall(builder.build()).enqueue(callback);
    }
}
