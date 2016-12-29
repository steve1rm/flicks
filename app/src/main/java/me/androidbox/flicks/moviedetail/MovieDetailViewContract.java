package me.androidbox.flicks.moviedetail;

/**
 * Created by steve on 10/14/16.
 */

public interface MovieDetailViewContract {
    void displayTagline(String tagline);
    void displayTitle(String title);
    void displayReleasedate(String releasedate);
    void displayMovieThumbnail(String imageUrl);
    void displayMovieBackdropPoster(String imageUri);
  //  void displayPopularity(String popularity);
    void displayOverview(String overview);
    void displayFailureMessage();
    void playMovieTrailer(String videoCode);
    void playMovieTrailerFailure();
    void displayRunningTime(int runningtime);
    void displayGetMovieRating(float rating);
}
