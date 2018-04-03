package io.github.emrys919.movies.mvp.views;

import java.util.List;

import io.github.emrys919.movies.data.vos.MovieVO;

/**
 * Created by myolwin00 on 8/27/17.
 */

public interface SearchView {
    void onSearching();
    void onSearchSuccess(List<MovieVO> movieList);
    void onSearchFailed(String message);
}
