package com.example.galileo.openweatherapp.retrofit;

import android.util.Log;


import com.example.galileo.openweatherapp.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static RestClient mRestClient;
    private WebService webService;

    private static final int CONNECT_TIMEOUT_MILLIS = 60; // 30s
    private static final int READ_TIMEOUT_MILLIS =  60; // 1m

    private RestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.SECONDS);
        httpClient.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // try the request
                Response response = chain.proceed(request);
                int count = 0;
                while (!response.isSuccessful() && count < 2) {
                    Log.e("intercept", "Request Retry - " + count);
                    count++;
                    // retry the request
                    response = chain.proceed(request);
                } // otherwise just pass the original response on
                return response;
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addConverterFactory(GsonConverterFactory.create());

        if (BuildConfig.DEBUG) {
            builder.client(httpClient.build());
        }

        Retrofit retrofit = builder.build();
        webService = retrofit.create(WebService.class);
    }

    public synchronized static RestClient getInstance() {
        if (mRestClient == null) {
            mRestClient = new RestClient();
        } return mRestClient;
    }

    public WebService getWebService() {
        return webService;
    }
}