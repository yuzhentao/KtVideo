package com.yuzhentao.ktvideo.view.progressbutton;

import android.graphics.Color;

public class ColorUtils {

    /**
     * Lightens a color by a given factor.
     *
     * @param color  The color to lighten
     * @param factor The factor to lighten the color. 0 will make the color unchanged. 1 will make the
     *               color white.
     * @return lighter version of the specified color.
     */
    public static int lighten(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

}