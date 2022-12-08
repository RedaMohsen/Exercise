package com.toters.exercise.helper;

import android.content.Context;


public class ResourceHelper {
    private Context context;

    public ResourceHelper(Context context) {
        this.context = context;
    }

    public String getString(int resourceId) {
        return context.getString(resourceId);
    }

    public int getColor(int resourceId){return context.getColor(resourceId);}
}
