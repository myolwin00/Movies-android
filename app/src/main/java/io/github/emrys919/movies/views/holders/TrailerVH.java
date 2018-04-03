package io.github.emrys919.movies.views.holders;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import io.github.emrys919.movies.R;
import io.github.emrys919.movies.controllers.TrailerItemController;
import io.github.emrys919.movies.data.vos.TrailerVO;
import io.github.emrys919.movies.utils.Constants;

/**
 * Created by myolwin00 on 8/7/17.
 */

public class TrailerVH extends BaseViewHolder<TrailerVO> {

    @BindView(R.id.iv_trailer_preview) ImageView ivTrailerPreview;

    private TrailerItemController mController;
    private TrailerVO mTrailer;

    public TrailerVH(View itemView, TrailerItemController controller) {
        super(itemView);
        mController = controller;
    }

    @Override
    public void bind(TrailerVO data) {
        mTrailer = data;
        Glide.with(ivTrailerPreview.getContext())
                .load(data.getTrailerPath())
                .thumbnail(0.1f)
                .placeholder(R.drawable.movie_placeholder_land)
                .error(R.drawable.movie_placeholder_land)
                .into(ivTrailerPreview);
    }

    @Override
    public void onClick(View v) {
        mController.onTrailerItemClicked(mTrailer);
    }
}
