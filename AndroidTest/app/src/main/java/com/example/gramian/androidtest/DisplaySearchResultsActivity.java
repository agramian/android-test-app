package com.example.gramian.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DisplaySearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_display_search_results);

        // Get the query from the intent
        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra(MainActivity.SEARCH_QUERY);


        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(searchQuery);

        // Set the text view as the activity layout
        setContentView(textView);
    }

}
