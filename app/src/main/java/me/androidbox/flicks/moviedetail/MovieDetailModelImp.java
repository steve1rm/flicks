package me.androidbox.flicks.moviedetail;

import javax.inject.Inject;

import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.model.FlicksMovieService;
import me.androidbox.flicks.model.MovieDetail;
import me.androidbox.flicks.model.Videos;
import me.androidbox.flicks.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by steve on 10/14/16.
 */

public class MovieDetailModelImp implements MovieDetailModelContract {
    @Inject FlicksMovieService mFlicksMovieService;

    public MovieDetailModelImp() {
        DaggerInjector.getsAppComponent().inject(MovieDetailModelImp.this);

        if(mFlicksMovieService != null) {
            Timber.d("mFlicksMovieService != null");
        }
    }

    @Override
    public void getMovieDetail(final int movie_id, final GetMovieDetailListener getMovieDetailListener) {
        mFlicksMovieService.getMovieDetail(movie_id, Constants.API_KEY).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                Timber.d("onResponse: %d %d", movie_id, response.body().getId());
                getMovieDetailListener.onGetMovieDetailSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Timber.e(t, "onFailure");
                getMovieDetailListener.onGetMovieDetailFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getMovieVideo(int movie_id, final GetMovieTrailerListener getMovieTrailerListener) {
        mFlicksMovieService.getMovieVideos(movie_id, Constants.API_KEY).enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                Timber.d("onResponse");
                getMovieTrailerListener.onGetMovieTrailerSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                Timber.e(t, "onFailure");
                getMovieTrailerListener.onGetMovieTrailerFailure(t.getMessage());
            }
        });
    }
}
