package com.toters.exercise.preferences;


import android.content.Context;
import android.content.SharedPreferences;


public class AppPreferences {
    private SharedPreferences prefs;

    public AppPreferences(Context context) {
        prefs = context.getSharedPreferences("exercise_app_pref", 0);
    }

    public String getString(String key) {
        return prefs.getString(key, "");
    }

    public boolean getTrueBoolean(String key) {
        return prefs.getBoolean(key, true);
    }

    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    public int getInt(String key) {
        return prefs.getInt(key, -1);
    }

    public long getLong(String key) {
        return prefs.getLong(key, -1);
    }

    public void writeString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public void writeBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public void writeFloat(String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }

    public float getFloat(String key) {
        return prefs.getFloat(key, 1f);
    }

    public void writeInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public void writeLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }



    public void cleanPref() {
        prefs.edit().clear().apply();
    }

    public void removePref(String key) {
        prefs.edit().remove(key).apply();
    }

}
