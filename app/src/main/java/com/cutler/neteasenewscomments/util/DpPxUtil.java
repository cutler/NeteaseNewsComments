package com.cutler.neteasenewscomments.util;

import android.content.Context;

public class DpPxUtil {

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }
}
