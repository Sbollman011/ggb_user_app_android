package com.tv.GreenGrubBox.data.network.retrofit;


import com.tv.GreenGrubBox.MvpApp;
import com.tv.GreenGrubBox.data.network.ApiEndPoint;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shoeb on 19/9/17.
 */

public class ApiClientGoogleUrl {
    private static Retrofit retrofit = null;
    private static final String TAG = ApiClient.class.getSimpleName();


    public static Retrofit getClient() {
        if (retrofit == null) {

            int cacheSize = 10 * 1024 * 1024; // 10 MB
            /*String dir = Environment.getExternalStorageDirectory() + File.separator + "GOIN_Cache";

            File mFile = new File(dir);
            if (!mFile.exists()) {
                boolean folderCreated = mFile.mkdir();
                System.out.println("folderCreated : " + folderCreated);
            }*/
            Cache cache = new Cache(MvpApp.getInstance().getCacheDir(), cacheSize);


            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cache(cache);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

            OkHttpClient okHttpClient = builder

                    .connectTimeout(180, TimeUnit.SECONDS)
                    .readTimeout(180, TimeUnit.SECONDS).
                            build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiEndPoint.BASE_URL_GOOGLE)

                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        }
        return retrofit;
    }
}
