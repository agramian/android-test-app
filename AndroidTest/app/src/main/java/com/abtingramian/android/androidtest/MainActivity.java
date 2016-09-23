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
    private static final String KEY_SELECTED_MENU_ITEM = "selected_menu_item";
    Map<String, Integer> featureViewMap;
    Map<String, Class> featureActivityMap;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nvView)
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.fragmentContainer)
    FrameLayout mainContent;
    MenuItem previousMenuItem;

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
        // set selected item on orientation change
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_SELECTED_MENU_ITEM)) {
            setSelectedDrawerItem(savedInstanceState.getString(KEY_SELECTED_MENU_ITEM));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String selectedMenuItem = "";
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            if (navigationView.getMenu().getItem(i).isChecked()) {
                selectedMenuItem = navigationView.getMenu().getItem(i).getTitle().toString();
            }
        }
        outState.putString(KEY_SELECTED_MENU_ITEM, selectedMenuItem);
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

    public void setSelectedDrawerItem(String title) {
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
            if (navigationView.getMenu().getItem(i).getTitle().toString().equals(title)) {
                navigationView.getMenu().getItem(i).setChecked(true);
            }
        }
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // replace content view
        if (featureViewMap.containsKey(menuItem.getTitle())) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, MainFragment.newInstance(featureViewMap.get(menuItem.getTitle())))
                    .commit();
        } else if (featureActivityMap.containsKey(menuItem.getTitle())) {
            startActivity(new Intent(this, featureActivityMap.get(menuItem.getTitle())));
        }
        // Highlight the selected item has been done by NavigationView
        setSelectedDrawerItem(menuItem.getTitle().toString());
        previousMenuItem = menuItem;
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
        featureViewMap.put(getString(R.string.feature_calendar), R.layout.view_calendar);
        // activity features
        featureActivityMap = new HashMap<>();
        featureActivityMap.put(getString(R.string.feature_collapsing_toolbar), CollapsingToolbarActivity.class);
    }

}
