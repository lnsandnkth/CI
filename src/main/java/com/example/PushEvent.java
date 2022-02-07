package com.example;

import com.google.gson.JsonElement;

public class PushEvent {

    public static String TYPE = "push";

    public String type;

    public PushEvent(JsonElement request) {

        this.type = PushEvent.TYPE;

    }
}
