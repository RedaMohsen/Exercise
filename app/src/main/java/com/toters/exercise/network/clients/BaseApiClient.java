package com.toters.exercise.network.clients;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.toters.exercise.constants.AppConstants;
import com.toters.exercise.helper.JsonParserHelper;
import com.toters.exercise.helper.PrefHelper;
import com.toters.exercise.network.clients.volley.BaseRequest;
import com.toters.exercise.network.clients.volley.InputStreamVolleyRequest;
import com.toters.exercise.network.clients.volley.MultiPartListRequest;
import com.toters.exercise.network.clients.volley.MultiPartRequest;
import com.toters.exercise.network.clients.volley.RefreshPolicy;
import com.toters.exercise.network.clients.volley.VolleyApiClient;
import com.toters.exercise.network.model.DataPart;
import com.toters.exercise.network.model.responseBody.ResponseBody;
import com.toters.exercise.utils.ErrorHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.rxjava3.core.Single;


public class BaseApiClient {


    //    private final String HEADER_FORM_ENCODED = "application/x-www-form-urlencoded";
    private final String HEADER_FORM_ENCODED = "application/json";
    private final String HEADER_APPLICATION_JSON = "application/json";
    private final String OS_TYPE = "android";

    private VolleyApiClient apiClient;
    private PrefHelper prefHelper;
    private ErrorHandler errorHandler;
    private JsonParserHelper jsonParserHelper;

    BaseApiClient(Context context) {
        this.apiClient = VolleyApiClient.getInstance(context);
        this.prefHelper = new PrefHelper(context);
        this.errorHandler = new ErrorHandler(context);
        this.jsonParserHelper = new JsonParserHelper(errorHandler);
    }

