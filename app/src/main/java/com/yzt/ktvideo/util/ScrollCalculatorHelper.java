package com.yzt.ktvideo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;

import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.yzt.ktvideo.R;

import java.util.Objects;

/**
 * 计算滑动，自动播放的帮助类
 * Created by guoshuyu on 2017/11/2.
 */
public class ScrollCalculatorHelper {

    private int firstVisible = 0;
    private int lastVisible = 0;
    private int visibleCount = 0;
    private int playId;
    private int rangeTop;
    private int rangeBottom;
    private PlayRunnable runnable;

    private Handler playHandler = new Handler();

    public ScrollCalculatorHelper(int playId, int rangeTop, int rangeBottom) {
        this.playId = playId;
        this.rangeTop = rangeTop;
        this.rangeBottom = rangeBottom;
    }

    public void onScrollStateChanged(RecyclerView view, int scrollState) {
        if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            playVideo(view);
        }
    }

    public void onScroll(RecyclerView view, int firstVisibleItem, int lastVisibleItem, int visibleItemCount) {
        if (firstVisible == firstVisibleItem) {
            return;
        }
        firstVisible = firstVisibleItem;
        lastVisible = lastVisibleItem;
        visibleCount = visibleItemCount;
    }

    private void playVideo(RecyclerView view) {
        if (view == null) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        GSYBaseVideoPlayer baseVideoPlayer = null;
        boolean needPlay = false;
        for (int i = 0; i < visibleCount; i++) {
            if (layoutManager != null
                    && layoutManager.getChildAt(i) != null
                    && Objects.requireNonNull(layoutManager.getChildAt(i)).findViewById(playId) != null) {
                GSYBaseVideoPlayer player = Objects.requireNonNull(layoutManager.getChildAt(i)).findViewById(playId);
                Rect rect = new Rect();
                player.getLocalVisibleRect(rect);
                int height = player.getHeight();
                //说明第一个完全可视
                if (rect.top == 0 && rect.bottom == height) {
                    baseVideoPlayer = player;
                    if ((player.getCurrentPlayer().getCurrentState() == GSYBaseVideoPlayer.CURRENT_STATE_NORMAL
                            || player.getCurrentPlayer().getCurrentState() == GSYBaseVideoPlayer.CURRENT_STATE_ERROR)) {
                        needPlay = true;
                    }
                    break;
                }
            }
        }
        if (baseVideoPlayer != null && needPlay) {
            if (runnable != null) {
                GSYBaseVideoPlayer tmpPlayer = runnable.baseVideoPlayer;
                playHandler.removeCallbacks(runnable);
                runnable = null;
                if (tmpPlayer == baseVideoPlayer) {
                    return;
                }
            }
            runnable = new PlayRunnable(baseVideoPlayer);
            //降低频率
            playHandler.postDelayed(runnable, 400);
        }
    }

    private class PlayRunnable implements Runnable {

        GSYBaseVideoPlayer baseVideoPlayer;

        PlayRunnable(GSYBaseVideoPlayer baseVideoPlayer) {
            this.baseVideoPlayer = baseVideoPlayer;
        }

        @Override
        public void run() {
            boolean inPosition = false;
            //如果未播放，需要播放
            if (baseVideoPlayer != null) {
                int[] screenPosition = new int[2];
                baseVideoPlayer.getLocationOnScreen(screenPosition);
                int halfHeight = baseVideoPlayer.getHeight() / 2;
                int rangePosition = screenPosition[1] + halfHeight;
                //中心点在播放区域内
                if (rangePosition >= rangeTop && rangePosition <= rangeBottom) {
                    inPosition = true;
                }
                if (inPosition) {
                    startPlayLogic(baseVideoPlayer, baseVideoPlayer.getContext());
                }
            }
        }
    }

    private void startPlayLogic(GSYBaseVideoPlayer baseVideoPlayer, Context context) {
        if (!NetworkUtil.INSTANCE.isWifi()) {
            showWifiDialog(baseVideoPlayer, context);
            return;
        }

        baseVideoPlayer.startPlayLogic();
    }

    private void showWifiDialog(final GSYBaseVideoPlayer baseVideoPlayer, Context context) {
        if (!NetworkUtil.INSTANCE.isConnected()) {
            ToastUtil.INSTANCE.longCenter(context.getString(R.string.no_net));
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.tips_not_wifi));
        builder.setPositiveButton(context.getResources().getString(R.string.tips_not_wifi_confirm), (dialog, which) -> {
            dialog.dismiss();
            baseVideoPlayer.startPlayLogic();
        });
        builder.setNegativeButton(context.getResources().getString(R.string.tips_not_wifi_cancel), (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

}