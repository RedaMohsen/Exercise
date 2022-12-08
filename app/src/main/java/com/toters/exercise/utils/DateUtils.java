package com.toters.exercise.utils;

import android.util.Log;

import java.text.SimpleDateFormat;

public class DateUtils {


    public static String getPlansDays(String startDate, String endDate) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outFormat = new SimpleDateFormat("dd");

        String days = "";
        try {
            days = String.format("%s%s%s", outFormat.format(inFormat.parse(startDate)), "-", outFormat.format(inFormat.parse(endDate)));


        } catch (Exception used) {
            Log.e("THE_KODE", "Trip Days Exception: ", used);
        }

        return days;
    }

    public static String getPlansDate(String startDate, String endDate) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outFormat = new SimpleDateFormat("MMM yyyy");

        String date = "";
        try {

            if (!outFormat.format(inFormat.parse(startDate)).equals(outFormat.format(inFormat.parse(endDate))))
                date = String.format("%s %s", outFormat.format(inFormat.parse(startDate)), outFormat.format(inFormat.parse(endDate)));

            else date = outFormat.format(inFormat.parse(endDate));

        } catch (Exception used) {
            Log.e("THE_KODE", "Trip Days Exception: ", used);
        }

        return date;
    }
}
