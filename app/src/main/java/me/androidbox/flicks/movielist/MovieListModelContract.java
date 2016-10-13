package me.androidbox.flicks.movielist;

/**
 * Created by steve on 10/12/16.
 */

public interface MovieListModelContract {
    interface UpComingMovieListener {
        void onGetMovieFailed();
        void onGetMovieSuccess();
    }

    void getUpComingMovies(UpComingMovieListener upComingMovieListener);
}
