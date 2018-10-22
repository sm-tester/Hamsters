package com.unrealmojo.hamsters.ui.hamsters;

import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.unrealmojo.hamsters.R;
import com.unrealmojo.hamsters.databinding.HamstersListItemBinding;
import com.unrealmojo.hamsters.helpers.Utilities;
import com.unrealmojo.hamsters.models.Hamster;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class HamstersListAdapter extends RecyclerView.Adapter<HamstersListAdapter.ViewHolder> {
    private List<Hamster> mData;
    private HamstersFragment mPage;
    private float imageWidth;
    private float imageHeight;

    public HamstersListAdapter(HamstersFragment fragment, List<Hamster> mData) {
        this.mPage = fragment;
        this.mData = mData;

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
        mData = data;
        notifyDataSetChanged();
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
