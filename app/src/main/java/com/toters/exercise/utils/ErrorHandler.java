package com.toters.exercise.utils;


import static com.toters.exercise.constants.StatusCodes.STATUS_VIOLATION;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.toters.exercise.R;
import com.toters.exercise.network.clients.volley.ErrorResponseBody;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ErrorHandler {
    public static final BehaviorRelay<Set<Request>> internetRelay = BehaviorRelay.createDefault(new HashSet<>());
    private static final PublishRelay<Boolean> logoutRelay = PublishRelay.create();
    private static final PublishRelay<VolleyError> errorRelay = PublishRelay.create();
    private final Context context;

    public ErrorHandler(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseError(VolleyError error) {
        ErrorResponseBody body = null;
        try {
            Log.e("REQUEST ERROR DATA", new String(error.networkResponse.data, StandardCharsets.UTF_8));
            body = new Gson().fromJson(new String(error.networkResponse.data, StandardCharsets.UTF_8), ErrorResponseBody.class);
        } catch (JsonParseException e) {
            Log.e("ErrorHandler", error.getClass().getSimpleName() + "JsonParseException", e);

            showUnexpectedError(error);
        }
        if (body != null ) {
            if (body.getErrors()!=null && body.getErrors().size() > 0) {
                String output = body
                        .getErrors()
                        .stream()
                        .map(ErrorResponseBody.APIError::getMessage)
                        .reduce("", (a, b) -> String.format("%s\n%s", a, b))
                        .trim();
                switch (body.getCode()) {
                    case STATUS_VIOLATION:
                        parseUnauthorizedError(new VolleyError(output));
                        break;

                    default:
                        VolleyError outputError = new VolleyError(output);
                        errorRelay.accept(outputError);
                        break;
                }
            } else {
                switch (body.getCode()) {
                    case STATUS_VIOLATION:
                        parseUnauthorizedError(new VolleyError(body.getMessage()));
                        break;
                    /*case STATUS_LOGIN_REQUIRED:
                        parseUnauthorizedError(new VolleyError(body.getMessage()));
                        break;*/
                    default:
                        VolleyError outputError = new VolleyError(body.getMessage());
                        errorRelay.accept(outputError);
                        break;
                }
            }
        }

    }

    public void showUnexpectedError(VolleyError error) {
        Log.e("ErrorHandler", "showUnexpectedError: " + error.getClass().getSimpleName());


        if (error instanceof NoConnectionError){
            if (!internetRelay.getValue().isEmpty())
                internetRelay.accept(internetRelay.getValue());
        }

        else if (error instanceof ServerError) {
            Toast.makeText(context, context.getResources().getString(R.string.error_server), Toast.LENGTH_SHORT).show();
        } else if (error instanceof NetworkError)
            Toast.makeText(context, context.getResources().getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        else if (error instanceof ParseError){
            Toast.makeText(context,context.getResources().getString(R.string.error_parse),Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(context,context.getResources().getString(R.string.error_server),Toast.LENGTH_SHORT).show();

    }

    public Observable<Set<Request>> getInternetRelay () {
        return internetRelay.filter(requests -> requests.size() > 0).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<VolleyError> getErrorRelay () {
        return errorRelay.observeOn(AndroidSchedulers.mainThread());
    }

    public void parseUnauthorizedError (VolleyError error){
        Toast.makeText(context, context.getResources().getString(R.string.error_unauthorized_access), Toast.LENGTH_SHORT).show();
        logoutRelay.accept(true);
    }

    public Observable<Boolean> getLogoutRelay () {
        return logoutRelay.distinctUntilChanged();
    }

}