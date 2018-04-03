package io.github.emrys919.movies.mvp.presenters;

import android.content.Context;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.github.emrys919.movies.data.models.MovieModel;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.events.DataEvent;
import io.github.emrys919.movies.mvp.views.MovieListView;
import io.github.emrys919.movies.utils.NetworkUtils;

import static io.github.emrys919.movies.utils.Constants.CATEGORY_NOW_PLAYING_MOVIES;
import static io.github.emrys919.movies.utils.Constants.CATEGORY_POPULAR_MOVIES;
import static io.github.emrys919.movies.utils.Constants.CATEGORY_UPCOMING_MOVIES;

/**
 * Created by myolwin00 on 7/9/17.
 */

public class MovieListPresenter extends BasePresenter {

    private MovieListView movieListView;
    private int movieCategory;
    private Context context;

    public MovieListPresenter(Context context, MovieListView movieListView, int movieCategory) {
        this.movieListView = movieListView;
        this.movieCategory = movieCategory;
        this.context = context;
    }

    public void onDisplayMovieList(List<MovieVO> movieList) {
        movieListView.onDisplayMovieList(movieList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadFailedEvent(DataEvent.MovieDetailLoadedEvent event) {
        if (!event.isSuccessful()) {
            movieListView.onMovieLoadFailed(event.getMessage());
        }
    }

    public void loadMovieListFromNetwork() {
        if (NetworkUtils.isOnline(context)) {
            movieListView.onLoadingMovieList();
            switch (movieCategory) {
                case CATEGORY_NOW_PLAYING_MOVIES:
                    MovieModel.getInstance().loadNowPlayingMovies(context);
                    break;
                case CATEGORY_UPCOMING_MOVIES:
                    MovieModel.getInstance().loadUpcomingMovies(context);
                    break;
                case CATEGORY_POPULAR_MOVIES:
                    MovieModel.getInstance().loadPopularMovies(context);
            }
        } else {
            movieListView.onMovieLoadFailed("No internet connection!");
        }
    }
}
