package com.unrealmojo.hamsters.ui.hamsters.detail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.unrealmojo.hamsters.R;
import com.unrealmojo.hamsters.databinding.HamsterDetailFragmentBinding;
import com.unrealmojo.hamsters.helpers.customs.RoundedCornersTransformation;
import com.unrealmojo.hamsters.helpers.Utilities;
import com.unrealmojo.hamsters.models.Hamster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

public class HamsterDetailFragment extends BottomSheetDialogFragment {

    private HamsterDetailFragmentBinding UI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UI = DataBindingUtil.inflate(
                inflater,
                R.layout.hamster_detail_fragment,
                container,
                false);

        return UI.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Point display = Utilities.getDisplaySize(getContext());
        float avWidth = display.x - Utilities.dp(6);

        float delta = avWidth <= Utilities.dp(354) ? 1 : (avWidth / Utilities.dp(354));
        float imageHeight = delta * Utilities.dp(416);

        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams((int) avWidth, (int) imageHeight);
        lp.setMargins(0, 0, 0, 0);
        UI.hamsterIV.setLayoutParams(lp);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final FrameLayout bottomSheet = (FrameLayout) getView().getParent();

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        bottomSheet.setBackground(new ColorDrawable(Color.TRANSPARENT));

        bottomSheet.getLayoutParams().width = metrics.widthPixels;
        bottomSheet.getLayoutParams().height = metrics.heightPixels;
        bottomSheet.setPadding(Utilities.dp(3), 0, Utilities.dp(3), 0);
        bottomSheet.setPaddingRelative(Utilities.dp(3), 0, Utilities.dp(3), 0);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(metrics.heightPixels - Utilities.dp(50));

        if (getArguments() != null) {
            Hamster hamster = getArguments().getParcelable("hamster");
            UI.titleTV.setText(hamster.getTitle());
            UI.desctTV.setText(hamster.getDesc());

            float avWidth = Utilities.getDisplaySize(getContext()).x - Utilities.dp(6);
            float delta = avWidth <= Utilities.dp(354) ? 1 : (avWidth / Utilities.dp(354));
            float avHeight = (delta * Utilities.dp(416));

            Picasso.get()
                    .load(hamster.getImage())
                    .resize((int)avWidth, (int)avHeight)
                    .onlyScaleDown()
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(Utilities.dp(10), 0, RoundedCornersTransformation.CornerType.TOP))
                    .into(UI.hamsterIV);

            UI.shareBtn.setOnClickListener(view -> {
                StringBuilder sb = new StringBuilder();
                sb.append(hamster.getTitle());
                sb.append("\n");
                sb.append(hamster.getImage());

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_txt)));
            });
        }

        UI.closeBtn.setOnClickListener(view -> {
            dismiss();
        });
    }
}
