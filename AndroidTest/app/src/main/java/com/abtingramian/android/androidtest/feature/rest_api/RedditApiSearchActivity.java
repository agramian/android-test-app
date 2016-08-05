package com.abtingramian.android.androidtest.feature.rest_api;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.gramian.androidtest.R;

public class RedditApiSearchActivity extends AppCompatActivity {

    public final static String SEARCH_QUERY = "com.example.gramian.androidtest.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_search);
    }

    public void search(View view) {
        Intent intent = new Intent(this, DisplaySearchResultsActivity.class);
        EditText searchText = (EditText) findViewById(R.id.search_text);
        String searchQuery = searchText.getText().toString();
        intent.putExtra(SEARCH_QUERY, searchQuery);
        startActivity(intent);
    }

}
