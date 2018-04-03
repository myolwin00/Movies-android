package io.github.emrys919.movies.data.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieProvider extends ContentProvider {

    private MovieDbHelper mMovieDbHelper;

    private static final int MOVIES = 100;
    private static final int TRAILERS = 200;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TRAILERS, TRAILERS);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mMovieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int rowsInserted = 0;

        try {
            db.beginTransaction();
            for (ContentValues value : values) {
                long _id = db.insert(tableName, null, value);
                if (_id > 0) {
                    rowsInserted++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        Context context = getContext();
        if (context != null && rowsInserted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return rowsInserted;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        String tableName = getTableName(uri);
        Cursor cursor = mMovieDbHelper.getReadableDatabase().query(
                tableName, projection, selection, selectionArgs, null, null, sortOrder);

        Context context = getContext();
        if (context != null) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case MOVIES:
                return MovieContract.MovieEntry.DIR_TYPE;
            case TRAILERS:
                return MovieContract.TrailerEntry.DIR_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();
        String tableName = getTableName(uri);
        Uri contentUri = getContentUri(uri);

        long _id = db.insert(tableName, null, values);

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return ContentUris.withAppendedId(contentUri, _id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
        String tableName = getTableName(uri);

        int rowDeleted = db.delete(tableName, selection, selectionArgs);

        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
        String tableName = getTableName(uri);

        int rowUpdated = db.update(tableName, values, selection, selectionArgs);

        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    private String getTableName(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                return MovieContract.MovieEntry.TABLE_NAME;
            case TRAILERS:
                return MovieContract.TrailerEntry.TABLE_NAME;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    private Uri getContentUri(Uri uri) {
        int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_URI;
            case TRAILERS:
                return MovieContract.TrailerEntry.CONTENT_URI;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }
}
