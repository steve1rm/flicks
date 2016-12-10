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
    }

    /* Model -->> Presenter */
    interface MovieListPresenterEvents {
        void onLoadUpComingMoviesSuccess(List<Results> moviesList);
        void onLoadUpComingMoviesFailure();
    }
}
