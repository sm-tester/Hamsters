package com.unrealmojo.hamsters.helpers.customs;

import android.content.Context;
import android.util.AttributeSet;

import com.unrealmojo.hamsters.R;
import com.unrealmojo.hamsters.helpers.Utilities;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

public class SmartToolbar extends Toolbar {

    private AppCompatImageButton searchBtn;
    private AppCompatImageButton devBtn;
    private AppCompatImageButton backBtn;
    private AppCompatTextView titleTV;

    private OnClickListener mBackListener;
    private OnClickListener mSearchListener;
    private OnClickListener mDevListener;

    public SmartToolbar(Context context) {
        super(context);
    }

    public SmartToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        searchBtn = findViewById(R.id.searchBtn);
        devBtn = findViewById(R.id.devBtn);
        backBtn = findViewById(R.id.backBtn);
        titleTV = findViewById(R.id.titleTV);

        backBtn.setOnClickListener(view -> {
            hideBackBtn();
            if (mBackListener != null) mBackListener.onClick(view);
        });

        searchBtn.setOnClickListener(view -> {
            if (mSearchListener != null) mSearchListener.onClick(view);
        });

        devBtn.setOnClickListener(view -> {
            if (mDevListener != null) mDevListener.onClick(view);
        });

        super.onFinishInflate();
    }

    public void hideBackBtn() {
        backBtn.setVisibility(GONE);
        ((MarginLayoutParams) titleTV.getLayoutParams()).setMarginStart(Utilities.dp(16));
        ((MarginLayoutParams) titleTV.getLayoutParams()).setMargins(Utilities.dp(16), 0, 0, 0);
    }

    public void showBackBtn() {
        ((MarginLayoutParams) titleTV.getLayoutParams()).setMarginStart(0);
        ((MarginLayoutParams) titleTV.getLayoutParams()).setMargins(0, 0, 0, 0);
        backBtn.setVisibility(VISIBLE);
    }

    public void hideAllMenu() {
        searchBtn.setVisibility(INVISIBLE);
        devBtn.setVisibility(INVISIBLE);
    }

    public void showAllMenu() {
        searchBtn.setVisibility(VISIBLE);
        devBtn.setVisibility(VISIBLE);
    }

    public void setTitle(String title) {
        titleTV.setText(title);
    }

    public void setTitle(int titleResId) {
        titleTV.setText(getContext().getString(titleResId));
    }

    public void setBackListener(OnClickListener mBackListener) {
        this.mBackListener = mBackListener;
    }

    public void setSearchListener(OnClickListener mSearchListener) {
        this.mSearchListener = mSearchListener;
    }

    public void setDevListener(OnClickListener mDevListener) {
        this.mDevListener = mDevListener;
    }
}
