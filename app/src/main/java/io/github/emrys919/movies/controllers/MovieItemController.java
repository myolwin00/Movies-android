package io.github.emrys919.movies.controllers;

import android.widget.ImageView;

import io.github.emrys919.movies.data.vos.MovieVO;

/**
 * Created by myolwin00 on 7/9/17.
 */

public interface MovieItemController extends BaseItemController {
    void navigateToDetail(MovieVO movie, ImageView moviePoster);
}
