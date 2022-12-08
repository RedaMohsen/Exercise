package com.toters.exercise.network.clients.volley;

import com.android.volley.VolleyError;
import com.toters.exercise.network.model.DataPart;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseRequest {
    private RequestType type;
    private int method;
    private String url;
    private ResponseListener listener;
    private HashMap<String, String> params;
    private JSONObject jsonRequest;
    private HashMap<String, List<DataPart>> byteListParams;
    private HashMap<String, DataPart> byteParams;

    private BaseRequest(Builder builder) {
        type = builder.type;
        method = builder.method;
        url = builder.url;
        listener = builder.listener;
        params = builder.params;
        jsonRequest = builder.jsonRequest;
        byteListParams = builder.byteListParams;
        byteParams = builder.byteParams;
    }

    public String getUrl() {
        return url;
    }

    public ResponseListener getListener() {
        return listener;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public JSONObject getJsonRequest() {
        return jsonRequest;
    }

    public Map<String, List<DataPart>> getByteListParams() {
        return byteListParams;
    }

    public Map<String, DataPart> getByteParams() {
        return byteParams;
    }

    public int getMethod() {
        return method;
    }

    public RequestType getType() {
        return type;
    }

    public static class Builder {
        private RequestType type;
        private int method;
        private String url;
        private ResponseListener listener;
        private HashMap<String, String> params;
        private JSONObject jsonRequest;
        private HashMap<String, List<DataPart>> byteListParams;
        private HashMap<String, DataPart> byteParams;

        public Builder() {

        }

        public Builder setType(RequestType type) {
            this.type = type;
            return this;
        }

        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setListener(ResponseListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setParams(HashMap<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder setJsonRequest(JSONObject jsonRequest) {
            this.jsonRequest = jsonRequest;
            return this;
        }

        public Builder setByteListParams(HashMap<String, List<DataPart>> byteListParams) {
            this.byteListParams = byteListParams;
            return this;
        }

        public Builder setByteParams(HashMap<String, DataPart> byteParams) {
            this.byteParams = byteParams;
            return this;
        }

        public BaseRequest build() {
            return new BaseRequest(this);
        }
    }

    public enum RequestType {
        STRING,
        JSON_OBJECT,
        MULTIPART_LIST,
        MULTIPART,
        BYTE
    }

    public interface ResponseListener<T> {
        void onSuccess(T response, int statusCode, String message);

        void onError(VolleyError error);
    }
}

