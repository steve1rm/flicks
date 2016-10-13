package me.androidbox.flicks.movielist;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by steve on 10/12/16.
 */

public interface MovieListPresenterContract {

    /* Presenter <<-- view */
    interface MoviePresenterOps<MovieListView> {
        void attachView(MovieListView view);
        void detachView();
        void loadUpcomingMovies();
    }

    /* Model -->> Presenter */
    interface PresenterEvents {
        void onLoadUpComingMoviesSuccess();
        void onLoadUpComingMoviesFailure();
    }
}
