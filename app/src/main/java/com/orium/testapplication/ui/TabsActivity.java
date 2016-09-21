package com.orium.testapplication.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orium.testapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabsActivity extends AppCompatActivity {

    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tab_layout) TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ItemViewPagerAdapter adapter = new ItemViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    static final class ItemViewPagerAdapter extends FragmentPagerAdapter {

        private final Context context;

        ItemViewPagerAdapter(final FragmentManager fm, Context context) {
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
            View view = inflater.inflate(R.layout.fragment_tab, container, false);
            ((TextView)view.findViewById(android.R.id.text1)).setText("Item: " + getArguments().getInt(ARG_INDEX));
            return view;
        }
    }
}
