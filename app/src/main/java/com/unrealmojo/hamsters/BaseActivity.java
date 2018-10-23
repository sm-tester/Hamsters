package com.unrealmojo.hamsters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

public class BaseActivity extends AppCompatActivity {

    public void enableWhiteBackButton() {
        showBackBtn();
        whiteBackUpBtn();
    }

    public void showBackBtn() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0.2f);
    }

    public void whiteBackUpBtn() {
        Drawable upArrow = AppCompatResources.getDrawable(this, R.drawable.ic_arrow_back_white_24dp);
//        upArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    public void disableBackBtn() {
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
