package io.github.emrys919.movies.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.emrys919.movies.R;
import io.github.emrys919.movies.adapters.TrailerAdapter;
import io.github.emrys919.movies.controllers.TrailerItemController;
import io.github.emrys919.movies.data.persistence.MovieContract;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.data.vos.TrailerVO;
import io.github.emrys919.movies.mvp.presenters.MovieDetailPresenter;
import io.github.emrys919.movies.mvp.views.MovieDetailView;
import io.github.emrys919.movies.utils.Constants;

import static android.R.id.message;
import static io.github.emrys919.movies.utils.Constants.ANIMATION_NAME;
import static io.github.emrys919.movies.utils.Constants.ID_MOVIE_DETAIL_LOADER;

public class MovieDetailActivity extends BaseActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        MovieDetailView, TrailerItemController, Palette.PaletteAsyncListener {

    private static final String BUNDLE_MOVIE_ID = "movie_id";

    @BindView(R.id.trailer_container) LinearLayout trailerContainer;
    @BindView(R.id.layout_movie_detail) View detailView;
    @BindView(R.id.view_movie_loading) View detailLoadingView;
    @BindView(R.id.header) LinearLayout llHeader;
    @BindView(R.id.details) LinearLayout llDetails;
    //@BindView(R.id.background) View background;
    @BindView(R.id.rv_trailers) RecyclerView rvTrailers;

    @BindView(R.id.iv_movie_poster) ImageView ivPoster;
    @BindView(R.id.iv_backdrop) ImageView ivBackdrop;

    @BindView(R.id.tv_overview) TextView tvOverview;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_runtime) TextView tvRuntime;
    @BindView(R.id.tv_released) TextView tvReleaseDate;
    @BindView(R.id.tv_trailer) TextView tvTrailer;
    @BindView(R.id.tv_genre) TextView tvGenre;
    @BindView(R.id.tv_trailer_error) TextView tvTrailerError;

    @BindView(R.id.toolbar) Toolbar toolbar;

    private long movieId;
    private MovieDetailPresenter mPresenter;
    private TrailerAdapter mTrailerAdapter;

    public static Intent newIntent(Context context, long movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(BUNDLE_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this, this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        movieId = getIntent().getLongExtra(BUNDLE_MOVIE_ID, 0);

        mPresenter = new MovieDetailPresenter(this, movieId, this);
        mPresenter.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivPoster.setTransitionName(ANIMATION_NAME);
        }

        mTrailerAdapter = new TrailerAdapter(this, this);

        rvTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTrailers.setItemAnimator(new DefaultItemAnimator());
        rvTrailers.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        rvTrailers.setAdapter(mTrailerAdapter);

        getSupportLoaderManager().initLoader(ID_MOVIE_DETAIL_LOADER, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MovieContract.MovieEntry.CONTENT_URI, null,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " =? ",
                new String[] {String.valueOf(movieId)},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            MovieVO movie = MovieVO.parseFromCursor(data);
            mPresenter.onLoadFinished(movie);
        } else {
            mPresenter.onLoadFinished(null);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onDetailLoading() {
        detailView.setVisibility(View.INVISIBLE);
        detailLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDetailLoaded(MovieVO movie) {

        detailLoadingView.setVisibility(View.INVISIBLE);
        detailView.setVisibility(View.VISIBLE);

        if (movie.getTrailerList().size() > 0) {
            mTrailerAdapter.setData(movie.getTrailerList());
        } else {
            rvTrailers.setVisibility(View.GONE);
            tvTrailerError.setVisibility(View.VISIBLE);
        }

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvRuntime.setText("Runtime:  "+String.valueOf(movie.getRuntime()) + "min");
        //TODO: genre list
        tvGenre.setText("Genre:  ");
        tvReleaseDate.setText("Release Date:  "+movie.getReleaseDate());


        Glide.with(this)
                .load(Constants.BASE_LOW_IMG_URL + movie.getPosterPath())
                .centerCrop()
                .thumbnail(0.1f)
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.movie_placeholder)
                .into(ivPoster);

        if (movie.getBackdropPath() == null) {
            Glide.with(this)
                    .load(R.drawable.movie_placeholder_land)
                    .into(ivBackdrop);
        } else {
            Glide.with(this)
                    .load(Constants.BASE_LOW_IMG_URL + movie.getBackdropPath())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            ivBackdrop.setImageBitmap(resource);
                            Palette.from(resource).generate(MovieDetailActivity.this);
                        }
                    });
        }
    }

    @Override
    public void onDetailLoadFailed(String message) {
        detailLoadingView.setVisibility(View.INVISIBLE);
        detailView.setVisibility(View.VISIBLE);
        Snackbar.make(detailView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.loadDetailFromNetwork();
                    }
                })
                .show();
    }

    @Override
    public void onTrailerItemClicked(TrailerVO trailer) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.BASE_YOTUBE_URL+trailer.getKey())));
    }

    @Override
    public void onGenerated(Palette palette) {
        // Use generated instance
        Palette.Swatch swatch = palette.getVibrantSwatch();

        if (swatch != null) {

            int backgroundColor = swatch.getRgb();

            llHeader.setBackgroundColor(backgroundColor);
            tvTitle.setTextColor(swatch.getTitleTextColor());
            tvOverview.setTextColor(swatch.getBodyTextColor());

            trailerContainer.setBackgroundColor(backgroundColor);
            //detailView.setBackgroundColor(swatch.getRgb());
            llDetails.setBackgroundColor(backgroundColor);
            tvRuntime.setTextColor(swatch.getBodyTextColor());
            tvReleaseDate.setTextColor(swatch.getBodyTextColor());

            tvTrailer.setTextColor(swatch.getTitleTextColor());
            tvGenre.setTextColor(swatch.getBodyTextColor());
            tvTrailerError.setTextColor(swatch.getBodyTextColor());
        }
    }
}
