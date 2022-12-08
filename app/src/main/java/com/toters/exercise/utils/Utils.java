package com.toters.exercise.utils;

import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.security.MessageDigest;

public class Utils {

    public static void showSnackBar(ViewGroup view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);

        View nav = null;
        for (int i = 0; i < view.getChildCount(); i++) {
            if (view.getChildAt(i) instanceof BottomNavigationView) {
                nav = view.getChildAt(i);
                break;
            }
        }
        if (nav != null)
            snackbar.setAnchorView(nav);
        else {
            try {
//                snackbar.setAnchorView(R.id.navigation);
            } catch (IllegalArgumentException ignored) {

            }
        }

        snackbar.show();
    }

    public static String capitalizeVendorType(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
