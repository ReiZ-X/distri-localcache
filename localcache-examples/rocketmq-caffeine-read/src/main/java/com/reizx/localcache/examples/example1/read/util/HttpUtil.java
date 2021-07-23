package com.reizx.localcache.examples.example1.read.util;

import okhttp3.*;

import java.io.IOException;

/**
 * @author junke
 */
public class HttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static OkHttpClient client = new OkHttpClient();

    public static String get(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Throwable r) {
            r.printStackTrace();
            return null;
        }
    }

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
