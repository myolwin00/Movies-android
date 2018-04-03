package io.github.emrys919.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.emrys919.movies.R;
import io.github.emrys919.movies.adapters.MoviePagerAdapter;
import io.github.emrys919.movies.fragments.MovieListFragment;
import io.github.emrys919.movies.utils.Constants;

public class MovieListActivity extends BaseActivity {

    private static final String TAG = MovieListActivity.class.getSimpleName();

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pager_movies) ViewPager pagerMovies;
    @BindView(R.id.tl_movies) TabLayout tlMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this, this);
        setSupportActionBar(toolbar);

        MoviePagerAdapter mPagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addTab(MovieListFragment.newInstance(Constants.CATEGORY_NOW_PLAYING_MOVIES), "Now Playing");
        mPagerAdapter.addTab(MovieListFragment.newInstance(Constants.CATEGORY_UPCOMING_MOVIES), "Upcoming");
        mPagerAdapter.addTab(MovieListFragment.newInstance(Constants.CATEGORY_POPULAR_MOVIES), "Popular");

        pagerMovies.setAdapter(mPagerAdapter);
        pagerMovies.setOffscreenPageLimit(mPagerAdapter.getCount());
        tlMovies.setupWithViewPager(pagerMovies);

        /*TabLayout.Tab nowPlayingTab = tlMovies.getTabAt(0);
        TabLayout.Tab upcomingTab = tlMovies.getTabAt(1);
        TabLayout.Tab popularTab = tlMovies.getTabAt(2);
        if (nowPlayingTab == null || upcomingTab == null || popularTab == null) {
        } else {
            nowPlayingTab.setIcon(R.drawable.ic_play_circle_filled_white_48dp);
            upcomingTab.setIcon(R.drawable.ic_access_time_black_24dp);
            popularTab.setIcon(R.drawable.ic_star_black_24dp);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                navigateToSearch();
                return true;
            case R.id.action_about:
                navigateToAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToAbout() {
        Intent intent = AboutActivity.newIntent(this);
        startActivity(intent);
    }

    private void navigateToSearch() {

        Intent intent = SearchActivity.newIntent(this);
        startActivity(intent);
    }
}