    public <T> Single<T> makePaginatedRequest(BaseRequest baseRequest, Type type) {
        return Single.create(e -> {
            Request request = null;
            Response.Listener listener;
            Gson gson = new Gson();

            Log.d("REQUEST", baseRequest.getUrl());
            if (baseRequest.getType() != BaseRequest.RequestType.BYTE) {
                listener = response -> {
                    ResponseBody<T> responseData = gson.fromJson(response.toString(), type);
                    T parsedResponse = jsonParserHelper.parseBaseObject(response, type);
                    if (parsedResponse != null) {
                        Log.d("REQUEST SUCCESS", baseRequest.getUrl());
                        baseRequest.getListener().onSuccess(parsedResponse,responseData.getStatusCode(),responseData.getMessage());
                        e.onSuccess(parsedResponse);
                    } else {
                        Log.d("REQUEST ERROR", baseRequest.getUrl());
                        baseRequest.getListener().onError(new VolleyError());
                        e.onError(new VolleyError());
                    }
                };
            } else {
                listener = response -> baseRequest.getListener().onSuccess(response,200,"");
            }

            Response.ErrorListener errorListener = error -> {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException | JSONException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } // returned data is not JSONObject?

                }
                handleError(error);
                if (!(error instanceof NoConnectionError)) {
                    baseRequest.getListener().onError(error);
                    e.onError(new VolleyError());
                }
            };

            switch (baseRequest.getType()) {
                case STRING:
                    request = createStringRequest(baseRequest, listener, errorListener);
                    break;
                case JSON_OBJECT:
                    request = createJsonRequest(baseRequest, listener, errorListener);
                    break;
                case MULTIPART_LIST:
                    request = createMultipartListRequest(baseRequest, listener, errorListener, baseRequest.getParams(), baseRequest.getByteListParams());
                    break;
                case MULTIPART:
                    request = createMultipartRequest(baseRequest, listener, errorListener, baseRequest.getParams(), baseRequest.getByteParams());
                    break;
                case BYTE:
                    request = createByteRequest(baseRequest, listener, errorListener);
                    break;
            }
            setRefreshPolicy(request);
            apiClient.getRequestQueue().add(request);
        });
    }

    <T> void makeRequest(BaseRequest baseRequest, Type type) throws ClassCastException {
        Response.Listener listener;
        Request request = null;

        Log.d("REQUEST", baseRequest.getUrl());
        if (baseRequest.getType() != BaseRequest.RequestType.BYTE) {
            listener = response -> {
                String message = "";
                try {
                    Gson gson = new Gson();
                    ResponseBody<T> responseData = gson.fromJson(response.toString(), type);
                    message = responseData.getMessage();

                    Log.d("makeRequest","message: "+message);

                } catch (Exception ex) {
                    Log.e("makeRequest","Exception: ",ex);
                }

                T parsedResponse = jsonParserHelper.parseBaseObject(response, type);
                if (parsedResponse != null) {
                    Log.d("REQUEST SUCCESS", baseRequest.getUrl());
                    try {
                        baseRequest.getListener().onSuccess(parsedResponse,200,message);
                    } catch (ClassCastException e) {
                        baseRequest.getListener().onError(new VolleyError("Invalid API return type"));
                    }
                } else {
                    Log.d("REQUEST ERROR", baseRequest.getUrl());
                    baseRequest.getListener().onError(new VolleyError());
                }
            };
        } else {
            listener = response -> baseRequest.getListener().onSuccess(response,200,"");
        }

        Response.ErrorListener errorListener = error -> {
            NetworkResponse response = error.networkResponse;
            if (error instanceof ServerError && response != null) {
                try {
                    String res = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    // Now you can use any deserializer to make sense of data
                    JSONObject obj = new JSONObject(res);
                } catch (UnsupportedEncodingException | JSONException e1) {
                    // Couldn't properly decode data to string
                    e1.printStackTrace();
                } // returned data is not JSONObject?

            }
            handleError(error);
            if (!(error instanceof NoConnectionError)) {
                baseRequest.getListener().onError(error);
            }
        };

        switch (baseRequest.getType()) {
            case STRING:
                request = createStringRequest(baseRequest, listener, errorListener);
                break;
            case JSON_OBJECT:
                request = createJsonRequest(baseRequest, listener, errorListener);
                break;
            case MULTIPART_LIST:
                request = createMultipartListRequest(baseRequest, listener, errorListener, baseRequest.getParams(), baseRequest.getByteListParams());
                break;
            case MULTIPART:
                request = createMultipartRequest(baseRequest, listener, errorListener, baseRequest.getParams(), baseRequest.getByteParams());
                break;
            case BYTE:
                request = createByteRequest(baseRequest, listener, errorListener);
                break;
        }
        setRefreshPolicy(request);
        apiClient.getRequestQueue().add(request);
    }
    private MultiPartListRequest createMultipartListRequest(BaseRequest baseRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, HashMap<String, String> params, Map<String, List<DataPart>> byteParams) {
        return new MultiPartListRequest(
                Request.Method.POST,
                baseRequest.getUrl(),
                listener,
                errorListener) {

            @Override
            public Map<String, String> getHeaders() {
                return getHeaderParams(baseRequest);
            }

            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            protected Map<String, List<DataPart>> getByteData() {
                return byteParams;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                handleParseNetworkError(this, volleyError);
                return super.parseNetworkError(volleyError);
            }

        };
    }

    private MultiPartRequest createMultipartRequest(BaseRequest baseRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Map<String, String> params, Map<String, DataPart> byteParams) {
        return new MultiPartRequest(
                Request.Method.POST,
                baseRequest.getUrl(),
                listener,
                errorListener) {

            @Override
            public Map<String, String> getHeaders() {
                return getHeaderParams(baseRequest);
            }

            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                return byteParams;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                handleParseNetworkError(this, volleyError);
                return super.parseNetworkError(volleyError);
            }

        };

    }
    private JsonObjectRequest createJsonRequest(BaseRequest baseRequest, Response.Listener listener, Response.ErrorListener errorListener) {
        return new JsonObjectRequest(baseRequest.getMethod(), baseRequest.getUrl(), baseRequest.getJsonRequest(), listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                return getHeaderParams(baseRequest);
            }

            @Override
            protected Map<String, String> getParams() {
                return baseRequest.getParams();
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                handleParseNetworkError(this, volleyError);
                return super.parseNetworkError(volleyError);
            }
        };
    }

    private StringRequest createStringRequest(BaseRequest baseRequest, Response.Listener listener, Response.ErrorListener errorListener) {
        return new StringRequest(baseRequest.getMethod(), baseRequest.getUrl(), listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                return getHeaderParams(baseRequest);
            }

            @Override
            protected Map<String, String> getParams() {
                return baseRequest.getParams();
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                handleParseNetworkError(this, volleyError);
                return super.parseNetworkError(volleyError);
            }
        };
    }

    private InputStreamVolleyRequest createByteRequest(BaseRequest baseRequest, Response.Listener listener, Response.ErrorListener errorListener) {
        return new InputStreamVolleyRequest(baseRequest.getMethod(), baseRequest.getUrl(), listener, errorListener, baseRequest.getParams()) {
            @Override
            public Map<String, String> getHeaders() {
                return getHeaderParams(baseRequest);
            }

            @Override
            protected Map<String, String> getParams() {
                return baseRequest.getParams();
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                handleParseNetworkError(this, volleyError);
                return super.parseNetworkError(volleyError);
            }
        };
    }

    private Map<String, String> getHeaderParams(BaseRequest baseRequest) {
        Map<String, String> params = new HashMap<>();
        if (baseRequest.getMethod() == Request.Method.POST && baseRequest.getType() != BaseRequest.RequestType.MULTIPART) {
            params.put("Content-Type", HEADER_APPLICATION_JSON);
            params.put("Accept-Type", HEADER_APPLICATION_JSON);
        }

        return params;
    }

    private void setRefreshPolicy(Request request) {
        request.setRetryPolicy(new RefreshPolicy(
                AppConstants.HTTP_TIMEOUT,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT,
                this));
    }


    private void handleError(VolleyError error) {
        if (error.networkResponse != null) {
            errorHandler.parseError(error);
        } else {
            errorHandler.showUnexpectedError(error);
        }
    }

    private void handleParseNetworkError(Request request, VolleyError volleyError) {
        if (volleyError instanceof AuthFailureError) {
        }
        if (volleyError instanceof NoConnectionError) {
            Set set = ErrorHandler.internetRelay.getValue();
            set.add(request);
            ErrorHandler.internetRelay.accept(set);
        }
    }



    <T> void makeCustomRequest(BaseRequest baseRequest, Type type) {
        Response.Listener listener;
        Request request = null;

        Log.d("REQUEST", baseRequest.getUrl());
        if (baseRequest.getType() != BaseRequest.RequestType.BYTE) {
            listener = response -> {
                T parsedResponse = jsonParserHelper.parseCustomObject(response, type);
                if (parsedResponse != null) {
                    Log.d("REQUEST SUCCESS", baseRequest.getUrl());
                    baseRequest.getListener().onSuccess(parsedResponse,200,"");
                } else {
                    Log.d("REQUEST ERROR", baseRequest.getUrl());
                    baseRequest.getListener().onError(new VolleyError());
                }
            };
        } else {
            listener = response -> baseRequest.getListener().onSuccess(response,200,"");
        }

        Response.ErrorListener errorListener = error -> {
            handleError(error);
            if (!(error instanceof NoConnectionError)) {
                baseRequest.getListener().onError(error);
            }
        };

        switch (baseRequest.getType()) {
            case STRING:
                request = createStringRequest(baseRequest, listener, errorListener);
                break;
            case JSON_OBJECT:
                request = createJsonRequest(baseRequest, listener, errorListener);
                break;
            case MULTIPART_LIST:
                request = createMultipartListRequest(baseRequest, listener, errorListener, baseRequest.getParams(), baseRequest.getByteListParams());
                break;
            case MULTIPART:
                request = createMultipartRequest(baseRequest, listener, errorListener, baseRequest.getParams(), baseRequest.getByteParams());
                break;
            case BYTE:
                request = createByteRequest(baseRequest, listener, errorListener);
                break;
        }
        setRefreshPolicy(request);
        apiClient.getRequestQueue().add(request);
    }
}