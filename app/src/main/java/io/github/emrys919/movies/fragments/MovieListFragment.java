package io.github.emrys919.movies.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.emrys919.movies.R;
import io.github.emrys919.movies.activities.MovieDetailActivity;
import io.github.emrys919.movies.adapters.MovieAdapter;
import io.github.emrys919.movies.controllers.MovieItemController;
import io.github.emrys919.movies.data.persistence.MovieContract;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.mvp.presenters.MovieListPresenter;
import io.github.emrys919.movies.mvp.views.MovieListView;
import io.github.emrys919.movies.utils.Constants;

import static io.github.emrys919.movies.utils.Constants.ANIMATION_NAME;
import static io.github.emrys919.movies.utils.Constants.ID_NOW_PLAYING_LOADER;
import static io.github.emrys919.movies.utils.Constants.ID_POPULAR_LOADER;
import static io.github.emrys919.movies.utils.Constants.ID_UPCOMING_LOADER;

/**
 * Created by myolwin00 on 7/12/17.
 */

public class MovieListFragment extends BaseFragment
        implements MovieListView,
        LoaderManager.LoaderCallbacks<Cursor>,
        SwipeRefreshLayout.OnRefreshListener,
        MovieItemController {

    private static final String TAG = MovieListFragment.class.getSimpleName();
    private static final String BUNDLE_CATEGORY = "movie_list_category";

    @BindView(R.id.rv_movie_list) RecyclerView rvMovieList;
    @BindView(R.id.sr_refresh_movies) SwipeRefreshLayout srMovieRefresh;

    private int mCategory;
    private MovieListPresenter movieListPresenter;
    private View rootView;
    private MovieAdapter mMovieAdapter;
    private Snackbar snackbar;

    public static MovieListFragment newInstance(int category) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCategory = bundle.getInt(BUNDLE_CATEGORY);
        }
        movieListPresenter = new MovieListPresenter(getContext(), this, mCategory);
        movieListPresenter.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        mMovieAdapter = new MovieAdapter(getContext(), this);

        srMovieRefresh.setOnRefreshListener(this);
        srMovieRefresh.setColorSchemeResources(R.color.accent);

        int columnCount = getResources().getInteger(R.integer.movie_grid_count);
        rvMovieList.setLayoutManager(new GridLayoutManager(getContext(), columnCount));
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setAdapter(mMovieAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        switch (mCategory) {
            case Constants.CATEGORY_NOW_PLAYING_MOVIES:
                getLoaderManager().initLoader(Constants.ID_NOW_PLAYING_LOADER, null, this);
                break;
            case Constants.CATEGORY_UPCOMING_MOVIES:
                getLoaderManager().initLoader(Constants.ID_UPCOMING_LOADER, null, this);
                break;
            case Constants.CATEGORY_POPULAR_MOVIES:
                getLoaderManager().initLoader(Constants.ID_POPULAR_LOADER, null, this);
                break;
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieListPresenter.onDestroy();
    }

    @Override
    public void onLoadingMovieList() {
        snackbar = Snackbar.make(rootView, "Loading movies.", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public void onDisplayMovieList(List<MovieVO> movieList) {
        Log.d(TAG, "display: "+movieList.size());
        if (snackbar != null) {
            snackbar.dismiss();
        }
        srMovieRefresh.setRefreshing(false);
        mMovieAdapter.setNewData(movieList);
    }

    @Override
    public void onMovieLoadFailed(String message) {
        srMovieRefresh.setRefreshing(false);
        snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_TYPE + " =? ",
                new String[] {String.valueOf(mCategory)},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<MovieVO> movieList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                MovieVO movie = MovieVO.parseFromCursor(data);
                movieList.add(movie);
            } while (data.moveToNext());
        }

        Log.d(TAG, mCategory + ": retrieved from db: " + movieList.size());

        if (movieList.size() == 0) {
            movieListPresenter.loadMovieListFromNetwork();
        } else {
            movieListPresenter.onDisplayMovieList(movieList);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onRefresh() {
        movieListPresenter.loadMovieListFromNetwork();
    }

    @Override
    public void navigateToDetail(MovieVO movie, ImageView moviePoster) {
        Intent intent = MovieDetailActivity.newIntent(getContext(), movie.getId());
        //startActivity(intent);

        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), new Pair(moviePoster, ANIMATION_NAME));
        ActivityCompat.startActivity(getContext(), intent, activityOptions.toBundle());
    }
}
