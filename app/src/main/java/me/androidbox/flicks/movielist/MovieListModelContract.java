package me.androidbox.flicks.movielist;

import java.util.List;

import me.androidbox.flicks.model.Movies;

/**
 * Created by steve on 10/12/16.
 */

public interface MovieListModelContract {
    interface UpComingMovieListener {
        void onGetMovieFailed();
        void onGetMovieSuccess(Movies movieList);
    }

    void getUpComingMovies(UpComingMovieListener upComingMovieListener);
    void releaseResources();
}
