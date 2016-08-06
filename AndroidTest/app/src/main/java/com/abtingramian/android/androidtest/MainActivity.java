package com.abtingramian.android.androidtest;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.abtingramian.android.androidtest.feature.alphabet_indexer.AlphabetIndexer;
import com.abtingramian.android.androidtest.feature.collapsing_toolbar.CollapsingToolbarActivity;
import com.abtingramian.android.androidtest.feature.rest_api.RedditApiSearchActivity;
import com.example.gramian.androidtest.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<String, String> featureMap;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private FrameLayout mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set up toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // content layout reference
        mainContent = (FrameLayout) findViewById(R.id.main_content);
        // feature map
        initializeFeatureMap();
        // set up navigation drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(navigationView);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = setupDrawerToggle();
        // add menu items dynamicall
        Menu menu = navigationView.getMenu();
        for (String key : featureMap.keySet()) {
            menu.add(key);
        }
        // Tie DrawerLayout events to the ActionBarToggle
        drawer.addDrawerListener(actionBarDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // replace content view
        mainContent.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.view_alphabet_indexer, mainContent);
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawer.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void initializeFeatureMap() {
        featureMap = new HashMap<>();
        featureMap.put(getString(R.string.feature_rest_api), "");
        featureMap.put(getString(R.string.feature_collapsing_toolbar), "");
        featureMap.put(getString(R.string.feature_alphabet_indexer), "");
    }

    public void startActivityReddictApiSearch(View view) {
        startActivity(new Intent(this, RedditApiSearchActivity.class));
    }

    public void startActivityCollapsingToolbar(View view) {
        startActivity(new Intent(this, CollapsingToolbarActivity.class));
    }

    public void startActivityAlphabetIndexer(View view) {
        startActivity(new Intent(this, AlphabetIndexer.class));
    }

}
