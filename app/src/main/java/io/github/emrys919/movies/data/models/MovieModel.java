package io.github.emrys919.movies.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Movie;
import android.net.Uri;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.github.emrys919.movies.MoviesApp;
import io.github.emrys919.movies.data.persistence.MovieContract;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.data.vos.TrailerVO;
import io.github.emrys919.movies.events.DataEvent;
import io.github.emrys919.movies.utils.Constants;

import static java.security.AccessController.getContext;

/**
 * Created by myolwin00 on 6/15/17.
 */
public class MovieModel extends BaseModel {

    private static final String TAG = MovieModel.class.getSimpleName();

    private static MovieModel objInstance;

    private MovieModel() {
        super();
    }

    public static MovieModel getInstance() {
        if (objInstance == null) {
            objInstance = new MovieModel();
        }
        return objInstance;
    }

    public void loadMovieDetail(Context context, long movieId, int movieType) {
        dataAgent.loadMovieDetail(context, movieId, movieType);
    }
    public void loadNowPlayingMovies(Context context) {
        dataAgent.loadNowPlayingMovies(context);
    }
    public void loadUpcomingMovies(Context context) {
        dataAgent.loadUpcomingMovies(context);
    }
    public void loadPopularMovies(Context context) {
        dataAgent.loadPopularMovies(context);
    }
    public void loadMovieTrailer(Context context, long movieId) {
        dataAgent.loadMovieTrailers(context, movieId);
    }
    public void searchMovies(String query) {
        dataAgent.onSearchMovies(query);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onDetailLoadedEvent(DataEvent.MovieDetailLoadedEvent event) {
        if (event.isSuccessful()) {
            int rowUpdated = event.getContext().getContentResolver().update(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieVO.parseToContentValues(event.getMovie()),
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " =? AND "
                            + MovieContract.MovieEntry.COLUMN_MOVIE_TYPE + " =? ",
                    new String[]{String.valueOf(event.getMovie().getId()), String.valueOf(event.getMovie().getMovieType())}
            );

            if (rowUpdated > 0) {
                Log.d(TAG, "updated: "+ rowUpdated);
            } else {
                if (event.getMovie().getMovieType() == 0) {
                    event.getContext().getContentResolver().insert(
                            MovieContract.MovieEntry.CONTENT_URI,
                            MovieVO.parseToContentValues(event.getMovie()));
                } else {
                    Log.d(TAG, "update failed: " + rowUpdated);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLoadedEvent(DataEvent.NowPlayingMovieLoadedEvent event) {
        if (event.isSuccessful()) {
            event.getContext().getContentResolver().delete(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_TYPE + " =? ",
                    new String[]{String.valueOf(Constants.CATEGORY_NOW_PLAYING_MOVIES)});

            int rowInserted = event.getContext().getContentResolver().bulkInsert(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieVO.convertToContentValues(event.getResponse().getMovieList(), Constants.CATEGORY_NOW_PLAYING_MOVIES));
            Log.d(TAG, "now playing>>inserted>>" + rowInserted);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLoadedEvent(DataEvent.UpcomingMovieLoadedEvent event) {

        if (event.isSuccessful()) {
            event.getContext().getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_TYPE + " =? ",
                    new String[]{String.valueOf(Constants.CATEGORY_UPCOMING_MOVIES)});

            int rowInserted = event.getContext().getContentResolver().bulkInsert(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieVO.convertToContentValues(event.getResponse().getMovieList(), Constants.CATEGORY_UPCOMING_MOVIES));
            Log.d(MoviesApp.TAG, "upcoming>>inserted>>" + rowInserted);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLoadedEvent(DataEvent.PopularMovieLoadedEvent event) {

        if (event.isSuccessful()) {
            event.getContext().getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_TYPE + " =? ",
                    new String[]{String.valueOf(Constants.CATEGORY_POPULAR_MOVIES)});

            int rowInserted = event.getContext().getContentResolver().bulkInsert(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieVO.convertToContentValues(event.getResponse().getMovieList(), Constants.CATEGORY_POPULAR_MOVIES));
            Log.d(MoviesApp.TAG, "popular>>inserted>>" + rowInserted);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLoadedEvent(DataEvent.TrailerLoadedEvent event) {
        if (event.isSuccessful()) {
            List<TrailerVO> trailerList = event.getResponse().getTrailers();
            ContentValues[] trailerListCVs = TrailerVO.parseToContentValueArrayForMovie(trailerList, event.getMovieId());
            int rowInserted = event.getContext().getContentResolver().bulkInsert(MovieContract.TrailerEntry.CONTENT_URI, trailerListCVs);
            Log.d(MoviesApp.TAG, "trailer>>inserted>>" + rowInserted);
        }
    }
}
