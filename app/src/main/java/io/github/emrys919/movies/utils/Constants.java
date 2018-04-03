package io.github.emrys919.movies.utils;

import io.github.emrys919.movies.BuildConfig;

public class Constants {

    // api url, key, etc...
    public static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/";
    public static final String BASE_LOW_IMG_URL = "http://image.tmdb.org/t/p/w500";
    public static final String BASE_HIGH_IMG_URL = "http://image.tmdb.org/t/p/original";

    public static final String YOUTUBE_TRAILER_PREVIEW_PATH = "http://img.youtube.com/vi/%s/0.jpg";

    public static final String BASE_YOTUBE_URL = "http://www.youtube.com/watch?v=";

    //TODO: enter your own tmdb api key here!
    public static final String MOVIE_API_KEY = BuildConfig.MovieApiKey;
    // TODO: enter our own youtube api key here!
    public static final String YOUTUBE_API_KEY = BuildConfig.YouTubeApiKey;

    public static final String API_GET_NOW_PLAYING_MOVIE_LIST = "movie/now_playing";
    public static final String API_GET_UPCOMING_MOVIE_LIST = "movie/upcoming";
    public static final String API_GET_POPULAR_MOVIE_LIST = "movie/popular";
    public static final String API_GET_MOVIE_DETAIL = "movie/{movieId}";
    public static final String API_GET_MOVIE_TRAILER = "movie/{movieId}/videos";
    public static final String API_SEARCH_MOVIE = "search/movie";

    public static final String PARAM_API_KEY = "api_key";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_MOVIE_ID = "movieId";
    public static final String PARAM_QUERY = "query";

    // loader id
    public static final int ID_NOW_PLAYING_LOADER = 100;
    public static final int ID_UPCOMING_LOADER = 101;
    public static final int ID_POPULAR_LOADER = 102;
    public static final int ID_MOVIE_DETAIL_LOADER = 200;

    // category id
    public static final int CATEGORY_OTHER_MOVIES = 0;
    public static final int CATEGORY_NOW_PLAYING_MOVIES = 1;
    public static final int CATEGORY_UPCOMING_MOVIES = 2;
    public static final int CATEGORY_POPULAR_MOVIES = 3;
    public static final int CATEGORY_FAVORITE_MOVIES = 4;

    // animation name
    public static final String ANIMATION_NAME = "animation";
}
