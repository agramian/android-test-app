package com.abtingramian.android.androidtest.feature.rest_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public final class RedditService {
    public static final String API_URL = "http://www.reddit.com/";

    private Retrofit retrofit;
    private Reddit reddit;

    public static class SearchResult extends ArrayList<String> {}

    public interface Reddit {
        @GET("/r/all/search.json?limit=100")
        Call<SearchResult> search(@Query("q") String query);
    }

    public RedditService() {
        Gson gson =
                new GsonBuilder()
                        .registerTypeAdapter(SearchResult.class, new RedditDeserializer())
                        .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        reddit = retrofit.create(Reddit.class);
    }

    public void search(String query, Callback<SearchResult> callback) {
        // Create a call instance for looking up Retrofit contributors.
        reddit.search(query).enqueue(callback);
    }
}
