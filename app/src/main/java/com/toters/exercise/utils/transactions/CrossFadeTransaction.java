package com.toters.exercise.utils.transactions;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler;
import com.toters.exercise.R;


public class CrossFadeTransaction extends AnimatorChangeHandler {

    @NonNull
    @Override
    protected Animator getAnimator(@NonNull ViewGroup container, @Nullable View from, @Nullable View to, boolean isPush, boolean toAddedToContainer) {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(425L);
        if (isPush) {
            if (to != null) createEnterAnimators(to, set, false);
            if (from != null) createExitAnimators(from, set, false);
        } else {
            if (from != null) createEnterAnimators(from, set, true);
            if (to != null) createExitAnimators(to, set, true);
        }
        return set;
    }

    @Override
    protected void resetFromView(@NonNull View from) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            from.setForeground(null);
        }
        from.setClipBounds(null);
        from.setTranslationY(0F);
    }

    @Override
    public boolean isReusable() {
        return true;
    }

    private void createEnterAnimators(View view, AnimatorSet set, Boolean isReversed) {
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);

        Rect fromRect = new Rect(rect);
        fromRect.top = (int) (fromRect.bottom * 0.959F);

        Rect toRect = new Rect(rect);

        if (isReversed) {
            Rect tmp = fromRect;
            fromRect = toRect;
            toRect = tmp;
        }
        ValueAnimator clipAnimator = ValueAnimator.ofObject(new RectEvaluator(rect), fromRect, toRect);
        clipAnimator.setInterpolator(AnimationUtils.loadInterpolator(view.getContext(), R.anim.fast_out_extra_sloww_in));
        clipAnimator.addUpdateListener(valueAnimator -> view.setClipBounds((Rect) clipAnimator.getAnimatedValue()));

        set.play(clipAnimator);

        float fromY = 0F;
        float toY = view.getHeight() * 0.041F;
        if (isReversed) {
            float tmp = fromY;
            fromY = toY;
            toY = tmp;
        }
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, toY, fromY);
        translateAnimator.setInterpolator(new FastOutSlowInInterpolator());

        set.play(translateAnimator);
    }

    private void createExitAnimators(View view, AnimatorSet set, Boolean isReversed) {
        ColorDrawable colorDrawable = new ColorDrawable(view.getContext().getResources().getColor(R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setForeground(colorDrawable);
        }

        int fromAlpha = 0;
        int toAlpha = 255;
        if (isReversed) {
            int tmp = fromAlpha;
            fromAlpha = toAlpha;
            toAlpha = tmp;
        }

        ValueAnimator fadeAnimator = ValueAnimator.ofInt(fromAlpha, toAlpha);
        fadeAnimator.setInterpolator(new LinearInterpolator());
        fadeAnimator.addUpdateListener(valueAnimator -> colorDrawable.setAlpha((Integer) fadeAnimator.getAnimatedValue()));

        set.play(fadeAnimator);

        float fromY = 0F;
        float toY = view.getHeight() * -0.02F;
        if (isReversed) {
            float tmp = fromY;
            fromY = toY;
            toY = tmp;
        }
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, fromY, toY);
        translateAnimator.setInterpolator(new FastOutSlowInInterpolator());
        set.play(translateAnimator);
    }
}
