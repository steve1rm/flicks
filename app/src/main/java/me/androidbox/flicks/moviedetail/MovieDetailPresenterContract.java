package me.androidbox.flicks.moviedetail;

/**
 * Created by steve on 10/14/16.
 */

public interface MovieDetailPresenterContract {
    /* Presenter <<-- View */
    interface MovieDetailPresneterOps<DetailFragment> {
        void attachView(DetailFragment view);
        void detachView();
        void loadMovieDetail(int movieId);
        void loadMovieTrailer(int movieId);
    }
}
