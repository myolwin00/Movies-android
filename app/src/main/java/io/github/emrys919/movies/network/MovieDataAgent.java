package io.github.emrys919.movies.network;

import android.content.Context;

/**
 * Created by myolwin00 on 6/15/17.
 */

public interface MovieDataAgent {
    //void loadAllMovies();
    void loadNowPlayingMovies(Context context);
    void loadUpcomingMovies(Context context);
    void loadPopularMovies(Context context);

    void loadMovieDetail(Context context, long movieId, int movieType);

    void loadMovieTrailers(Context context, long movieId);

    void onSearchMovies(String query);
}
