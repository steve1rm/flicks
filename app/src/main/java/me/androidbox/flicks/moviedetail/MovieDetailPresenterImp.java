package me.androidbox.flicks.moviedetail;

import javax.inject.Inject;

import me.androidbox.flicks.di.DaggerInjector;
import timber.log.Timber;

/**
 * Created by steve on 10/14/16.
 */

public class MovieDetailPresenterImp implements
        MovieDetailPresenterContract.MovieDetailPresenterEvents,
        MovieDetailPresenterContract.MovieDetailPresneterOps<MovieDetailView>,
        MovieDetailModelContract.GetMovieDetailListener {

    private MovieDetailViewContract mMovieDetailViewContract;

    @Inject MovieDetailModelImp mMovieDetailModelImp;

    public MovieDetailPresenterImp() {
        DaggerInjector.getsAppComponent().inject(MovieDetailPresenterImp.this);
        if(mMovieDetailModelImp != null) {
            Timber.d("mMovieDetailModelImp != null");
        }
    }

    @Override
    public void attachView(MovieDetailView movieDetailView) {
        mMovieDetailViewContract = movieDetailView;
    }

    @Override
    public void detachView() {
        mMovieDetailViewContract = null;
    }

    @Override
    public void loadMovieDetail(Long movieId) {
        mMovieDetailModelImp.getMovieDetail(movieId, MovieDetailPresenterImp.this);
    }

    @Override
    public void onGetMovieDetailFailure() {

    }

    @Override
    public void onGetMovieDetailSuccess() {

    }

    @Override
    public void onLoadMovieDetailSuccess() {

    }

    @Override
    public void onLoadMovieDetailFailure() {

    }

}
