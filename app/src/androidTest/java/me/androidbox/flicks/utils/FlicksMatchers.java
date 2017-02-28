package me.androidbox.flicks.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import me.androidbox.flicks.R;

/**
 * Created by steve on 2/13/17.
 */

public class FlicksMatchers {
    public static Matcher<View> withTitleViewName(final String expected) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if(item != null && item.findViewById(R.id.tvMovieTitle) != null) {
                    final TextView movieTitle = (TextView)item.findViewById(R.id.tvMovieTitle);
                    if(TextUtils.isEmpty(movieTitle.getText())) {
                        return false;
                    }
                    else {
                        return movieTitle.getText().equals(expected);
                    }
                }
                else {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Looking for matching text: " + expected);
            }
        };
    }

    /**
     * Compare that the dst is the same as the src
     * @param srcId resource id that will be the text to compare with
     * @param dstId resource id that will return true if it match the src
     * @return true of false is the comparison is correct or incorrect
     */
    public static Matcher<View> compareTextView(final int srcId, final int dstId) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Compare that the resouce ids have the same text");
            }
        }
    }
}
