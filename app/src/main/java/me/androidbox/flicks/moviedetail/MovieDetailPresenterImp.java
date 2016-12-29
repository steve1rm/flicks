package me.androidbox.flicks.moviedetail;

import javax.inject.Inject;

import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.model.MovieDetail;
import me.androidbox.flicks.model.Videos;
import timber.log.Timber;

/**
 * Created by steve on 10/14/16.
 */

public class MovieDetailPresenterImp implements
        MovieDetailPresenterContract.MovieDetailPresneterOps<MovieDetailView>,
        MovieDetailModelContract.GetMovieDetailListener,
        MovieDetailModelContract.GetMovieTrailerListener {

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
    public void loadMovieTrailer(int movieId) {
        mMovieDetailModelImp.getMovieVideo(movieId, MovieDetailPresenterImp.this);
    }

    @Override
    public void onGetMovieDetailFailure(String errMessage) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.displayFailureMessage();
        }
        else {
            Timber.e("mMovieDetailViewContract == null, check attachView as been called");
        }
    }

    /* Calculate the vote average by averaging with 50 to get a scale of 0 to 5 */
    private float calculateAverage(float voteAverage) {
        return (voteAverage / 10) * 5;
    }

    @Override
    public void onGetMovieDetailSuccess(MovieDetail movieDetail) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.displayOverview(movieDetail.getOverview());
            mMovieDetailViewContract.displayReleasedate(movieDetail.getRelease_date());
            mMovieDetailViewContract.displayTagline(movieDetail.getTagline());
            mMovieDetailViewContract.displayTitle(movieDetail.getTitle());
            mMovieDetailViewContract.displayMovieThumbnail(movieDetail.getPoster_path());
            mMovieDetailViewContract.displayMovieBackdropPoster(movieDetail.getBackdrop_path());
            mMovieDetailViewContract.displayRunningTime(movieDetail.getRuntime());
            mMovieDetailViewContract.displayGetMovieRating(calculateAverage(movieDetail.getVote_average()));
        }
        else {
            Timber.e("mMovieDetailViewContract == null, check attachView as been called");
        }
    }



    @Override
    public void onGetMovieTrailerFailure(String errMessage) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.playMovieTrailerFailure();
        }
        else {
            Timber.e("mMovieDetailViewContract == null, check attachView as been called");
        }

    }

    @Override
    public void onGetMovieTrailerSuccess(Videos videos) {
        if(mMovieDetailViewContract != null) {
            if(videos.getResults().size() > 0) {
      //          mMovieDetailViewContract.playMovieTrailer(videos.getResults().get(0).getKey());
            }
            else {
                mMovieDetailViewContract.playMovieTrailerFailure();
            }
        }
        else {
            Timber.e("mMovieDetailViewContract == null, check attachView as been called");
        }

    }
}
