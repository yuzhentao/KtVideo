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

import static com.yuzhentao.ktvideo.view.progressbutton.ThemeUtils.getThemeAccentColor;
import static com.yuzhentao.ktvideo.view.progressbutton.ThemeUtils.getThemePrimaryColor;

public class ProgressFloatingActionButton extends FrameLayout /*implements ProgressView.OnCompletedListener*/ {

    private FloatingActionButton fab;
    private AppCompatImageView iv;
    private ProgressView progressView;

    private Drawable mFinalIcon;
    private OnClickListener mListener;

    private int mPrimaryColor;
    private int mAccentColor;
    private int mAccentColorLight;

    public ProgressFloatingActionButton(Context context) {
        this(context, null, 0);
    }

    public ProgressFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(getContext(), R.layout.view_progress_button, this);
        fab = view.findViewById(R.id.pfFab);
        iv = view.findViewById(R.id.iv);
        progressView = view.findViewById(R.id.pfProgress);

        mPrimaryColor = getThemePrimaryColor(context);
        mAccentColor = getThemeAccentColor(context);
        mAccentColorLight = ColorUtils.lighten(mAccentColor, 0.5f);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ProgressFloatingActionButton, defStyleAttr, 0);

        if (a.hasValue(R.styleable.ProgressFloatingActionButton_pFabProgressIcon))
            setIcon(a.getDrawable(R.styleable.ProgressFloatingActionButton_pFabProgressIcon));

        if (a.hasValue(R.styleable.ProgressFloatingActionButton_pFabFinalIcon))
            setFinalIcon(a.getDrawable(R.styleable.ProgressFloatingActionButton_pFabFinalIcon));

        setStartingProgress(
                a.getInt(R.styleable.ProgressFloatingActionButton_pFabStartingProgress, 0)
        );

        setCurrentProgress(
                a.getInt(R.styleable.ProgressFloatingActionButton_pFabCurrentProgress,
                        progressView.mStartingProgress),
                false
        );

        setTotalProgress(
                a.getInt(R.styleable.ProgressFloatingActionButton_pFabTotalProgress, 100)
        );

        setStepSize(
                a.getInt(R.styleable.ProgressFloatingActionButton_pFabStepSize, 10)
        );

        setProgressColor(
                a.getColor(R.styleable.ProgressFloatingActionButton_pFabCircleColor,
                        mPrimaryColor
                )
        );

        setFabColor(
                a.getColor(
                        R.styleable.ProgressFloatingActionButton_pFabColor,
                        mAccentColor
                )
        );

        setRippleColor(
                a.getColor(
                        R.styleable.ProgressFloatingActionButton_pFabRippleColor,
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