package com.example.toastinglibrary;

import android.content.Context;
import android.widget.Toast;

public class ToastingLib {

    public static void toastingShow(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
