package me.androidbox.flicks.movielist;

import me.androidbox.flicks.model.Movies;

/**
 * Created by steve on 10/12/16.
 */

public interface MovieListPresenterContract {

    /* Presenter <<-- view */
    interface MovieListPresenterOps<MovieListView> {
        void attachView(MovieListView view);
        void detachView();
        void loadUpcomingMovies();
    }

    /* Model -->> Presenter */
    interface MovieListPresenterEvents {
        void onLoadUpComingMoviesSuccess(Movies moviesList);
        void onLoadUpComingMoviesFailure();
    }
}
