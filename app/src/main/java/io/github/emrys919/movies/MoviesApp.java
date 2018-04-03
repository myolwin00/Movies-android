package io.github.emrys919.movies;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by myo on 5/2/17.
 */

public class MoviesApp extends Application {

    public static final String TAG = "MoviesApp";

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
