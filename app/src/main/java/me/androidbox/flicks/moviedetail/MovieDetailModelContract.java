package me.androidbox.flicks.moviedetail;

/**
 * Created by steve on 10/14/16.
 */

public interface MovieDetailModelContract {
    interface GetMovieDetailListener {
        void onGetMovieDetailSuccess();
        void onGetMovieDetailFailure();
    }

    void getMovieDetail(Long movie_id, GetMovieDetailListener getMovieDetailListener);
}
