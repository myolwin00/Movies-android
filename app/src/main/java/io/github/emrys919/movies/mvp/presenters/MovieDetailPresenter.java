package io.github.emrys919.movies.mvp.presenters;

import android.content.Context;
import android.net.Uri;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import io.github.emrys919.movies.data.models.MovieModel;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.data.vos.TrailerVO;
import io.github.emrys919.movies.events.DataEvent;
import io.github.emrys919.movies.mvp.views.MovieDetailView;
import io.github.emrys919.movies.utils.Constants;
import io.github.emrys919.movies.utils.NetworkUtils;

import static android.R.transition.move;

/**
 * Created by myolwin00 on 8/2/17.
 */

public class MovieDetailPresenter extends BasePresenter {

    private Context mContext;
    private MovieDetailView mMovieDetailView;
    private long mMovieId;
    private int mMovieType;

    public MovieDetailPresenter(Context context, long mMovieId, MovieDetailView mMovieDetailView) {
        this.mContext = context;
        this.mMovieDetailView = mMovieDetailView;
        this.mMovieId = mMovieId;
    }

    @Subscribe
    public void onEvent(DataEvent.MovieDetailLoadedEvent event) {
        if (!event.isSuccessful()) {
            mMovieDetailView.onDetailLoadFailed("Detail loading failed.");
        }
    }

    public void onLoadFinished(MovieVO movie) {
        if (movie != null) {
            if (movie.getMovieType() != Constants.CATEGORY_OTHER_MOVIES) {
                if (movie.getRuntime() != 0) {
                    movie.setTrailerList(TrailerVO.loadTrailerListByMovieId(mContext, mMovieId));
                    mMovieDetailView.onDetailLoaded(movie);
                } else {
                    mMovieType = movie.getMovieType();
                    loadDetailFromNetwork();
                }
            } else {
                movie.setTrailerList(TrailerVO.loadTrailerListByMovieId(mContext, mMovieId));
                mMovieDetailView.onDetailLoaded(movie);
            }
        } else {
            mMovieType = Constants.CATEGORY_OTHER_MOVIES;
            loadDetailFromNetwork();
        }
    }

    public void loadDetailFromNetwork() {
        if (NetworkUtils.isOnline(mContext)) {
            mMovieDetailView.onDetailLoading();
            MovieModel.getInstance().loadMovieDetail(mContext, mMovieId, mMovieType);
            MovieModel.getInstance().loadMovieTrailer(mContext, mMovieId);
        } else {
            mMovieDetailView.onDetailLoadFailed("No internet connection.");
        }
    }
}
