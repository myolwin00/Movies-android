
package io.github.emrys919.movies.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.github.emrys919.movies.data.persistence.MovieContract;
import io.github.emrys919.movies.utils.Constants;

import static android.R.attr.id;
import static android.R.attr.key;
import static android.R.attr.name;
import static android.R.attr.type;

public class TrailerVO {

    @SerializedName("id")
    private String mId;
    @SerializedName("iso_3166_1")
    private String mIso31661;
    @SerializedName("iso_639_1")
    private String mIso6391;
    @SerializedName("key")
    private String mKey;
    @SerializedName("name")
    private String mName;
    @SerializedName("site")
    private String mSite;
    @SerializedName("size")
    private Long mSize;
    @SerializedName("type")
    private String mType;

    public String getTrailerPath() {
        return String.format(Constants.YOUTUBE_TRAILER_PREVIEW_PATH, mKey);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getIso31661() {
        return mIso31661;
    }

    public void setIso31661(String iso31661) {
        mIso31661 = iso31661;
    }

    public String getIso6391() {
        return mIso6391;
    }

    public void setIso6391(String iso6391) {
        mIso6391 = iso6391;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        mSite = site;
    }

    public Long getSize() {
        return mSize;
    }

    public void setSize(Long size) {
        mSize = size;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public static ContentValues[] parseToContentValueArrayForMovie(List<TrailerVO> trailerList, long movieId) {
        ContentValues[] contentValuesArray = new ContentValues[trailerList.size()];
        for (int index = 0; index < contentValuesArray.length; index++) {
            TrailerVO trailer = trailerList.get(index);
            contentValuesArray[index] = trailer.parseToContentValuesForMovie(movieId);
        }
        return contentValuesArray;
    }

    private ContentValues parseToContentValuesForMovie(long movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_ID, mId);
        //contentValues.put(MovieContract.TrailerEntry.COLUMN_ISO_639_1, mIso6391);
        contentValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_KEY, mKey);
        contentValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_NAME, mName);
        //contentValues.put(MovieContract.TrailerEntry.COLUMN_SITE, site);
        //contentValues.put(MovieContract.TrailerEntry.COLUMN_SIZE, size);
        //contentValues.put(MovieContract.TrailerEntry.COLUMN_TYPE, type);
        contentValues.put(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, movieId);
        return contentValues;
    }

    public static List<TrailerVO> loadTrailerListByMovieId(Context context, long movieId) {
        List<TrailerVO> trailerList = new ArrayList<>();

        Cursor trailerCursor = context.getContentResolver().query(MovieContract.TrailerEntry.CONTENT_URI,
                null,
                MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " =? ",
                new String[]{String.valueOf(movieId)},
                null);

        if (trailerCursor != null && trailerCursor.moveToFirst()) {
            do {
                trailerList.add(TrailerVO.parseFromCursor(trailerCursor));

            } while (trailerCursor.moveToNext());
            trailerCursor.close();
        }

        return trailerList;
    }

    public static TrailerVO parseFromCursor(Cursor trailerCursor) {
        TrailerVO trailer = new TrailerVO();
        trailer.mId = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TRAILER_ID));
        //trailer.iso639_1 = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_ISO_639_1));
        trailer.mKey = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TRAILER_KEY));
        trailer.mName = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TRAILER_NAME));
        //trailer.site = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_SITE));
        //trailer.size = trailerCursor.getInt(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_SIZE));
        //trailer.type = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TYPE));
        return trailer;
    }
}
