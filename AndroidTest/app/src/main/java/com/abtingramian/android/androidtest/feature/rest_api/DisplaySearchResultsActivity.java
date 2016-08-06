package com.abtingramian.android.androidtest.feature.rest_api;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gramian.androidtest.R;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplaySearchResultsActivity extends AppCompatActivity {

    private static final String TAG = DisplaySearchResultsActivity.class.getSimpleName();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_results);

        // Get the query from the intent
        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra(RedditApiSearch.SEARCH_QUERY);

        new RedditService().search(searchQuery, new Callback<RedditService.SearchResult>() {
            @Override
            public void onResponse(Call<RedditService.SearchResult> call, Response<RedditService.SearchResult> response) {
                if (response.isSuccessful()) {
                    onResults(response.body());
                } else {
                    onResults(null);
                }
            }

            @Override
            public void onFailure(Call<RedditService.SearchResult> call, Throwable t) {
                t.printStackTrace();
                onResults(null);
            }
        });
    }

    private void onResults(List<String> results) {
        if (results == null) {
            results = Collections.emptyList();
        }

        findViewById(R.id.loading).animate().alpha(0).start();

        listView = (ListView) findViewById(R.id.search_results);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                results);

        listView.setAdapter(arrayAdapter);
        new AlertDialog.Builder(this)
                .setTitle("Results")
                .setMessage(String.format("Search returned %s result(s).", results.size()))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }
}
