package com.orium.testapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.Toast;

import com.orium.testapplication.model.Item;
import com.orium.testapplication.network.TestWebApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String RETAIN_FRAGMENT_TAG = "fragment_data";
    
    @Inject TestWebApi mService;

    @Bind(android.R.id.list)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RetainedFragment<List<Item>> dataFragment;

    private Call<List<Item>> mSalonsCall;
    
    private List<Item> mItemItems = new ArrayList<>();
    private ItemsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        App.getComponent().inject(this);

        restoreRetainedData();

        initUI();
    }

    private void restoreRetainedData() {
        FragmentManager fm = getSupportFragmentManager();
        dataFragment = (RetainedFragment<List<Item>>) fm.findFragmentByTag(RETAIN_FRAGMENT_TAG);

        if (dataFragment == null) {
            dataFragment = new RetainedFragment<>();
            fm.beginTransaction().add(dataFragment, RETAIN_FRAGMENT_TAG).commit();
            dataFragment.setData(mItemItems);
        } else {
            mItemItems = dataFragment.getData();
        }
    }

    private void initUI() {
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 24,
                        getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE);
        SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestSalons();
            }
        };
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

        mAdapter = new ItemsAdapter(mItemItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mItemItems.size() == 0) {
            requestSalons();
        }
    }

    private void requestSalons() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSalonsCall = mService.getItems();
        mSalonsCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(final Call<List<Item>> call, final Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    List<Item> data = response.body();
                    if (data != null) {
                        updateList(data);
                    }
                } else {
                    showErrorToast(response.message());
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(final Call<List<Item>> call, final Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
                showErrorToast(t.getLocalizedMessage());
            }
        });
    }

    private void showErrorToast(final String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void updateList(final List<Item> items) {
        if (items != null) {
            mItemItems.clear();
            mItemItems.addAll(items);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSalonsCall != null) {
            mSalonsCall.cancel();
        }
    }
}
