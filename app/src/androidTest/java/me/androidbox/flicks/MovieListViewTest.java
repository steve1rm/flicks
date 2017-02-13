package me.androidbox.flicks;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.androidbox.flicks.movielist.MovieListActivity;
import me.androidbox.flicks.movielist.MovieListView;
import me.androidbox.flicks.utils.RecyclerViewItemCountAssertion;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * Created by steve on 2/11/17.
 */
@RunWith(AndroidJUnit4.class)
public class MovieListViewTest {
    private IdlingResource mIdlingResource;

    @Rule public ActivityTestRule<MovieListActivity> mActivityRule = new ActivityTestRule<>(MovieListActivity.class);

    @Before
    public void registerIdlingResource() {
        MovieListView movieListView = (MovieListView)mActivityRule.getActivity().getFragmentManager().findFragmentById(R.id.activity_list_container);
        mIdlingResource = movieListView.getIdingResource();
        Espresso.registerIdlingResources(mIdlingResource);
        if(mIdlingResource != null) {
            Espresso.registerIdlingResources(mIdlingResource);
        }
    }

    @After
    public void unregisterIdlingResource() {
        if(mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

    @Test
    public void shouldLoadMoreThanZeroMovies() {
        onView(withId(R.id.rvMovieList))
                .check(new RecyclerViewItemCountAssertion(greaterThan(0)));
    }

    @Test
    public void shouldLoadDetailMovieWithCorrectTitle() {
        onView(withId(R.id.rvMovieList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        /* tvMovieTitle */
        onView(withId(R.id.tvMovieDetailTitle))
                .check(matches(withText("Rings")));

    }
}
