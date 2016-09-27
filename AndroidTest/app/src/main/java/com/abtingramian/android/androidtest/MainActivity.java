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
import android.widget.FrameLayout;

import com.abtingramian.android.androidtest.feature.collapsing_toolbar.CollapsingToolbarActivity;
import com.example.gramian.androidtest.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    Map<String, Integer> featureViewMap;
    Map<String, Class> featureActivityMap;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nvView)
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.main_content)
    FrameLayout mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // set up toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // feature map
        initializeFeatureMap();
        // set up navigation drawer
        setupDrawerContent(navigationView);
        actionBarDrawerToggle = setupDrawerToggle();
        // add menu items dynamicall
        Menu menu = navigationView.getMenu();
        for (String key : featureViewMap.keySet()) {
            menu.add(key);
        }
        for (String key : featureActivityMap.keySet()) {
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
        if (featureViewMap.containsKey(menuItem.getTitle())) {
            getLayoutInflater().inflate(featureViewMap.get(menuItem.getTitle()), mainContent);
        } else if (featureActivityMap.containsKey(menuItem.getTitle())) {
            startActivity(new Intent(this, featureActivityMap.get(menuItem.getTitle())));
        }
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
        // custom view features
        featureViewMap = new HashMap<>();
        featureViewMap.put(getString(R.string.feature_rest_api), R.layout.view_api_search);
        featureViewMap.put(getString(R.string.feature_alphabet_indexer), R.layout.view_alphabet_indexer);
        featureViewMap.put(getString(R.string.feature_recyclerview_animations), R.layout.view_recyclerview_animations);
        // activity features
        featureActivityMap = new HashMap<>();
        featureActivityMap.put(getString(R.string.feature_collapsing_toolbar), CollapsingToolbarActivity.class);
    }

}
