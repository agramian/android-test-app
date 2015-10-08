package com.example.gramian.androidtest.androidtest.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gramian.androidtest.R;
import com.example.gramian.androidtest.androidtest.service.RedditService;
import com.example.gramian.androidtest.androidtest.service.github.GitHubService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplaySearchResultsActivity extends AppCompatActivity {

    private static final String TAG = DisplaySearchResultsActivity.class.getSimpleName();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_results);

        // Get the query from the intent
        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra(MainActivity.SEARCH_QUERY);

        RedditService redditService = new RedditService();
        List<String> resultList = new ArrayList<String>();
        try {
             resultList = redditService.search(searchQuery);
        }
        catch (Exception e) {
            Log.d(TAG, "Caught Exception: " + e.getMessage());
        }
        listView = (ListView) findViewById(R.id.search_results);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                resultList);

        listView.setAdapter(arrayAdapter);
        new AlertDialog.Builder(this)
                .setTitle("Results")
                .setMessage(String.format("Search returned %s result(s).", resultList.size()))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

}
