package me.androidbox.flicks.movielist;

import java.util.List;

import me.androidbox.flicks.model.Results;

/**
 * Created by steve on 10/12/16.
 */

public interface MovieListPresenterContract {

    /* Presenter <<-- view */
    interface MovieListPresenterOps<MovieListView> {
        void attachView(MovieListView view);
        void detachView();
        void loadUpcomingMovies();
        void loadNowPlayingMovies();
    }

    /* Model -->> Presenter */
    interface MovieListPresenterEvents {
        void onLoadUpComingMoviesSuccess(List<Results> moviesList);
        void onLoadUpComingMoviesFailure();
        void onLoadNowPlayingMovies(List<Results> movieList);
        void onLoadNowPlayingMoviesFailure(String errorMessage);
    }
}
