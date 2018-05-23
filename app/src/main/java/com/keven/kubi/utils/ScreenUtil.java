package com.keven.kubi.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by keven-liang on 2018/5/15.
 */

public class ScreenUtil {

    private static int   screenHeight;    // 高度

    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 100;
        }
        if (screenHeight == 0) {
            screenHeight = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getHeight();
        }
        return screenHeight;
    }
}
