package io.github.emrys919.movies.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.github.emrys919.movies.R;
import io.github.emrys919.movies.controllers.MovieItemController;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.views.holders.BaseViewHolder;
import io.github.emrys919.movies.views.holders.MovieViewHolder;

public class MovieAdapter extends BaseRecyclerAdapter<BaseViewHolder, MovieVO> {

    private MovieItemController movieItemController;

    public MovieAdapter(Context context, MovieItemController movieItemController) {
        super(context);
        this.movieItemController = movieItemController;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view, movieItemController);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

}
