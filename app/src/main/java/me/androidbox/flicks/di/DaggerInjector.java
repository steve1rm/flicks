package me.androidbox.flicks.di;

import android.app.Application;

/**
 * Created by steve on 10/12/16.
 */

public class DaggerInjector extends Application {
    private static AppComponent sAppComponent = DaggerAppComponent
            .builder()
            .retrofitModule(new RetrofitModule())
            .presenterModule(new PresenterModule())
            .modelModule(new ModelModule())
            .build();

    public static AppComponent getsAppComponent() {
        return sAppComponent;
    }
}
