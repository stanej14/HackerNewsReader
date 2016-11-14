package cz.stanej14.hackernewsreader.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Utility class for UI.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {13.11.16}
 **/
public class UiUtils {
    public static final String TAG = UiUtils.class.getName();

    private UiUtils() {
    }

    public static int dpToPx(Context ctx, int dp) {
        Resources resources = ctx.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
}
