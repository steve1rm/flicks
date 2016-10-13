package me.androidbox.flicks.movielist;

import javax.inject.Inject;

import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.model.FlicksMovieService;
import timber.log.Timber;

/**
 * Created by steve on 10/12/16.
 */

public class MovieListModelImp implements MovieListModelContract {

    @Inject
    FlicksMovieService mFlicksMoveService;

    public MovieListModelImp() {
        DaggerInjector.getsAppComponent().inject(MovieListModelImp.this);

        if(mFlicksMoveService != null) {
            Timber.d("mFlicksMovieService != null");
        }

    }

    @Override
    public void getUpComingMovies(UpComingMovieListener upComingMovieListener) {
        Timber.d("getUpComingMovies");
    }
}
