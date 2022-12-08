package com.toters.exercise.network.clients.volley;


import com.android.volley.AuthFailureError;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.toters.exercise.network.clients.BaseApiClient;


public class RefreshPolicy implements RetryPolicy {
    private int mCurrentTimeoutMs;
    private int mCurrentRetryCount;
    private final int mMaxNumRetries;
    private final float mBackoffMultiplier;

    private BaseApiClient baseApiClient;

    public RefreshPolicy(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier, BaseApiClient baseApiClient) {
        mCurrentTimeoutMs = initialTimeoutMs;
        mMaxNumRetries = maxNumRetries;
        mBackoffMultiplier = backoffMultiplier;
        this.baseApiClient = baseApiClient;
    }

    @Override
    public int getCurrentTimeout() {
        return mCurrentTimeoutMs;
    }

    @Override
    public int getCurrentRetryCount() {
        return mCurrentRetryCount;
    }

    public float getBackoffMultiplier() {
        return mBackoffMultiplier;
    }

    @Override
    public void retry(VolleyError error) throws VolleyError {
        mCurrentRetryCount++; //increment our retry count
        mCurrentTimeoutMs += (mCurrentTimeoutMs * mBackoffMultiplier);

        if (error instanceof AuthFailureError) { //we got a 401, and need a new token
            //baseApiClient.refreshToken();
        }

        if (!hasAttemptRemaining()) {
            throw error;
        }
    }

    protected boolean hasAttemptRemaining() {
        return mCurrentRetryCount <= mMaxNumRetries;
    }
}
