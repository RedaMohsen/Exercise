package com.toters.exercise.network.clients.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyApiClient {

    private static VolleyApiClient apiClient;
    private static RequestQueue requestQueue;
    private static ImageLoader mImageLoader;

    private VolleyApiClient() {
    }

    public static VolleyApiClient getInstance(Context context) {
        if (apiClient == null) {
            requestQueue = Volley.newRequestQueue(context);
            apiClient = new VolleyApiClient();



            mImageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap> cache = new LruCache<>(1000);

                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                }
            });


        }
        return apiClient;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
