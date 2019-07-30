package com.yuzhentao.ktvideo.view.progressbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.TypedValue;

import com.yuzhentao.ktvideo.R;

public class ThemeUtils {

    /**
     * Obtains the current theme's primary color.
     * Will default to Color.BLUE.
     *
     * @param context The current context
     * @return current theme's primary color
     */
    public static int getThemePrimaryColor(final Context context) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(
                typedValue.data,
                new int[]{R.attr.colorPrimary}
        );
        int color = a.getColor(0, Color.BLUE);
        a.recycle();
        return color;
    }

    /**
     * Obtains the current theme's secondary color.
     * Will default to Color.CYAN.
     *
     * @param context The current context
     * @return current theme's secondary color
     */
    public static int getThemeAccentColor(final Context context) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(
                typedValue.data,
                new int[]{R.attr.colorAccent}
        );
        int color = a.getColor(0, Color.CYAN);
        a.recycle();
        return color;
    }

}