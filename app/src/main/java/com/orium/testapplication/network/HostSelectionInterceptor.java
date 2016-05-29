package com.orium.testapplication.network;

import android.net.Uri;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by admin on 01.05.2016.
 */
public final class HostSelectionInterceptor implements Interceptor {
    private volatile String host;

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        String host = this.host;
        if (host != null) {
            Uri uri = Uri.parse(host);
            HttpUrl newUrl = request.url().newBuilder()
                    .host(uri.getHost()).port(uri.getPort()).scheme(uri.getScheme())
                    .build();
            request = request.newBuilder()
                    .url(newUrl)
                    .build();
        }
        return chain.proceed(request);
    }
}