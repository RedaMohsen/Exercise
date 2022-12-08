package com.toters.exercise.helper;



import android.content.Context;

import com.toters.exercise.constants.AppConstants;
import com.toters.exercise.constants.PrefConstants;
import com.toters.exercise.preferences.AppPreferences;

public class PrefHelper extends AppPreferences {

    private AppPreferences appPreferences;

    public PrefHelper(Context context) {
        super(context);
        appPreferences = new AppPreferences(context);
    }







    public String getCurrentLanguage() {
        return getString(PrefConstants.PREF_LANGUAGE).isEmpty() ? AppConstants.EN : getString(PrefConstants.PREF_LANGUAGE);
    }

    public void saveCurrentLanguage(String language) {
        writeString(PrefConstants.PREF_LANGUAGE, language);
    }

}