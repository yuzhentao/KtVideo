package com.yuzhentao.transitionhelper.method;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.yuzhentao.transitionhelper.bean.InfoBean;
import com.yuzhentao.transitionhelper.expose.base.ExposeView;

/**
 * Created by Mr_immortalZ on 2016/10/24.
 * email : mr_immortalz@qq.com
 */
public abstract class InflateShowMethod extends ShowMethod {

    public View inflateView;

    public InflateShowMethod(Activity activity, int layoutId) {
        this.inflateView = LayoutInflater.from(activity).inflate(layoutId, null);
    }

    @Override
    public void translate(InfoBean bean, ExposeView parent, View child) {
        set.playTogether(
                ObjectAnimator.ofFloat(child, "translationX", 0, -bean.translationX),
                ObjectAnimator.ofFloat(child, "translationY", 0, -bean.translationY),
                ObjectAnimator.ofFloat(child, "scaleX", 1, 1 / bean.scale),
                ObjectAnimator.ofFloat(child, "scaleY", 1, 1 / bean.scale)
        );
        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(showDuration).start();
    }

}