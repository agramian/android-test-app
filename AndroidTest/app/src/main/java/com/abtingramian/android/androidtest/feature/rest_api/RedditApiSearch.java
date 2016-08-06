package com.abtingramian.android.androidtest.feature.rest_api;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.gramian.androidtest.R;

public class RedditApiSearch extends LinearLayout {

    public final static String SEARCH_QUERY = "com.example.gramian.androidtest.MESSAGE";

    public RedditApiSearch(Context context) {
        super(context);
    }

    public RedditApiSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.buttonSearch).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

    public void search() {
        Intent intent = new Intent(getContext(), DisplaySearchResultsActivity.class);
        EditText searchText = (EditText) findViewById(R.id.search_text);
        String searchQuery = searchText.getText().toString();
        intent.putExtra(SEARCH_QUERY, searchQuery);
        getContext().startActivity(intent);
    }

}
