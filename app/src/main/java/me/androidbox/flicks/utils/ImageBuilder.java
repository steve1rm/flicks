package me.androidbox.flicks.utils;

import static me.androidbox.flicks.utils.Constants.W185;
import static me.androidbox.flicks.utils.Constants.W300;

/**
 * Created by steve on 11/30/16.
 */

public final class ImageBuilder {

    public static String buildImagePath(String size, String posterPath) {
                /* Build image path to display associated image */
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.CONFIGURATION);

        if(size.equalsIgnoreCase(Constants.W92)) {
            stringBuilder.append(Constants.W92);
        }
        else if(size.equalsIgnoreCase(Constants.W185)) {
            stringBuilder.append(W185);
        }
        else if(size.equalsIgnoreCase(Constants.W300)) {
            stringBuilder.append(W300);
        }

        stringBuilder.append(posterPath);

        return stringBuilder.toString();
    }


/*
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Constants.CONFIGURATION);
    stringBuilder.append(Constants.W185);
    stringBuilder.append(mMoviesList.get(position).getResults().get(0).getPoster_path());
*/

}
