package io.github.emrys919.movies.views.holders;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import io.github.emrys919.movies.R;
import io.github.emrys919.movies.controllers.MovieItemController;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.utils.Constants;

/**
 * Created by myolwin00 on 6/14/17.
 */

public class MovieViewHolder extends BaseViewHolder<MovieVO> {

    private MovieItemController mController;
    private MovieVO mMovie;

    @BindView(R.id.iv_movie_poster) ImageView ivMovieCover;

    public MovieViewHolder(View itemView, MovieItemController controller) {
        super(itemView);
        mController = controller;
    }

    @Override
    public void onClick(View v) {
        mController.navigateToDetail(mMovie, ivMovieCover);
    }

    @Override
    public void bind(MovieVO data) {
        mMovie = data;

        Glide.with(ivMovieCover.getContext())
                .load(Constants.BASE_LOW_IMG_URL + data.getPosterPath())
                .centerCrop()
                .thumbnail(0.1f)
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.movie_placeholder)
                .into(ivMovieCover);
    }
}
