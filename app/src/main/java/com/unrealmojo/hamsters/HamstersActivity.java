package com.unrealmojo.hamsters;

import android.os.Bundle;

import com.unrealmojo.hamsters.databinding.HamstersActivityBinding;
import com.unrealmojo.hamsters.helpers.customs.SearchViewLayout;
import com.unrealmojo.hamsters.ui.base.BaseFragment;
import com.unrealmojo.hamsters.ui.developer.DeveloperFragment;
import com.unrealmojo.hamsters.ui.hamsters.HamstersFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class HamstersActivity extends AppCompatActivity implements HamstersActivityViewModel.InteractActions {

    private HamstersActivityBinding UI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UI = DataBindingUtil
                .setContentView(this, R.layout.hamsters_activity);

        HamstersActivityViewModel viewModel = ViewModelProviders.of(this)
                .get(HamstersActivityViewModel.class);
        viewModel.interactActions = this;

        if (savedInstanceState == null) {
            openPage(1, false, null);
        }

        UI.toolbar.setTitle(R.string.app_name);

        UI.toolbar.setBackListener(view -> {
            onBackPressed();
        });

        UI.toolbar.setSearchListener(view -> {
            ((SearchViewLayout) UI.searchBar).expand(true);
        });

        UI.toolbar.setDevListener(view -> {
            openPage(2, true, null);
        });
    }

    @Override
    public void openPage(int pageNum, boolean needBackStack, Bundle args) {
        BaseFragment mCurrFrag;
        switch (pageNum) {
            case 1:
                mCurrFrag = HamstersFragment.newInstance(args);
                break;
            case 2:
                UI.toolbar.hideAllMenu();
                UI.toolbar.showBackBtn();
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
            ft.commitAllowingStateLoss();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
