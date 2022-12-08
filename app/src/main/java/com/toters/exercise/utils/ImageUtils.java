package com.toters.exercise.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.toters.exercise.R;


public class ImageUtils {

    public static void loadCrossFadeImage(String url, ImageView imgView, int errorId, int radius) {
        DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        RequestOptions requestOptions = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(radius));

        Glide.with(imgView.getContext())
                .asDrawable()
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .apply(requestOptions)
                .error(errorId)
                .into(imgView);
    }

    public static void loadCrossFadeImagePlaceholder(String url, ImageView imgView, int errorId) {
        DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        RequestOptions requestOptions = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(24));

        Glide.with(imgView.getContext())
                .asDrawable()
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .apply(requestOptions)
                .error(errorId)
                .placeholder(errorId)
                .into(imgView);
    }

    public static void loadCircleImage(String url, ImageView imgView, int ic_user_image_placeholder) {
        DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        RequestOptions requestOptions = RequestOptions.circleCropTransform();

        Glide.with(imgView.getContext())
                .asDrawable()
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .apply(requestOptions)
                .placeholder(ic_user_image_placeholder)
                .load(url)
                .into(imgView);
    }

    public static void loadImage(String url, ImageView imgView) {
        Glide.with(imgView.getContext())
                .load(url)
                .into(imgView);
    }

    public static void loadImage(String url, ImageView imgView, RequestListener<Drawable> listener) {
        Glide.with(imgView.getContext())
                .load(url)
                .addListener(listener)
                .into(imgView);
    }

    public static void loadImage(String url, ImageView imgView, int placeholderId) {
        Glide.with(imgView.getContext())
                .load(url)
                .error(placeholderId)
                .into(imgView);
    }
}
