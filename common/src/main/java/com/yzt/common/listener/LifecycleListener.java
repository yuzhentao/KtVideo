package com.yzt.common.listener;

public interface LifecycleListener {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}