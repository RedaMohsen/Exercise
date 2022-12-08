package com.toters.exercise.utils.transactions;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler;

public class SplashTransaction extends AnimatorChangeHandler {
    Long mDuration=2000L;
    @NonNull
    @Override
    protected Animator getAnimator(@NonNull ViewGroup container, @Nullable View from, @Nullable View to, boolean isPush, boolean toAddedToContainer) {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(mDuration);
        if (isPush) {
            if (to != null && from != null) createEnterAnimators(to, from, set);
        }
        return set;
    }

    public SplashTransaction(){}

    public SplashTransaction(Long duration){
        this.mDuration=duration;
    }

    @Override
    protected void resetFromView(@NonNull View from) {

    }

    @Override
    public boolean isReusable() {
        return true;
    }

    private void createEnterAnimators(View view, View from, AnimatorSet set) {

        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (view.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,view.getContext().getResources().getDisplayMetrics());
        }





        from.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(from.getDrawingCache(), 0, 0, from.getWidth()/2, from.getHeight());
        Bitmap bottomBitmap = Bitmap.createBitmap(from.getDrawingCache(), from.getWidth()/2, 0, from.getWidth()/2, from.getHeight());
        from.setDrawingCacheEnabled(false);

        ImageView top = new ImageView(view.getContext());
        ImageView bottom = new ImageView(view.getContext());

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        top.setLayoutParams(lp);
        bottom.setLayoutParams(lp);

        top.getLayoutParams().width = from.getWidth() / 2;

        bottom.getLayoutParams().width = from.getWidth() / 2;
        bottom.setTranslationX(from.getWidth() / 2f);

        top.setImageBitmap(bitmap);
        bottom.setImageBitmap(bottomBitmap);

        // Finally, add the ImageView to layout
        ((ConstraintLayout) view).addView(top);
        ((ConstraintLayout) view).addView(bottom);

        from.setVisibility(View.GONE);


        float fromX=view.getWidth()/2f;
        float toX=view.getWidth();

        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(top, View.TRANSLATION_X, 0, -fromX);
        translateAnimator.setInterpolator(new FastOutSlowInInterpolator());

        ObjectAnimator translateAnimator2 = ObjectAnimator.ofFloat(bottom, View.TRANSLATION_X, fromX, toX);
        translateAnimator2.setInterpolator(new FastOutSlowInInterpolator());

        /*for a vertical animation with top and bottom half*/


        set.playTogether(translateAnimator, translateAnimator2);

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((ConstraintLayout) view).removeView(top);
                ((ConstraintLayout) view).removeView(bottom);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}

/*For spliting view horizontally and animation */
/*  from.setDrawingCacheEnabled(true);
          Bitmap bitmap = Bitmap.createBitmap(from.getDrawingCache(), 0, 0, from.getWidth(), from.getHeight() / 2);
          Bitmap bottomBitmap = Bitmap.createBitmap(from.getDrawingCache(), 0, from.getHeight() / 2, from.getWidth(), from.getHeight() / 2);
          from.setDrawingCacheEnabled(false);

          ImageView top = new ImageView(view.getContext());
          ImageView bottom = new ImageView(view.getContext());

          ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

          top.setLayoutParams(lp);
          bottom.setLayoutParams(lp);

          top.getLayoutParams().height = from.getHeight() / 2;

          bottom.getLayoutParams().height = from.getHeight() / 2;
          bottom.setTranslationY(from.getHeight() / 2f);

          top.setImageBitmap(bitmap);
          bottom.setImageBitmap(bottomBitmap);

          // Finally, add the ImageView to layout
          ((ConstraintLayout) view).addView(top);
          ((ConstraintLayout) view).addView(bottom);

          from.setVisibility(View.GONE);

          float fromY = 0F;
          float toY = view.getHeight() / 2f * -1F + actionBarHeight;
          float fromY2 = view.getHeight() / 2f;
          float toY2 = view.getHeight();

          float fromX=view.getWidth()/2f;
          float toX=0f;
          float toX2=view.getWidth();

          ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(top, View.TRANSLATION_Y, fromY, toY);
          translateAnimator.setInterpolator(new FastOutSlowInInterpolator());

          ObjectAnimator translateAnimator2 = ObjectAnimator.ofFloat(bottom, View.TRANSLATION_Y, fromY2, toY2);
          translateAnimator2.setInterpolator(new FastOutSlowInInterpolator());*/

