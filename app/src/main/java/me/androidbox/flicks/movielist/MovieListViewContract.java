package me.androidbox.flicks.movielist;

import me.androidbox.flicks.model.Movies;

/**
 * Created by steve on 10/12/16.
 */

public interface MovieListViewContract {
    void loadUpcomingMovies(Movies moviesList);
}
