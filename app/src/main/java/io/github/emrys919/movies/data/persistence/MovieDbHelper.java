package io.github.emrys919.movies.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.github.emrys919.movies.data.persistence.MovieContract.*;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "movies.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " ("
            + MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, "
            + MovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT, "
            + MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT, "
            + MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT, "
            + MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, "
            + MovieEntry.COLUMN_MOVIE_BACKDROP_PATH + " TEXT, "
            + MovieEntry.COLUMN_MOVIE_RUNTIME + " REAL DEFAULT 0, "
            + MovieEntry.COLUMN_MOVIE_TYPE + " INTEGER NOT NULL, "
            //+ MovieEntry.COLUMN_MOVIE_IS_FAVORITE + " INTEGER DEFAULT 0, "
            //+ " UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT IGNORE ), "
            + " UNIQUE ("
            + MovieEntry.COLUMN_MOVIE_ID + ", " + MovieEntry.COLUMN_MOVIE_TYPE
            + ") ON CONFLICT IGNORE );";

    private static final String CREATE_TRAILER_TABLE =
            "CREATE TABLE " + TrailerEntry.TABLE_NAME + " ("
            + TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TrailerEntry.COLUMN_TRAILER_ID + " TEXT NOT NULL, "
            + TrailerEntry.COLUMN_TRAILER_KEY + " TEXT NOT NULL, "
            + TrailerEntry.COLUMN_TRAILER_NAME + " TEXT NOT NULL, "
            + TrailerEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, "
            + " UNIQUE (" + TrailerEntry.COLUMN_TRAILER_ID + ") ON CONFLICT IGNORE );";

    public MovieDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIES_TABLE);
        db.execSQL(CREATE_TRAILER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrailerEntry.TABLE_NAME);
        onCreate(db);
    }
}
