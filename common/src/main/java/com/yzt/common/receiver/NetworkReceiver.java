package com.yzt.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.yzt.common.R;
import com.yzt.common.util.NetworkUtil;
import com.yzt.common.util.ToastUtil;

/**
 * 网络状态监听
 *
 * @author yzt 2020/2/9
 */
public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null
                && intent.getAction() != null
                && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (!NetworkUtil.INSTANCE.isConnected()) {
                ToastUtil.INSTANCE.longCenter(context.getString(R.string.no_net));
            }
        }
    }

}