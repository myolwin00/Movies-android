package io.github.emrys919.movies.mvp.views;

import io.github.emrys919.movies.data.vos.MovieVO;

/**
 * Created by myolwin00 on 8/2/17.
 */

public interface MovieDetailView {
    void onDetailLoading();
    void onDetailLoaded(MovieVO movie);
    void onDetailLoadFailed(String message);
}
