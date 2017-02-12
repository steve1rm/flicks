package me.androidbox.flicks.utils;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by steve on 2/11/17.
 */

public class RecyclerViewItemCountAssertion implements ViewAssertion {
    private final Matcher<Integer> mMatcher;


    public RecyclerViewItemCountAssertion(int expectedCount) {
        mMatcher = is(expectedCount);
    }

    public RecyclerViewItemCountAssertion(Matcher<Integer> matcher) {
        mMatcher = matcher;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if(noViewFoundException != null) {
            throw noViewFoundException;
        }
        else {
            RecyclerView recyclerView = (RecyclerView)view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            int count = adapter.getItemCount();
            assertThat(adapter.getItemCount(), mMatcher);
        }

    }
}
