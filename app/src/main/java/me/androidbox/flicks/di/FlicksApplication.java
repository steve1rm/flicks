package me.androidbox.flicks.di;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import timber.log.Timber;

/**
 * Created by steve on 10/12/16.
 */

public class FlicksApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(FlicksApplication.this);

        Timber.plant(new Timber.DebugTree());
    }
}
