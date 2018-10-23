package com.unrealmojo.hamsters.ui.hamsters;

import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.squareup.picasso.Picasso;
import com.unrealmojo.hamsters.R;
import com.unrealmojo.hamsters.databinding.HamstersListItemBinding;
import com.unrealmojo.hamsters.helpers.Utilities;
import com.unrealmojo.hamsters.models.Hamster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class HamstersListAdapter extends RecyclerView.Adapter<HamstersListAdapter.ViewHolder> implements Filterable {
    private List<Hamster> mData;
    private List<Hamster> mOriginalData;
    private HamstersFragment mPage;
    private float imageWidth;
    private float imageHeight;
    private boolean isDataFiltered = false;

    public HamstersListAdapter(HamstersFragment fragment, List<Hamster> mData) {
        this.mPage = fragment;
        this.mData = mData;
        this.mOriginalData = mData;

        this.imageWidth = (Utilities.getDisplaySize(mPage.getContext()).x - Utilities.dp(46)) / 2;

        float delta = imageWidth <= Utilities.dp(160) ? 1 : (imageWidth / Utilities.dp(160));
        this.imageHeight = delta * Utilities.dp(188);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HamstersListItemBinding binding = DataBindingUtil.inflate(
                mPage.getLayoutInflater(),
                R.layout.hamsters_list_item,
                parent,
                false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void submitData(List<Hamster> data) {
        Collections.sort(data);
        mData = data;
        mOriginalData = data;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (!TextUtils.isEmpty(constraint)) {
                    String key = constraint.toString().toLowerCase();
                    ArrayList<Hamster> data = new ArrayList<>();
                    for (Hamster hamster : mData) {
                        if (hamster.getTitle().contains(key)) {
                            data.add(hamster);
                        }
                    }

                    if (data.size() > 0) {
                        filterResults.values = data;
                        filterResults.count = data.size();
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count > 0) {
                    mData = (List<Hamster>) results.values;
                    Collections.sort(mData);
                    isDataFiltered = true;
                    notifyDataSetChanged();
                    return;
                }

                showOriginal();
            }
        };
    }

    public void showOriginal() {
        if (isDataFiltered) {
            mData = mOriginalData;
            notifyDataSetChanged();
        }
        isDataFiltered = false;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private HamstersListItemBinding binding;

        ViewHolder(@NonNull HamstersListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(view -> {
                mPage.onItemClicked(mData.get(getAdapterPosition()));
            });
        }

        void bindData(Hamster data) {
            Picasso.get()
                    .load(data.getImage())
                    .placeholder(R.drawable.ic_image_not_found)
                    .resize((int) imageWidth, (int) imageHeight)
                    .centerCrop()
                    .into(binding.itemIV);

            binding.setHamster(data);
        }
    }

}
