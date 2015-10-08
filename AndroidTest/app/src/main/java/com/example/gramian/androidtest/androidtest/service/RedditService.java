package com.example.gramian.androidtest.androidtest.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Retrofit;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.http.GET;
import retrofit.http.Query;


public final class RedditService {
    public static final String API_URL = "http://www.reddit.com/";

    private Retrofit retrofit;
    private Reddit reddit;

    class SearchResult {
        public String title;
    }

    public interface Reddit {
        @GET("/r/all/search.json")
        Call<List<SearchResult>> search(@Query("q") String query);
    }

    public RedditService() {
        Gson gson =
                new GsonBuilder()
                        .registerTypeAdapter(Content.class, new RedditDeserializer())
                        .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        reddit = retrofit.create(Reddit.class);
    }

    public List<String> search(String query) throws IOException {
        // Create a call instance for looking up Retrofit contributors.
        Call<List<SearchResult>> call = reddit.search(query);
        List<SearchResult> results = call.execute().body();
        List<String> resultList = new ArrayList<String>();
        for (SearchResult result : results) {
            resultList.add(result.title);
        }
        return resultList;
    }
}
