package com.toters.exercise.utils;

import android.view.View;

public class ClickableSpan extends android.text.style.ClickableSpan {
    String TAG="";
    clickableSpanInterface mListener;
    public ClickableSpan( String TAG, clickableSpanInterface listener){
        this.TAG=TAG;
        this.mListener=listener;
    }

    @Override
    public void onClick(View widget) {
        if (mListener!=null)
            mListener.onSpanClicked(TAG);
    }

    public interface clickableSpanInterface{
        void onSpanClicked(String TAG);
    }
}