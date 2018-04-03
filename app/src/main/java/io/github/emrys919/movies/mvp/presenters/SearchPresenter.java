package io.github.emrys919.movies.mvp.presenters;

import android.content.Context;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.github.emrys919.movies.data.models.MovieModel;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.events.DataEvent;
import io.github.emrys919.movies.mvp.views.SearchView;
import io.github.emrys919.movies.utils.NetworkUtils;

/**
 * Created by myolwin00 on 8/27/17.
 */

public class SearchPresenter extends BasePresenter {

    private Context mContext;
    private SearchView mSearchView;
    private String query;

    public SearchPresenter(Context mContext, SearchView mSearchView) {
        this.mContext = mContext;
        this.mSearchView = mSearchView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DataEvent.SearchEvent event) {
        List<MovieVO> searchResult = event.getResponse().getResults();
        if (searchResult.size() > 0) {
            mSearchView.onSearchSuccess(searchResult);
        } else {
            mSearchView.onSearchFailed("\""+query+"\"" + " not found.");
        }
    }

    public void searchMovie(String query) {
        this.query = query;
        if (NetworkUtils.isOnline(mContext)) {
            mSearchView.onSearching();
            MovieModel.getInstance().searchMovies(query);
        } else {
            mSearchView.onSearchFailed("No internet connection.");
        }
    }
}
