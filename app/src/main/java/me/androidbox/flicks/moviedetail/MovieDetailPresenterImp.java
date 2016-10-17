package me.androidbox.flicks.moviedetail;

import javax.inject.Inject;

import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.model.MovieDetail;
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
    public void loadMovieDetail(int movieId) {
        mMovieDetailModelImp.getMovieDetail(movieId, MovieDetailPresenterImp.this);
    }

    @Override
    public void onGetMovieDetailFailure(String errMessage) {

    }

    @Override
    public void onGetMovieDetailSuccess(MovieDetail movieDetail) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.displayOverview(movieDetail.getOverview());
            mMovieDetailViewContract.displayReleasedate(movieDetail.getRelease_date());
            mMovieDetailViewContract.displayTagline(movieDetail.getTagline());
            mMovieDetailViewContract.displayTitle(movieDetail.getTitle());
        }
        else {
            Timber.e("mMovieDetailViewContract == null, check attachView as been called");
        }
    }

    @Override
    public void onLoadMovieDetailSuccess() {

    }

    @Override
    public void onLoadMovieDetailFailure() {

    }

}
