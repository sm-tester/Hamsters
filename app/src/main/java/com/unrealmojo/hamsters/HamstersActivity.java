package com.unrealmojo.hamsters;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.unrealmojo.hamsters.databinding.HamstersActivityBinding;
import com.unrealmojo.hamsters.ui.base.BaseFragment;
import com.unrealmojo.hamsters.ui.developer.DeveloperFragment;
import com.unrealmojo.hamsters.ui.hamsters.HamstersFragment;

public class HamstersActivity extends AppCompatActivity implements HamstersActivityViewModel.InteractActions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HamstersActivityBinding UI = DataBindingUtil
                .setContentView(this, R.layout.hamsters_activity);

        setSupportActionBar(UI.toolbar);

        HamstersActivityViewModel viewModel = ViewModelProviders.of(this)
                .get(HamstersActivityViewModel.class);
        viewModel.interactActions = this;

        if (savedInstanceState == null) {
            openPage(1, false, null);
        }
    }

    @Override
    public void openPage(int pageNum, boolean needBackStack, Bundle args) {
        BaseFragment mCurrFrag;
        switch (pageNum) {
            case 1:
                mCurrFrag = HamstersFragment.newInstance(args);
                break;
            case 2:
                mCurrFrag = DeveloperFragment.newInstance(args);
                break;
            default:
                return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (needBackStack) ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        ft.replace(R.id.container, mCurrFrag, mCurrFrag.pageTag());

        if (needBackStack) ft.addToBackStack(mCurrFrag.pageTag());

        try {
            ft.commitNow();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
