package io.github.emrys919.movies.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.github.emrys919.movies.network.responses.SearchResponse;
import io.github.emrys919.movies.network.responses.MoviesResponse;
import io.github.emrys919.movies.network.responses.TrailerResponse;
import io.github.emrys919.movies.data.vos.MovieVO;
import io.github.emrys919.movies.events.DataEvent;
import io.github.emrys919.movies.events.DataEvent.MovieDetailLoadedEvent;
import io.github.emrys919.movies.events.DataEvent.NowPlayingMovieLoadedEvent;
import io.github.emrys919.movies.events.DataEvent.PopularMovieLoadedEvent;
import io.github.emrys919.movies.events.DataEvent.UpcomingMovieLoadedEvent;
import io.github.emrys919.movies.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by myolwin00 on 6/15/17.
 */

public class RetrofitAgent implements MovieDataAgent {

    private static final String TAG = RetrofitAgent.class.getSimpleName();

    private static RetrofitAgent objInstance;
    private final MovieApi movieApi;

    private RetrofitAgent() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClient)
                .build();

        movieApi = retrofit.create(MovieApi.class);
    }

    public static RetrofitAgent getInstance() {
        if (objInstance == null) {
            objInstance = new RetrofitAgent();
        }
        return objInstance;
    }

    @Override
    public void loadMovieDetail(final Context context, final long movieId, final int movieType) {
        Log.d(TAG, "detail loading.");
        Call<MovieVO> loadMovieDetailCall = movieApi.getMovieDetails(movieId, Constants.MOVIE_API_KEY);
        loadMovieDetailCall.enqueue(new Callback<MovieVO>() {
            @Override
            public void onResponse(Call<MovieVO> call, Response<MovieVO> response) {
                MovieVO movieResponse = response.body();
                if (movieResponse != null) {
                    Log.d(TAG, "detail loading success: "+ movieResponse.getTitle());
                    movieResponse.setMovieType(movieType);
                    MovieDetailLoadedEvent event = new MovieDetailLoadedEvent(context, movieResponse,
                            response.message(), true);
                    EventBus.getDefault().post(event);
                } else {
                    MovieDetailLoadedEvent event = new MovieDetailLoadedEvent(context, null,
                            response.message(), false);
                    EventBus.getDefault().post(event);
                    Log.d(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieVO> call, Throwable t) {
                Log.d(TAG, "failed: "+ movieId+" :"+ t.getMessage());
                MovieDetailLoadedEvent event = new MovieDetailLoadedEvent(context, null, t.getMessage(), false);
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public void loadMovieTrailers(final Context context, final long movieId) {
        Call<TrailerResponse> loadMovieTrailersCall = movieApi.getMovieTrailers(movieId, Constants.MOVIE_API_KEY);
        loadMovieTrailersCall.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                TrailerResponse trailerResponse = response.body();
                if (trailerResponse != null) {
                    DataEvent.TrailerLoadedEvent event =
                            new DataEvent.TrailerLoadedEvent(context, trailerResponse, movieId,
                                    response.message(), true);
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                DataEvent.TrailerLoadedEvent event =
                        new DataEvent.TrailerLoadedEvent(context, null, movieId, t.getMessage(), false);
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public void onSearchMovies(String query) {
        final Call<SearchResponse> searchMovieCall = movieApi.searchMovies(query, Constants.MOVIE_API_KEY);
        searchMovieCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                if (searchResponse != null) {
                    DataEvent.SearchEvent event = new DataEvent.SearchEvent(searchResponse);
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadNowPlayingMovies(final Context context) {
        Log.d(TAG, "now playing loading.");
        Call<MoviesResponse> loadMovieCall = movieApi.getNowPlayingMovies(Constants.MOVIE_API_KEY);
        loadMovieCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                if (moviesResponse != null) {
                    NowPlayingMovieLoadedEvent event = new NowPlayingMovieLoadedEvent(
                            context, moviesResponse, response.message(), true);
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                NowPlayingMovieLoadedEvent event = new NowPlayingMovieLoadedEvent(
                        context, null, t.getMessage(), false);
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public void loadUpcomingMovies(final Context context) {
        Log.d(TAG, "loading>> upcoming");
        Call<MoviesResponse> loadMovieCall = movieApi.getUpcomingMovies(Constants.MOVIE_API_KEY);
        loadMovieCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                if (moviesResponse != null) {
                    UpcomingMovieLoadedEvent event =
                            new UpcomingMovieLoadedEvent(context, moviesResponse,
                                    response.message(), true);
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                UpcomingMovieLoadedEvent event =
                        new UpcomingMovieLoadedEvent(context, null, t.getMessage(), false);
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public void loadPopularMovies(final Context context) {
        Log.d(TAG, "loading>> popular");
        Call<MoviesResponse> loadMovieCall = movieApi.getPopularMovies(Constants.MOVIE_API_KEY);
        loadMovieCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                if (moviesResponse != null) {
                    PopularMovieLoadedEvent event =
                            new PopularMovieLoadedEvent(context, moviesResponse, response.message(), true);
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                PopularMovieLoadedEvent event =
                        new PopularMovieLoadedEvent(context, null, t.getMessage(), false);
                EventBus.getDefault().post(event);
            }
        });
    }
}
