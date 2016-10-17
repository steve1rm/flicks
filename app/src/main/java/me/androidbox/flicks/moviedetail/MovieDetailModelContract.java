package me.androidbox.flicks.moviedetail;

import me.androidbox.flicks.model.MovieDetail;

/**
 * Created by steve on 10/14/16.
 */

public interface MovieDetailModelContract {
    interface GetMovieDetailListener {
        void onGetMovieDetailSuccess(MovieDetail movieDetail);
        void onGetMovieDetailFailure(String errMessage);
    }

    void getMovieDetail(int movie_id, GetMovieDetailListener getMovieDetailListener);
}
