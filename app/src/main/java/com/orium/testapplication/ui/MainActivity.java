package com.orium.testapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.orium.testapplication.R;
import com.orium.testapplication.ui.items.ItemsActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by drobysh on 10/09/16.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.action)
    void onActionClick() {
        startActivity(new Intent(this, ItemsActivity.class));
    }
}
