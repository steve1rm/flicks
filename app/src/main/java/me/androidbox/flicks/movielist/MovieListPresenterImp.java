package me.androidbox.flicks.movielist;

import javax.inject.Inject;

import me.androidbox.flicks.di.DaggerInjector;
import timber.log.Timber;

/**
 * Created by steve on 10/12/16.
 */

public class MovieListPresenterImp implements
        MovieListPresenterContract.MoviePresenterOps<MovieListViewContract>,
        MovieListPresenterContract.PresenterEvents,
        MovieListModelContract.UpComingMovieListener {

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
    public void loadUpcomingMovies() {
        mMovieListModelImp.getUpComingMovies(MovieListPresenterImp.this);
    }

    @Override
    public void onLoadUpComingMoviesSuccess() {
        mMovieListView.loadUpcomingMovies();
    }

    @Override
    public void onLoadUpComingMoviesFailure() {

    }

    @Override
    public void onGetMovieFailed() {
        Timber.e("onGetMovieFailed");
    }

    @Override
    public void onGetMovieSuccess() {
        Timber.e("onGetMovieSuccess");
    }
}
