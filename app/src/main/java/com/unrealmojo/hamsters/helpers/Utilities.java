package com.unrealmojo.hamsters.helpers;

import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.Collection;

import androidx.lifecycle.LiveData;

public class Utilities {
    private static final String TAG = Utilities.class.getSimpleName();

    private static float density = 1f;

    private static Utilities ourInstance = new Utilities();

    public static Utilities object() {
        return ourInstance;
    }

    private Utilities() {}

    public void init(Context context) {
        density = context.getResources().getDisplayMetrics().density;
    }

    public static Point getDisplaySize(Context context) {
        Point displaySize = new Point();
        try {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                if (display != null) {
                    display.getMetrics(displayMetrics);
                    display.getSize(displaySize);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return displaySize;
    }

    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int)Math.ceil(density * value);
    }

    public static boolean isNetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo acNetInfo = connectivityManager.getActiveNetworkInfo();
        return acNetInfo != null && acNetInfo.isConnected();
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;

        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }

        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNoneBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static <T> boolean IsNullOrEmpty(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    public static boolean IsEmpty(LiveData liveData) {
        return liveData == null || liveData.getValue() == null;
    }
}
