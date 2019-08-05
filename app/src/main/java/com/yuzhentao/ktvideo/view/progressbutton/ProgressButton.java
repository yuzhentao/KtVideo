package com.yuzhentao.ktvideo.view.progressbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.yuzhentao.ktvideo.R;
import com.yuzhentao.ktvideo.util.ColorUtils;
import com.yuzhentao.ktvideo.util.ThemeUtils;

public class ProgressButton extends FrameLayout /*implements ProgressView.OnCompletedListener*/ {

    private FloatingActionButton fab;
    private AppCompatImageView iv;
    private ProgressView progressView;

    private Drawable mFinalIcon;
    private OnClickListener mListener;

    private int mPrimaryColor;
    private int mAccentColor;
    private int mAccentColorLight;

    public ProgressButton(Context context) {
        this(context, null, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(getContext(), R.layout.view_progress_button, this);
        fab = view.findViewById(R.id.pfFab);
        iv = view.findViewById(R.id.iv);
        progressView = view.findViewById(R.id.pfProgress);

        mPrimaryColor = ThemeUtils.INSTANCE.getThemePrimaryColor(context);
        mAccentColor = ThemeUtils.INSTANCE.getThemeAccentColor(context);
        mAccentColorLight = ColorUtils.INSTANCE.lighten(mAccentColor, 0.5f);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ProgressButton, defStyleAttr, 0);

        if (a.hasValue(R.styleable.ProgressButton_pb_fab_progress_icon))
            setIcon(a.getDrawable(R.styleable.ProgressButton_pb_fab_progress_icon));

        if (a.hasValue(R.styleable.ProgressButton_pb_fab_final_icon))
            setFinalIcon(a.getDrawable(R.styleable.ProgressButton_pb_fab_final_icon));

        setStartingProgress(
                a.getInt(R.styleable.ProgressButton_pb_fab_starting_progress, 0)
        );

        setCurrentProgress(
                a.getInt(R.styleable.ProgressButton_pb_fab_current_progress,
                        progressView.mStartingProgress),
                false
        );

        setTotalProgress(
                a.getInt(R.styleable.ProgressButton_pb_fab_total_progress, 100)
        );

        setStepSize(
                a.getInt(R.styleable.ProgressButton_pb_fab_step_size, 10)
        );

        setProgressColor(
                a.getColor(R.styleable.ProgressButton_pb_fab_circle_color,
                        mPrimaryColor
                )
        );

        setFabColor(
                a.getColor(
                        R.styleable.ProgressButton_pb_fab_color,
                        mAccentColor
                )
        );

        setRippleColor(
                a.getColor(
                        R.styleable.ProgressButton_pb_fab_ripple_color,
                        mAccentColorLight
                )
        );

        a.recycle();

//        progressView.setListener(this);
//        fab.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressView.next(true);
//            }
//        });

//        iv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressView.next(true);
//            }
//        });
    }

    /*
     * ({@link ProgressView}) will notify the ({@link ProgressFloatingActionButton}) once the
     * progress has been completed through the ({@link ProgressView.OnCompletedListener}) interface.
     * */
/*    @Override
    public void onProgressCompleted() {
        // If the progress is completed the FAB will change
        if (mFinalIcon != null) {
            fab.setImageDrawable(mFinalIcon);
            iv.setImageDrawable(mFinalIcon);
        }
        fab.setOnClickListener(mListener);
        iv.setOnClickListener(mListener);
    }*/

    /*
     * Getters and Setters
     * */
    public FloatingActionButton getFab() {
        return fab;
    }

    public void setStartingProgress(int start) {
        progressView.mStartingProgress = start;
    }

    public void setCurrentProgress(int current, boolean animate) {
        progressView.setCurrentProgress(current, animate);
    }

    public void setTotalProgress(int total) {
        progressView.mTotalProgress = total;
    }

    public void setStepSize(int size) {
        progressView.mStepSize = size;
    }

    public void setIcon(Drawable icon) {
        if (icon != null) {
            fab.setImageDrawable(icon);
            iv.setImageDrawable(icon);
        }
    }

    public void setImageRes(int resId) {
        if (resId > 0) {
            fab.setImageResource(resId);
            iv.setImageResource(resId);
        }
    }

    public void setFinalIcon(Drawable mFinalIcon) {
        this.mFinalIcon = mFinalIcon;
    }

    public void setProgressColor(int color) {
        progressView.setColor(color);
    }

    public void setFabColor(int color) {
        fab.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    public void setRippleColor(int color) {
        fab.setRippleColor(color);
    }

    public void setCompletedListener(OnClickListener mListener) {
        this.mListener = mListener;
    }

}