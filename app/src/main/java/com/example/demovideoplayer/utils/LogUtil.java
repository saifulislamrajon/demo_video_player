package com.example.demovideoplayer.utils;

import androidx.annotation.Nullable;

import com.example.demovideoplayer.BuildConfig;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

/*
* Created by Saiful
* */
public class LogUtil {


    public static void initializeLog(final String tagName) {

        if (BuildConfig.DEBUG) {

            Timber.plant(new Timber.DebugTree() {
                @Override
                protected @Nullable
                String createStackElementTag(@NotNull StackTraceElement element) {
                    return tagName + ":[" + super.createStackElementTag(element) + ":" + element.getLineNumber() + "]";
                }
            });

        } else {
            //
        }
    }


}