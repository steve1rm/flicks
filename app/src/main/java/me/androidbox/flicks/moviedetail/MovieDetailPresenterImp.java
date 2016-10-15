package me.androidbox.flicks.moviedetail;

/**
 * Created by steve on 10/14/16.
 */

public class MovieDetailPresenterImp implements
        MovieDetailPresenterContract.MovieDetailPresenterEvents,
        MovieDetailPresenterContract.MovieDetailPresneterOps<MovieDetailView>,
        MovieDetailModelContract.GetMovieDetailListener {

    @Override
    public void attachView(MovieDetailView movieDetailView) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void loadMovieDetail(String movieId) {

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
