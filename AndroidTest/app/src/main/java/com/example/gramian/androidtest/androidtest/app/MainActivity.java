package com.example.gramian.androidtest.androidtest.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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
}
