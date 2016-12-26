package me.androidbox.flicks.movielist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.flicks.R;
import me.androidbox.flicks.di.ApplicationModule;
import me.androidbox.flicks.moviedetail.MovieDetailActivity;
import timber.log.Timber;

/**
 * Created by steve on 10/15/16.
 */

public class MovieViewHolderPortrait extends RecyclerView.ViewHolder {
    public static final String MOVIEID_KEY = "movieid_key";

    @BindView(R.id.tvMovieTitle) TextView mTvMovieTitle;
    @BindView(R.id.ivMovieHeader) ImageView mIvMovieHeader;
    @BindView(R.id.flFooterBackground) FrameLayout mFlFooterBackground;

    @Inject ApplicationModule applicationModule;

    public MovieViewHolderPortrait(View itemView, final MovieListAdapter movieListAdapter, final Context context) {
        super(itemView);
        ButterKnife.bind(MovieViewHolderPortrait.this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("MovieId: %d", movieListAdapter.getMovieId(getAdapterPosition()));

                /* Start activity passing the movie ID */
                final Intent intent = new Intent(context, MovieDetailActivity.class)
                        .putExtra(MOVIEID_KEY, movieListAdapter.getMovieId(getAdapterPosition()));

                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                                .makeSceneTransitionAnimation((Activity)context, mIvMovieHeader, context.getString(R.string.image_transition));

                // context.startActivity(intent, activityOptionsCompat.toBundle());
                context.startActivity(intent);
            }
        });
    }
}
