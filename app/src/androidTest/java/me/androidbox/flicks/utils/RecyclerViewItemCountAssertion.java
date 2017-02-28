package me.androidbox.flicks.utils;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
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

            assertThat(adapter.getItemCount(), mMatcher);
        }
    }



    public static String getText(final Matcher<View> matcher) {
        final String[] text = new String[]{""};

        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "get text from view";
            }

            @Override
            public void perform(UiController uiController, View view) {
                final TextView textView = (TextView)view;
                text[0] = textView.getText().toString();
            }
        });

        return text[0];
    }
}
