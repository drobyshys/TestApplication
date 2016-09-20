package com.orium.testapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orium.testapplication.R;
import com.orium.testapplication.model.Item;
import com.orium.testapplication.network.TestWebApi;
import com.orium.testapplication.ui.items.ItemsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by drobysh on 10/09/16.
 */
public class MainActivity extends AppCompatActivity {

    private static final int MENU_GROUP = 0;

    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.tab_layout) TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupNavigationView();
        prepareNavView();

        ItemViewPagerAdapter adapter = new ItemViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
                        switch (menuItem.getItemId()) {
                            case 1:
                                startActivity(new Intent(getApplicationContext(), ItemsActivity.class));
                                break;
                        }
                        menuItem.setChecked(true);
                        setTitle(menuItem.getTitle());
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

        Menu menu = navigationView.getMenu();
        menu.clear();
        menu.add(MENU_GROUP, 0, 0, "show list");
        menu.getItem(0).setChecked(true);
        menu.add(MENU_GROUP, 1, 1, "item 2");
        menu.add(MENU_GROUP, 2, 2, "item 3");
        menu.setGroupCheckable(MENU_GROUP, true, true);
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

    public static final class ItemViewPagerAdapter extends FragmentPagerAdapter {

        private final Context context;

        public ItemViewPagerAdapter(final FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(final int position) {
            return TabItemFragment.newInstance(context, position);
        }

        @Override
        public void destroyItem(final ViewGroup container, final int position, final Object object) {
            FragmentManager fm = ((Fragment) object).getFragmentManager();
            fm.beginTransaction().remove((Fragment) object).commit();
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            return "Item " + position;
        }
    }

    public static class TabItemFragment extends Fragment {

        public static final String ARG_INDEX = "index";

        public static Fragment newInstance(Context ctx, int index) {
            Bundle args = new Bundle();
            args.putInt(ARG_INDEX, index);
            return Fragment.instantiate(ctx, TabItemFragment.class.getName(), args);
        }

        @Nullable
        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
            TextView view = (TextView) inflater.inflate(R.layout.fragment_tab, container, false);
            view.setText("Item: " + getArguments().getInt(ARG_INDEX));
            return view;
        }
    }
}
