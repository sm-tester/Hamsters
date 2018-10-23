package com.unrealmojo.hamsters;

import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.squareup.picasso.Picasso;
import com.unrealmojo.hamsters.helpers.Utilities;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HamsterApp extends Application {

    public static volatile Handler appHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        appHandler = new Handler(getMainLooper());

        Utilities.object().init(this);

        initNetworkServices();
    }

    private void initNetworkServices() {

        OkHttpClient mHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.initialize(getApplicationContext(), mHttpClient);

        Picasso picasso;

        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.HEADERS);

            picasso = new Picasso.Builder(this)
                    .listener((picasso1, uri, exception) -> exception.printStackTrace())
                    .build();
        }
        else {
            picasso = new Picasso.Builder(this).build();
        }

        Picasso.setSingletonInstance(picasso);
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            HamsterApp.appHandler.post(runnable);
        } else {
            HamsterApp.appHandler.postDelayed(runnable, delay);
        }
    }
}
