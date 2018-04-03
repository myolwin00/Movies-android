package io.github.emrys919.movies.activities;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.emrys919.movies.R;
import io.github.emrys919.movies.adapters.MovieAdapter;
import io.github.emrys919.movies.controllers.MovieItemController;
import io.github.emrys919.movies.data.models.MovieModel;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.events.DataEvent;
import io.github.emrys919.movies.mvp.presenters.SearchPresenter;
import io.github.emrys919.movies.mvp.views.SearchView;

import static android.R.id.message;
import static io.github.emrys919.movies.utils.Constants.ANIMATION_NAME;

public class SearchActivity extends AppCompatActivity
        implements MovieItemController, View.OnKeyListener, SearchView {

    @BindView(R.id.layout_loading) View loadingView;
    @BindView(R.id.search_container) FrameLayout searchContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.edt_search) EditText edtSearch;
    @BindView(R.id.rv_search_results) RecyclerView rvSearchResults;

    private MovieAdapter mMovieAdapter;
    private SearchPresenter mPresenter;
    private Snackbar snackbar;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this, this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mPresenter = new SearchPresenter(this, this);
        mPresenter.onCreate();

        mMovieAdapter = new MovieAdapter(this, this);

        int columnCount = getResources().getInteger(R.integer.movie_grid_count);
        rvSearchResults.setLayoutManager(new GridLayoutManager(this, columnCount));
        rvSearchResults.setItemAnimator(new DefaultItemAnimator());
        rvSearchResults.setAdapter(mMovieAdapter);

        edtSearch.setOnKeyListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @OnClick(R.id.ib_clear) void onClick() {
        edtSearch.setText("");
    }

    @Override
    public void navigateToDetail(MovieVO movie, ImageView moviePoster) {

        Intent intent = MovieDetailActivity.newIntent(this, movie.getId());

        overridePendingTransition(R.anim.enter, R.anim.exit);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, new Pair(moviePoster, ANIMATION_NAME));
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

            String query = edtSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(query)) {

                mPresenter.searchMovie(query);

                InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(edtSearch.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }

            return true;
        }

        return true;
    }

    @Override
    public void onSearching() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
        rvSearchResults.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearchSuccess(List<MovieVO> movieList) {
        if (snackbar != null) {
            snackbar.dismiss();
        }
        loadingView.setVisibility(View.GONE);
        rvSearchResults.setVisibility(View.VISIBLE);
        mMovieAdapter.setData(movieList);
    }

    @Override
    public void onSearchFailed(String message) {
        loadingView.setVisibility(View.GONE);
        snackbar = Snackbar.make(searchContainer, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
