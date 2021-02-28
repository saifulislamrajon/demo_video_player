package com.example.demovideoplayer.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.demovideoplayer.utils.LogUtil;

import timber.log.Timber;

public class AppVideoPlayer extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        LogUtil.initializeLog("LVIDEOPLAYER");

        Timber.e("START APP_VIDEO_PLAYER");
    }
}
