package com.orium.testapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.orium.testapplication.R;
import com.orium.testapplication.ui.items.ItemsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by drobysh on 10/09/16.
 */
public class MainActivity extends AppCompatActivity {

    private static final int MENU_GROUP = 0;

    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupNavigationView();
        prepareNavView();
    }

    private void setupNavigationView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void prepareNavView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        return onMenuItemSelected(menuItem);
                    }
                });

        Menu menu = navigationView.getMenu();
        menu.clear();
        menu.add(MENU_GROUP, 0, 0, "list screen");
        menu.getItem(0).setChecked(true);
        menu.add(MENU_GROUP, 1, 1, "tabs screen");
        menu.add(MENU_GROUP, 2, 2, "item 3");
        menu.setGroupCheckable(MENU_GROUP, true, true);
    }

    boolean onMenuItemSelected(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 0:
                startActivity(new Intent(getApplicationContext(), ItemsActivity.class));
                break;
            case 1:
                startActivity(new Intent(getApplicationContext(), TabsActivity.class));
                break;
        }
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                //NO-OP
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
