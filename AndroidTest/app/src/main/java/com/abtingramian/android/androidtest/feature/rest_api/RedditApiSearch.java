package com.abtingramian.android.androidtest.feature.rest_api;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.gramian.androidtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedditApiSearch extends LinearLayout {

    public final static String SEARCH_QUERY = "com.example.gramian.androidtest.MESSAGE";

    public RedditApiSearch(Context context) {
        super(context);
    }

    public RedditApiSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @BindView(R.id.buttonSearch)
    Button buttonSearch;
    @BindView(R.id.search_text)
    EditText searchText;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonSearch)
    void search() {
        Intent intent = new Intent(getContext(), DisplaySearchResultsActivity.class);
        String searchQuery = searchText.getText().toString();
        intent.putExtra(SEARCH_QUERY, searchQuery);
        getContext().startActivity(intent);
    }

}
