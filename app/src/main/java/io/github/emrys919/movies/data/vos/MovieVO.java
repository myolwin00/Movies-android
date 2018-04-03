
package io.github.emrys919.movies.data.vos;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.github.emrys919.movies.data.persistence.MovieContract;

import static android.R.attr.type;

public class MovieVO {

    // new
    @SerializedName("belongs_to_collection")
    private Object mBelongsToCollection;
    @SerializedName("budget")
    private Long mBudget;
    @SerializedName("genres")
    private List<Genre> mGenres;
    @SerializedName("homepage")
    private String mHomepage;
    @SerializedName("imdb_id")
    private String mImdbId;
    @SerializedName("production_companies")
    private List<ProductionCompany> mProductionCompanies;
    @SerializedName("production_countries")
    private List<ProductionCountry> mProductionCountries;
    @SerializedName("revenue")
    private Long mRevenue;
    @SerializedName("runtime")
    private Long mRuntime;
    @SerializedName("spoken_languages")
    private List<SpokenLanguage> mSpokenLanguages;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("tagline")
    private String mTagline;
    //>
    @SerializedName("adult")
    private Boolean mAdult;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("genre_ids")
    private List<Long> mGenreIds;
    @SerializedName("id")
    private Long mId;
    @SerializedName("original_language")
    private String mOriginalLanguage;
    @SerializedName("original_title")
    private String mOriginalTitle;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("popularity")
    private Double mPopularity;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("video")
    private Boolean mVideo;
    @SerializedName("vote_average")
    private Double mVoteAverage;
    @SerializedName("vote_count")
    private Long mVoteCount;

    private boolean isFavorite;
    private int movieType;
    private List<TrailerVO> trailerList;

    public List<TrailerVO> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<TrailerVO> trailerList) {
        this.trailerList = trailerList;
    }

    public Long getRuntime() {
        return mRuntime;
    }

    public void setRuntime(Long mRuntime) {
        this.mRuntime = mRuntime;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(Boolean adult) {
        mAdult = adult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public List<Long> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        mGenreIds = genreIds;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Boolean getVideo() {
        return mVideo;
    }

    public void setVideo(Boolean video) {
        mVideo = video;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public Long getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(Long voteCount) {
        mVoteCount = voteCount;
    }

    public int getMovieType() {
        return movieType;
    }

    public void setMovieType(int movieType) {
        this.movieType = movieType;
    }

    public static ContentValues[] convertToContentValues(List<MovieVO> movieList, int movieType) {
        ContentValues[] movieCVs = new ContentValues[movieList.size()];
        for (int i = 0; i < movieList.size(); i++) {
            MovieVO movie = movieList.get(i);
            movie.setMovieType(movieType);
            movieCVs[i] = parseToContentValues(movie);
        }
        return movieCVs;
    }

    public static ContentValues parseToContentValues(MovieVO movie) {
        ContentValues cv = new ContentValues();

        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.mId);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH, movie.mBackdropPath);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, movie.mOverview);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, movie.mPosterPath);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.mReleaseDate);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movie.mTitle);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_TYPE, movie.movieType);

        if (movie.mRuntime != null) {
            cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_RUNTIME, movie.mRuntime);
        }
        //cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT, mVoteCount);
        //cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_IS_FAVORITE, isFavorite ? 1 : 0);
        return cv;
    }

    public static MovieVO parseFromCursor(Cursor data) {
        MovieVO movie = new MovieVO();
        movie.mId = data.getLong(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
        movie.mPosterPath = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH));
        movie.mBackdropPath = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH));
        //movie.isFavorite = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_IS_FAVORITE)) == 1;
        movie.mTitle = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE));
        movie.mOverview = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW));
        movie.mRuntime = data.getLong(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RUNTIME));
        movie.mReleaseDate = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE));
        movie.movieType = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TYPE));
        return movie;
    }
}
