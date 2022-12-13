package com.yzt.common.player;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.yzt.common.R;
import com.yzt.common.base.App;
import com.yzt.common.util.NetworkUtil;
import com.yzt.common.util.ToastUtil;

import timber.log.Timber;

/**
 * 纵向列表自动播放工具类
 */
public class AutoPlayUtil {

    private static long lastTime;

    private AutoPlayUtil() {

    }

    /**
     * 纵向滑动
     */
    public static void onVerticalScrollPlayVideo(
            RecyclerView recyclerView,
            int playerContainerId,
            int playerId,
            int firstVisiblePosition,
            int lastVisiblePosition,
            boolean isScroll
    ) {
        try {
            if (recyclerView == null)
                return;

            if (!isScroll && System.currentTimeMillis() - lastTime < 500) {
                Timber.e(">>>>>AutoPlayUtil-onScroll-拦截");
                lastTime = System.currentTimeMillis();
                return;
            }

            lastTime = System.currentTimeMillis();
            boolean hasVideo = false;
            for (int index = 0; index <= lastVisiblePosition - firstVisiblePosition; index++) {
                View itemView = recyclerView.getChildAt(index);
                if (itemView == null) {
                    continue;
                }

                if (itemView.getId() == playerContainerId) {
                    Timber.e(">>>>>AutoPlayUtil-onScroll-纵向位置=%s", index);
                    hasVideo = true;
                    View playerView = itemView.findViewById(playerId);
                    if (playerView instanceof StandardGSYVideoPlayer) {
                        StandardGSYVideoPlayer player = (StandardGSYVideoPlayer) playerView;
                        boolean isVisible = isViewVisibleOver90(player);
                        Timber.e(">>>>>AutoPlayUtil-onScroll-纵向位置=" + index + ">>>可见=" + isVisible + ">>>状态=" + player.getCurrentPlayer().getCurrentState());
                        if (isVisible) {
                            if (
                                    player.getCurrentPlayer().getCurrentState() == GSYBaseVideoPlayer.CURRENT_STATE_NORMAL
                                            || player.getCurrentPlayer().getCurrentState() == GSYBaseVideoPlayer.CURRENT_STATE_ERROR
                            ) {
                                Timber.e(">>>>>AutoPlayUtil-onScroll-自动播放=" + index + ">>>状态=" + player.getCurrentPlayer().getCurrentState());
                                if (!NetworkUtil.INSTANCE.isWifi()) {
                                    showWifiDialog(App.getApp(), player);
                                    return;
                                }

                                player.startPlayLogic();
                            } else if (player.getCurrentPlayer().getCurrentState() == GSYBaseVideoPlayer.CURRENT_STATE_PAUSE) {
                                Timber.e(">>>>>AutoPlayUtil-onScroll-恢复播放=" + index + ">>>状态=" + player.getCurrentPlayer().getCurrentState());
                                if (!NetworkUtil.INSTANCE.isWifi()) {
                                    showWifiDialog(App.getApp(), player);
                                    return;
                                }

                                GSYVideoManager.onResume();
                            }
                            break;
                        } else if (player.isInPlayingState()) {
                            Timber.e(">>>>>AutoPlayUtil-onScroll-暂停播放=" + index + ">>>状态=" + player.getCurrentPlayer().getCurrentState());
                            GSYVideoManager.onPause();
                        }
                    }
                }
            }
            if (!hasVideo) {
                Timber.e(">>>>>AutoPlayUtil-onScroll-未找到视频，暂停播放");
                GSYVideoManager.onPause();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e(">>>>>AutoPlayUtil-onScroll-异常=%s", e.getMessage());
        }
    }

    public static void resume() {
        try {
            Timber.e(">>>>>AutoPlayUtil-resume");
            GSYVideoManager.onResume();
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e(">>>>>AutoPlayUtil-resume-异常=%s", e.getMessage());
        }
    }

    public static void pause() {
        try {
            Timber.e(">>>>>AutoPlayUtil-pause");
            GSYVideoManager.onPause();
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e(">>>>>AutoPlayUtil-pause-异常=%s", e.getMessage());
        }
    }

    public static void release() {
        try {
            Timber.e(">>>>>AutoPlayUtil-release");
            GSYVideoManager.releaseAllVideos();
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e(">>>>>AutoPlayUtil-release-异常=%s", e.getMessage());
        }
    }

    private static void showWifiDialog(Context context, final GSYBaseVideoPlayer player) {
        if (!NetworkUtil.INSTANCE.isConnected()) {
            ToastUtil.INSTANCE.longCenter(context.getString(R.string.no_net));
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.tips_not_wifi));
        builder.setPositiveButton(context.getResources().getString(R.string.tips_not_wifi_confirm), (dialog, which) -> {
            dialog.dismiss();
            player.startPlayLogic();
        });
        builder.setNegativeButton(context.getResources().getString(R.string.tips_not_wifi_cancel), (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    /**
     * View可见面积>=90%，视频使用
     */
    public static boolean isViewVisibleOver90(StandardGSYVideoPlayer player) {
        return getViewHeightVisiblePercent(player) >= 0.9F && getViewWidthVisiblePercent(player) >= 0.9F;
    }

    /**
     * View可见面积>=75%
     */
    public static boolean isViewVisibleOver75(View view) {
        return getViewHeightVisiblePercent(view) >= 0.75F && getViewWidthVisiblePercent(view) >= 0.75F;
    }

    /**
     * View不可见
     */
    public static boolean isViewInvisible(View view) {
        return getViewHeightVisiblePercent(view) == 0.F || getViewWidthVisiblePercent(view) == 0.F;
    }

    /**
     * 获取View的可见高度比例
     */
    public static float getViewHeightVisiblePercent(View view) {
        if (view == null) {
            return 0.F;
        }

        float height = view.getHeight();
        Rect rect = new Rect();
        if (!view.getLocalVisibleRect(rect)) {
            return 0.F;
        }

        float visibleHeight = rect.bottom - rect.top;
        return visibleHeight / height;
    }

    /**
     * 获取View的可见宽度比例
     */
    public static float getViewWidthVisiblePercent(View view) {
        if (view == null) {
            return 0.F;
        }

        float width = view.getWidth();
        Rect rect = new Rect();
        if (!view.getLocalVisibleRect(rect)) {
            return 0.F;
        }

        float visibleWidth = rect.right - rect.left;
        return visibleWidth / width;
    }

}