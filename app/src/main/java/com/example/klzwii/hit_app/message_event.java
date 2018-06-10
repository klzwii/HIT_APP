package com.example.klzwii.hit_app;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

public class message_event {
    private String message;
    private JSONObject json;
    public message_event(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setjson(JSONObject json){this.json=json;}

    public JSONObject getJson() {
        return json;
    }
}