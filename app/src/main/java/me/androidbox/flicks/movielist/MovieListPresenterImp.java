package me.androidbox.flicks.movielist;

import java.util.List;

import javax.inject.Inject;

import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.model.Pages;
import me.androidbox.flicks.model.Results;
import timber.log.Timber;

/**
 * Created by steve on 10/12/16.
 */

public class MovieListPresenterImp implements
        MovieListPresenterContract.MovieListPresenterOps<MovieListViewContract>,
        MovieListPresenterContract.MovieListPresenterEvents,
        MovieListModelContract.UpComingMovieListener,
        MovieListModelContract.NowPlayingListener {

    @Inject
    MovieListModelImp mMovieListModelImp;

    private MovieListViewContract mMovieListView;

    public MovieListPresenterImp() {
        DaggerInjector.getsAppComponent().inject(MovieListPresenterImp.this);

        if(mMovieListModelImp != null) {
            Timber.d("mMovieListModelImp != null");
        }
    }

    @Override
    public void attachView(MovieListViewContract movieListView) {
        mMovieListView = movieListView;
    }

    @Override
    public void detachView() {
        mMovieListView = null;
    }

    @Override
    public void onGetNowPlayingFailed() {

    }

    @Override
    public void onGetNowPlayingSuccess(Pages pages) {

    }

    @Override
    public void loadUpcomingMovies() {
        mMovieListModelImp.getNowPlayingMovies(MovieListPresenterImp.this);
    }

    @Override
    public void onLoadUpComingMoviesSuccess(List<Results> moviesList) {
        mMovieListView.loadUpcomingMovies(moviesList);
    }

    @Override
    public void onLoadUpComingMoviesFailure() {

    }


    @Override
    public void onGetMovieFailed() {
        Timber.e("onGetMovieFailed");
    }

    @Override
    public void onGetMovieSuccess(List<Results> moviesList) {
        Timber.d("onGetMovieSuccess");
        mMovieListView.loadUpcomingMovies(moviesList);
    }


}
