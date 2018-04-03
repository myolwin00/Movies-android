package io.github.emrys919.movies.controllers;

import io.github.emrys919.movies.data.vos.TrailerVO;

/**
 * Created by myolwin00 on 8/7/17.
 */

public interface TrailerItemController extends BaseItemController {
    void onTrailerItemClicked(TrailerVO trailer);
}
