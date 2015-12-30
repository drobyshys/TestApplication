package com.orium.testapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.orium.testapplication.model.SalonsResponse;
import com.orium.testapplication.network.TestWebApi;

import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Call<SalonsResponse> mSalonsCall;
    private TestWebApi mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://staging.salony.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        mService = retrofit.create(TestWebApi.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSalonsCall = mService.getSalons();
        mSalonsCall.enqueue(new Callback<SalonsResponse>() {
            @Override
            public void onResponse(final Response<SalonsResponse> response, final Retrofit retrofit) {
                SalonsResponse data = response.body();
                if (data != null ) {
                    Log.i("MainActivity", String.valueOf(data.getSalons()));
                }
            }

            @Override
            public void onFailure(final Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSalonsCall.cancel();
    }
}
