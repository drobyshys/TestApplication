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

import com.orium.testapplication.model.Salon;
import com.orium.testapplication.model.SalonsResponse;
import com.orium.testapplication.network.TestWebApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String RETAIN_FRAGMENT_TAG = "fragment_data";
    
    @Inject TestWebApi mService;

    @Bind(android.R.id.list)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RetainedFragment<List<Salon>> dataFragment;

    private Call<SalonsResponse> mSalonsCall;
    
    private List<Salon> mSalonItems = new ArrayList<>();
    private SalonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        restoreRetainedData();

        App.getObjectGraph().inject(this);

        initUI();
    }



    private void restoreRetainedData() {
        FragmentManager fm = getSupportFragmentManager();
        dataFragment = (RetainedFragment<List<Salon>>) fm.findFragmentByTag(RETAIN_FRAGMENT_TAG);

        if (dataFragment == null) {
            dataFragment = new RetainedFragment<>();
            fm.beginTransaction().add(dataFragment, RETAIN_FRAGMENT_TAG).commit();
            dataFragment.setData(mSalonItems);
        } else {
            mSalonItems = dataFragment.getData();
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

        mAdapter = new SalonAdapter(mSalonItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSalonItems.size() == 0) {
            requestSalons();
        }
    }

    private void requestSalons() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSalonsCall = mService.getSalons();
        mSalonsCall.enqueue(new Callback<SalonsResponse>() {
            @Override
            public void onResponse(final Response<SalonsResponse> response, final Retrofit retrofit) {
                SalonsResponse data = response.body();
                if (data != null) {
                    updateList(data.getSalons());
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(final Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateList(final List<Salon> salons) {
        if (salons != null) {
            mSalonItems.clear();
            mSalonItems.addAll(salons);
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
