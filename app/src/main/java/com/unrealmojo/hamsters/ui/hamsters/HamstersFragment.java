package com.unrealmojo.hamsters.ui.hamsters;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.unrealmojo.hamsters.HamstersActivityViewModel;
import com.unrealmojo.hamsters.R;
import com.unrealmojo.hamsters.databinding.HamstersFragmentBinding;
import com.unrealmojo.hamsters.helpers.customs.CharacterItemDecaration;
import com.unrealmojo.hamsters.helpers.Utilities;
import com.unrealmojo.hamsters.helpers.customs.SearchViewLayout;
import com.unrealmojo.hamsters.helpers.customs.SmartToolbar;
import com.unrealmojo.hamsters.models.Hamster;
import com.unrealmojo.hamsters.ui.base.BaseFragment;
import com.unrealmojo.hamsters.ui.hamsters.detail.HamsterDetailFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HamstersFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private HamstersFragmentBinding UI;
    private SearchViewLayout searchBar;
    private HamstersViewModel mViewModel;
    private HamstersListAdapter mAdapter;

    public static HamstersFragment newInstance(Bundle args) {
        HamstersFragment hamstersFragment = new HamstersFragment();
        hamstersFragment.setArguments(args);
        return hamstersFragment;
    }

    @Override
    public String pageTag() {
        return "HamstersFragment";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        UI = DataBindingUtil.inflate(
                inflater,
                R.layout.hamsters_fragment,
                container,
                false);

        UI.setIsNetReachable(true);
        return UI.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SmartToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.hideBackBtn();
        toolbar.showAllMenu();

        mViewModel = ViewModelProviders.of(this).get(HamstersViewModel.class);

        UI.hamstersRV.setHasFixedSize(true);
        UI.hamstersRV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        UI.hamstersRV.addItemDecoration(new CharacterItemDecaration(Utilities.dp(6)));

        mAdapter = new HamstersListAdapter(this, new ArrayList<>());
        UI.hamstersRV.setAdapter(mAdapter);

        UI.refreshLayout.setOnRefreshListener(this);

        if (Utilities.isNetAvailable(getContext())) UI.refreshLayout.setRefreshing(true);
        else UI.setIsNetReachable(false);

        mViewModel.repo.observe(this, hamstersResp -> {
            UI.refreshLayout.setRefreshing(false);

            if (!hamstersResp.isRequestOK()) {
                handleReqError(getString(R.string.fail_req_try_again_txt));
                return;
            }

            mAdapter.submitData(hamstersResp.getData());
        });

        setupSearchBar();
    }

    private void setupSearchBar() {
        searchBar = getActivity().findViewById(R.id.searchBar);

        searchBar.setCollapseListener(() -> {
            mAdapter.showOriginal();
        });

        searchBar.setCleanListener(() -> {
            mAdapter.showOriginal();
        });

        searchBar.setSearchListener(searchKeyword -> {
            mAdapter.getFilter().filter(searchKeyword);
        });

        searchBar.setSearchBoxListener(new SearchViewLayout.SearchBoxListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s.toString());
            }
        });
    }

    @Override
    public void onRefresh() {
        if (!Utilities.isNetAvailable(getContext())) {
            UI.setIsNetReachable(false);
            handleReqError(getString(R.string.offline_mode_txt));
            return;
        }
        UI.setIsNetReachable(true);
        mViewModel.refreshData();
    }

    private void handleReqError(String msg) {
        Snackbar
                .make(UI.hamstersConstLay,
                        msg,
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh_txt, v -> {
                    onRefresh();
                })
                .show();
    }

    void onItemClicked(Hamster hamster) {
        Bundle arg = new Bundle();
        arg.putParcelable("hamster", hamster);

        HamsterDetailFragment detailFragment = new HamsterDetailFragment();
        detailFragment.setArguments(arg);

        detailFragment.show(getChildFragmentManager(), "HamsterDetailFragment");
    }
}
