package com.example.gramian.androidtest.androidtest.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.gramian.androidtest.R;
import com.example.gramian.androidtest.androidtest.app.DisplaySearchResultsActivity;

public class MainActivity extends AppCompatActivity {

    public final static String SEARCH_QUERY = "com.example.gramian.androidtest.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void search(View view) {
        Intent intent = new Intent(this, DisplaySearchResultsActivity.class);
        EditText searchText = (EditText) findViewById(R.id.search_text);
        String searchQuery = searchText.getText().toString();
        intent.putExtra(SEARCH_QUERY, searchQuery);
        startActivity(intent);
    }
}
