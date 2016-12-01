package me.androidbox.flicks.utils;

/**
 * Created by steve on 11/30/16.
 */

public final class ImageBuilder {

    public static String buildImagePath(int size, String posterPath) {
                /* Build image path to display associated image */
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.CONFIGURATION);
        stringBuilder.append(Constants.W185);
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
