package me.androidbox.flicks.movielist;

import java.util.List;

import me.androidbox.flicks.model.Results;

/**
 * Created by steve on 10/12/16.
 */

public interface MovieListViewContract {
    void loadUpcomingMovies(List<Results> moviesList);
    void loadNowPlayingMovies(List<Results> movieList);
}
