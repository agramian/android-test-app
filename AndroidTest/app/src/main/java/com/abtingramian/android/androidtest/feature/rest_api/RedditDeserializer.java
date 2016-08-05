package com.abtingramian.android.androidtest.feature.rest_api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

class RedditDeserializer implements JsonDeserializer<RedditService.SearchResult>
{
    @Override
    public RedditService.SearchResult deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        // Get the "data" element from the parsed JSON
        JsonArray children = je.getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("children");

        RedditService.SearchResult result = new RedditService.SearchResult();

        for (JsonElement element : children) {
            String title = element.getAsJsonObject().get("data").getAsJsonObject().get("title").getAsString();
            result.add(title);
        }

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return result;

    }
}