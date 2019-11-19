package com.mycompany.testtask.module;

import android.app.Application;
import com.mycompany.testtask.rest.JsonPlaceHolderApi;
import com.mycompany.testtask.rest.MyInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {
    private static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
    private static final int TIMEOUT = 30000;


    @Provides
    @Singleton
    GsonConverterFactory gsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory rxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    MyInterceptor provideInterceptor() {
        return new MyInterceptor();
    }

    @Provides
    @Singleton
    Cache provideCache(Application application) {
        int cacheSize = 5 * 1024 * 1024; // 5MB
        File cacheDir = application.getCacheDir();
        return new Cache(cacheDir, cacheSize);
    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient(MyInterceptor interceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(GsonConverterFactory converterFactory, RxJava2CallAdapterFactory rxAdapter, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(rxAdapter)
                .build();
    }

    @Provides
    @Singleton
    static JsonPlaceHolderApi provideHolderApi(Retrofit retrofit) {
        return retrofit.create(JsonPlaceHolderApi.class);
    }
}
