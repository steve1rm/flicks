package me.androidbox.flicks.movielist;

import java.util.List;

import me.androidbox.flicks.model.Latest;
import me.androidbox.flicks.model.Pages;
import me.androidbox.flicks.model.Results;

/**
 * Created by steve on 10/12/16.
 */

public interface MovieListModelContract {
    interface UpComingMovieListener {
        void onGetMovieFailed();
        void onGetMovieSuccess(List<Results> movieList);
    }
    void getUpComingMovies(UpComingMovieListener upComingMovieListener);

    interface NowPlayingListener {
        void onGetNowPlayingFailed();
        void onGetNowPlayingSuccess(Pages pages);
    }
    void getNowPlayingMovies(NowPlayingListener nowPlayingListener);

    interface LatestMovieListener {
        void onGetLatestMovieFailed();
        void onGetLatestMovieSuccess(Latest latest);
    }
    void getLatestMovie(LatestMovieListener latestMovieListener);

    void releaseResources();
}
