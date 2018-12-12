package com.cmk.app.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by cmk on 2018/11/26.
 */

public class CircularRevealAnim {

    private AnimListener animListener;
    private static int DURATION = 400;
    private Animator animator;

    public interface AnimListener {
        void onShowAnimationEnd();

        void onHideAnimationEnd();
    }

    public void actionOtherVisible(final boolean isShow, View triggerView, final View animView) {

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (isShow) {
                animView.setVisibility(View.VISIBLE);
                if (animListener != null) animListener.onShowAnimationEnd();
            } else {
                animView.setVisibility(View.GONE);
                if (animListener != null) animListener.onHideAnimationEnd();
            }
            return;
        }

        /**
         * 计算 triggerView 的中心位置
         */
        int[] tvLocation = new int[2];
        triggerView.getLocationInWindow(tvLocation);
        int tvX = tvLocation[0] + triggerView.getWidth() / 2;
        int tvY = tvLocation[1] + triggerView.getHeight() / 2;
        /**
         * 计算 animView 的中心位置
         */
        int[] avLocation = new int[2];
        animView.getLocationInWindow(avLocation);
        int avX = avLocation[0] + animView.getWidth() / 2;
        int avY = avLocation[1] + animView.getHeight() / 2;

        int rippleW = tvX < avX ? animView.getWidth() - tvX : tvX - avLocation[0];
        int rippleH = tvY < avY ? animView.getWidth() - tvY : tvY - avLocation[1];

        float maxRadius = (float) Math.sqrt(rippleW * rippleW + rippleH * rippleH);
        float startRadius;
        float endRadius;

        if (isShow) {
            startRadius = 0;
            endRadius = maxRadius;
        } else {
            endRadius = 0;
            startRadius = maxRadius;
        }

        animator = ViewAnimationUtils.createCircularReveal(animView, tvX, tvY, startRadius, endRadius);
        animView.setVisibility(View.VISIBLE);
        animator.setDuration(DURATION);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isShow) {
                   animView.setVisibility(View.VISIBLE);
                    if (animListener != null) animListener.onShowAnimationEnd();
                } else {
                    animView.setVisibility(View.GONE);
                    if (animListener != null) animListener.onHideAnimationEnd();
                }
            }
        });
        animator.start();
    }

    public void show(View triggerView, View showView) {
        actionOtherVisible(true, triggerView, showView);
    }

    public void hide(View triggerView, View hideView) {
        actionOtherVisible(false, triggerView, hideView);
    }

    public void setAnimListener(AnimListener listener) {
        this.animListener = listener;
    }
}
