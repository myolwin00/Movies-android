package io.github.emrys919.movies.mvp.views;

import java.util.List;

import io.github.emrys919.movies.data.vos.MovieVO;

/**
 * Created by myolwin00 on 7/9/17.
 */

public interface MovieListView {
    void onLoadingMovieList();
    void onDisplayMovieList(List<MovieVO> movieList);
    void onMovieLoadFailed(String message);
}
