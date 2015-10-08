package com.example.gramian.androidtest.androidtest.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;


public final class RedditService {
    public static final String API_URL = "http://www.reddit.com/";

    private Retrofit retrofit;
    private Reddit reddit;

    public static class SearchResult extends ArrayList<String> {}

    public interface Reddit {
        @GET("/r/all/search.json")
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

    public List<String> search(String query) throws IOException {
        // Create a call instance for looking up Retrofit contributors.
        Call<SearchResult> call = reddit.search(query);
        return call.execute().body();
    }
}
