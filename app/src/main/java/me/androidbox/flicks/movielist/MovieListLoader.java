package me.androidbox.flicks.movielist;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.androidbox.flicks.model.Results;
import timber.log.Timber;

/**
 * Created by steve on 1/2/17.
 */
public class MovieListLoader extends Loader<List<Results>> {
    private List<Results> mData = Collections.emptyList();

    public MovieListLoader(Context context, List<Results> data) {
        super(context);
        Timber.d("MovieListLoader(context, mData)");
        mData = new ArrayList<>();

        if(data != null) {
            mData = data;
        }
    }

    @Override
    protected void onStartLoading() {
        Timber.d("onStartLoading");

        if(mData.size() > 0) {
            Timber.d("onStartLoading size: %d", mData.size());
            deliverResult(mData);
        }
        else {
            forceLoad();
        }
    }

    @Override
    public void onForceLoad() {
        Timber.d("onForceLoad");

     //   List<Results> data = new ArrayList<>();

        /* Get more data from the impresenter */
    }

    @Override
    public void deliverResult(List<Results> data) {
        Timber.d("deliverResult");

        super.deliverResult(data);
    }

}
