package com.tv.GreenGrubBox.data.network.retrofit;


import com.tv.GreenGrubBox.MvpApp;
import com.tv.GreenGrubBox.data.network.ApiEndPoint;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shoeb on 8/8/17.
 */

public class ApiClient {
    private static Retrofit retrofit = null;
    private static final String TAG = ApiClient.class.getSimpleName();


    public static Retrofit getClient() {
        if (retrofit == null) {

            int cacheSize = 10 * 1024 * 1024; // 10 MB

            Cache cache = new Cache(MvpApp.getInstance().getCacheDir(), cacheSize);


            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cache(cache);

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request;
                    request = original.newBuilder()
                            .header("User-Agent", "OkHttp Example")
                            .build();

                    Response response = chain.proceed(request);

                    return response;
                }
            });

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

            OkHttpClient okHttpClient = builder

                    .connectTimeout(5*60, TimeUnit.SECONDS)
                    .readTimeout(5*60, TimeUnit.SECONDS).
                            build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiEndPoint.BASE_URL)

                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        }
        return retrofit;
    }

    public boolean isFromCache(Result<?> retroResult) {
        return retroResult.response().raw().cacheResponse() != null;
    }

}
