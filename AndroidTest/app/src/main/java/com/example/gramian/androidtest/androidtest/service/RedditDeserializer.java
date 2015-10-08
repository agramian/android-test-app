package com.example.gramian.androidtest.androidtest.service;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

class Content {
    public String data;
}

class RedditDeserializer implements JsonDeserializer<Content>
{
    @Override
    public Content deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        // Get the "data" element from the parsed JSON
        JsonElement content = je.getAsJsonObject().get("data")
                .getAsJsonObject().getAsJsonArray("children").get(0);

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(content, Content.class);

    }
}