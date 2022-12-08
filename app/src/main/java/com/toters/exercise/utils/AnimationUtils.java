package com.toters.exercise.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import com.toters.exercise.constants.AppConstants;


public class AnimationUtils {

    public static void animateViewVisibility(View v, float action, int duration){
        v.animate().alpha(action).setDuration(duration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (action == AppConstants.ANIMATE_FADE_OUT){
                    v.setVisibility(View.GONE);
                }else if(action == AppConstants.ANIMATE_FADE_IN){
                    v.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    public static void rotateView(View view,float start, float end) {
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", start, end);
        rotate.setDuration(500);
        rotate.start();
    }


    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    public static ValueAnimator getScaleExpandAnimationExpand(final View v, int duration)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "scaleY", 0f,1f);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                v.setPivotY(0f);
                v.setVisibility(View.VISIBLE);
            }
        });
        return animator;
    }


    public static ValueAnimator getScaleCollapseAnimationCollapse(final View v, int duration)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "scaleY", 1f,0f);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                v.setPivotY(1f);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                v.setVisibility(View.GONE);
            }
        });
        return animator;
    }


    public static void slideUp(int duration,final View view,final View spacingView){

        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY",0f,-350f);
        animation.setDuration(duration);
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
                if (spacingView!=null) {
                    spacingView.setVisibility(View.GONE);
                }
            }
        });
        animation.start();

    }

    public static void slideDown(int duration,View view,final View spacingView){
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY",   -350f,0f);
        animation.setDuration(duration);

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.VISIBLE);
                if (spacingView!=null) {
                    spacingView.setVisibility(View.VISIBLE);
                }
            }
        });
        animation.start();

    }

    public static void translateView(View view,int fromXDelta,int toXDelta,int fromYDelta,int toYDelta,boolean visibility){
        TranslateAnimation anim = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        anim.setDuration(1000);

        anim.setAnimationListener(new TranslateAnimation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation)
            {
//                if (visibility)
//                    view.setVisibility(View.VISIBLE);
//                else view.setVisibility(View.GONE);

//                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)view.getLayoutParams();
//                params.topMargin += amountToMoveDown;
//                params.leftMargin += amountToMoveRight;
//                view.setLayoutParams(params);
            }
        });

        view.startAnimation(anim);
    }
}
