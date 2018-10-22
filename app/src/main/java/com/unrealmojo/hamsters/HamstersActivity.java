package com.unrealmojo.hamsters;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.unrealmojo.hamsters.databinding.HamstersActivityBinding;
import com.unrealmojo.hamsters.ui.hamsters.HamstersFragment;

public class HamstersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HamstersActivityBinding UI = DataBindingUtil
                .setContentView(this, R.layout.hamsters_activity);

        setSupportActionBar(UI.toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HamstersFragment.newInstance())
                    .commitNow();
        }
    }
}
