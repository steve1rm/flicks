package me.androidbox.flicks.moviedetail;

import me.androidbox.flicks.model.MovieDetail;
import me.androidbox.flicks.model.Videos;

/**
 * Created by steve on 10/14/16.
 */

public interface MovieDetailModelContract {
    interface GetMovieDetailListener {
        void onGetMovieDetailSuccess(MovieDetail movieDetail);
        void onGetMovieDetailFailure(String errMessage);
    }

    void getMovieDetail(int movie_id, GetMovieDetailListener getMovieDetailListener);

    interface GetMovieTrailerListener {
        void onGetMovieTrailerSuccess(Videos videos);
        void onGetMovieTrailerFailure(String errMessage);
    }

    void getMovieVideo(int movie_id, GetMovieTrailerListener getMovieTrailerListener);
}
