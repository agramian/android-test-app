package com.example.gramian.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplaySearchResultsActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_results);
        /*
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
        */
        listView = (ListView) findViewById(R.id.search_results);
        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> resultList = new ArrayList<String>();
        resultList.add("foo");
        resultList.add("bar");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                resultList );

        listView.setAdapter(arrayAdapter);
    }

}
