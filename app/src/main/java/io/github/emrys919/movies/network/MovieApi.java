package io.github.emrys919.movies.network;

import io.github.emrys919.movies.network.responses.SearchResponse;
import io.github.emrys919.movies.network.responses.MoviesResponse;
import io.github.emrys919.movies.network.responses.TrailerResponse;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.utils.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by myolwin00 on 6/15/17.
 */

public interface MovieApi {

    @GET(Constants.API_GET_NOW_PLAYING_MOVIE_LIST)
    Call<MoviesResponse> getNowPlayingMovies(
            @Query(Constants.PARAM_API_KEY) String apiKey
            //@Query(Constants.PARAM_PAGE) int page
    );

    @GET(Constants.API_GET_UPCOMING_MOVIE_LIST)
    Call<MoviesResponse> getUpcomingMovies(
            @Query(Constants.PARAM_API_KEY) String apiKey
            //@Query("page") int page
    );

    @GET(Constants.API_GET_POPULAR_MOVIE_LIST)
    Call<MoviesResponse> getPopularMovies(
            @Query(Constants.PARAM_API_KEY) String apiKey
            //@Query("page") int page
    );

    @GET(Constants.API_GET_MOVIE_DETAIL)
    Call<MovieVO> getMovieDetails(
            @Path(Constants.PARAM_MOVIE_ID) long movieId,
            @Query(Constants.PARAM_API_KEY) String apiKey
    );

    @GET(Constants.API_GET_MOVIE_TRAILER)
    Call<TrailerResponse> getMovieTrailers(
            @Path(Constants.PARAM_MOVIE_ID) long movieId,
            @Query(Constants.PARAM_API_KEY) String apiKey
    );

    @GET(Constants.API_SEARCH_MOVIE)
    Call<SearchResponse> searchMovies(
            @Query(Constants.PARAM_QUERY) String query,
            @Query(Constants.PARAM_API_KEY) String apiKey
    );
}
