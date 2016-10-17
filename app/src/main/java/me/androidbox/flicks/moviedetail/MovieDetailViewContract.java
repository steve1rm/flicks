package me.androidbox.flicks.moviedetail;

/**
 * Created by steve on 10/14/16.
 */

public interface MovieDetailViewContract {
    void displayTagline(String tagline);
    void displayTitle(String title);
    void displayReleasedate(String releasedate);
  //  void displayPopularity(String popularity);
    void displayOverview(String overview);
    void displayFailureMessage();
}
