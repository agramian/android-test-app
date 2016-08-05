package com.abtingramian.android.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.abtingramian.android.androidtest.feature.alphabet_indexer.AlphabetIndexerActivity;
import com.abtingramian.android.androidtest.feature.collapsing_toolbar.CollapsingToolbarActivity;
import com.abtingramian.android.androidtest.feature.rest_api.RedditApiSearchActivity;
import com.example.gramian.androidtest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startActivityReddictApiSearch(View view) {
        startActivity(new Intent(this, RedditApiSearchActivity.class));
    }

    public void startActivityCollapsingToolbar(View view) {
        startActivity(new Intent(this, CollapsingToolbarActivity.class));
    }

    public void startActivityAlphabetIndexer(View view) {
        startActivity(new Intent(this, AlphabetIndexerActivity.class));
    }

}
