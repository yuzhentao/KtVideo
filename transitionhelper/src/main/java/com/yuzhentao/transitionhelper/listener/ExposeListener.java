package com.yuzhentao.transitionhelper.listener;

/**
 * Created by Mr_immortalZ on 2017/12/6.
 * email : mr_immortalz@qq.com
 */
public interface ExposeListener {

    void onExposeStart();

    void onExposeProgress(float progress);

    void onExposeEnd();

}