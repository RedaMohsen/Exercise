package com.toters.exercise.helper;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import com.toters.exercise.R;


public class FontsHelper {

    private final Context context;

    public FontsHelper(Context context) {
        this.context = context;
    }

    public Typeface getFont(int resourceId) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return context.getResources().getFont(resourceId);
        }
        return ResourcesCompat.getFont(context, resourceId);
    }

    public Typeface regular() {
        return getFont(R.font.poppins_regular);
    }

    public Typeface regularHelvetica() {
        return getFont(R.font.helvetica);
    }

    public Typeface medium() {
        return getFont(R.font.poppins_medium);
    }

    public Typeface bold() {
        return getFont(R.font.poppins_bold);
    }

    public Typeface boldHelvetica() {
        return getFont(R.font.helvetica_bold);
    }


}
