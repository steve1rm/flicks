package me.androidbox.flicks.moviedetail;

/**
 * Created by steve on 10/14/16.
 */

public interface MovieDetailPresenterContract {
    /* Presenter <<-- View */
    interface MovieDetailPresneterOps<MovieDetailView> {
        void attachView(MovieDetailView view);
        void detachView();
        void loadMovieDetail(int movieId);
    }

    /* Model -->> Presenter */
    interface MovieDetailPresenterEvents {
        void onLoadMovieDetailSuccess();
        void onLoadMovieDetailFailure();
    }
}
