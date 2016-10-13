package me.androidbox.flicks.di;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by steve on 10/12/16.
 */

public class FlicksApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
