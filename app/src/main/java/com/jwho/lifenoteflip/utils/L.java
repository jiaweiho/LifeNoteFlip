package com.jwho.lifenoteflip.utils;

import android.util.Log;

/**
 * Class used for logging in Android.
 */
public class L {
    public static <T extends Class>void d(T clazz, String message) {
        Log.d(clazz.getSimpleName(), message);
    }

    public static <T extends Class>void i(T clazz, String message) {
        Log.i(clazz.getSimpleName(), message);
    }

    public static void dM(Class clazz, String method, String message) {
        Log.d(clazz.getSimpleName(), method + ": " + message);
    }
}
