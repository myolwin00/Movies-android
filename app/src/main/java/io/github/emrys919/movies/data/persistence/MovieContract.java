package io.github.emrys919.movies.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import io.github.emrys919.movies.MoviesApp;

import static io.github.emrys919.movies.data.persistence.MovieContract.MovieEntry.COLUMN_MOVIE_ID;

public class MovieContract {

    // io.github.emrys919.movies
    public static final String CONTENT_AUTHORITY = MoviesApp.class.getPackage().getName();
    //  content://io.github.emrys919.movies
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_TRAILERS = "trailers";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String TABLE_NAME = PATH_MOVIES;

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_POSTER_PATH = "poster_path";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_MOVIE_RUNTIME = "runtime";
        public static final String COLUMN_MOVIE_TYPE = "movie_type";
//        public static final String COLUMN_MOVIE_POPULARITY = "popularity";
//        public static final String COLUMN_MOVIE_VOTE_COUNT = "vote_count";
//        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "vote_average";
//        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "original_title";
//        public static final String COLUMN_MOVIE_IS_FAVORITE = "is_favorite";
    }

    public static final class TrailerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;

        public static final String TABLE_NAME = PATH_TRAILERS;

        public static final String COLUMN_TRAILER_ID = "trailer_id";
        public static final String COLUMN_TRAILER_KEY = "key";
        public static final String COLUMN_TRAILER_NAME = "name";
        public static final String COLUMN_MOVIE_ID = "movie_id";
    }
}
