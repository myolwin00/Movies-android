package io.github.emrys919.movies.events;

import android.content.Context;

import io.github.emrys919.movies.network.responses.SearchResponse;
import io.github.emrys919.movies.network.responses.MoviesResponse;
import io.github.emrys919.movies.network.responses.TrailerResponse;
import io.github.emrys919.movies.data.vos.MovieVO;

/**
 * Created by myolwin00 on 6/15/17.
 */

public class DataEvent {

    public static class MovieLoadedEvent {
        private MoviesResponse response;
        private String message;
        private Context context;
        private boolean isSuccessful;

        public MovieLoadedEvent(Context context, MoviesResponse response, String message, boolean isSuccessful) {
            this.response = response;
            this.message = message;
            this.context = context;
            this.isSuccessful = isSuccessful;
        }

        public MoviesResponse getResponse() {
            return response;
        }

        public String getMessage() {
            return message;
        }

        public Context getContext() {
            return context;
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }
    }

    public static class NowPlayingMovieLoadedEvent extends MovieLoadedEvent {

        public NowPlayingMovieLoadedEvent(Context context, MoviesResponse response, String message, boolean isSuccessful) {
            super(context, response, message, isSuccessful);
        }
    }

    public static class UpcomingMovieLoadedEvent extends MovieLoadedEvent {

        public UpcomingMovieLoadedEvent(Context context, MoviesResponse response, String message, boolean isSuccessful) {
            super(context, response, message, isSuccessful);
        }
    }

    public static class PopularMovieLoadedEvent extends MovieLoadedEvent {
        public PopularMovieLoadedEvent(Context context, MoviesResponse response, String message, boolean isSuccessful) {
            super(context, response, message, isSuccessful);
        }
    }

    public static class TrailerLoadedEvent {
        private Context context;
        private TrailerResponse response;
        private long movieId;
        private String message;
        private boolean isSuccessful;

        public TrailerLoadedEvent(Context context, TrailerResponse response, long movieId,
                                  String message, boolean isSuccessful) {
            this.context = context;
            this.response = response;
            this.movieId = movieId;
            this.message = message;
            this.isSuccessful = isSuccessful;
        }

        public Context getContext() {
            return context;
        }

        public TrailerResponse getResponse() {
            return response;
        }

        public long getMovieId() {
            return movieId;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }
    }

    public static class MovieDetailLoadedEvent {

        private Context context;
        private MovieVO movie;
        private String message;
        private boolean isSuccessful;

        public MovieDetailLoadedEvent(Context context, MovieVO movie, String message, boolean isSuccessful) {
            this.context = context;
            this.movie = movie;
            this.message = message;
            this.isSuccessful = isSuccessful;
        }

        public Context getContext() {
            return context;
        }

        public MovieVO getMovie() {
            return movie;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }
    }

    public static class SearchEvent {
        private SearchResponse response;

        public SearchEvent(SearchResponse response) {
            this.response = response;
        }

        public SearchResponse getResponse() {
            return response;
        }
    }
}
