package me.androidbox.flicks.movielist;

import android.content.Context;
import android.content.res.Resources;

import java.util.List;

import javax.inject.Inject;

import me.androidbox.flicks.R;
import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.model.FlicksMovieService;
import me.androidbox.flicks.model.Movie;
import me.androidbox.flicks.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

        mFlicksMoveService.getUpcomingMovies(Constants.API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Timber.d(response.body().toString());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Timber.e(t, "onFailure");
            }
        });
    }
}
