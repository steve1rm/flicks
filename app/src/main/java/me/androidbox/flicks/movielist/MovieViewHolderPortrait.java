package me.androidbox.flicks.movielist;

import android.content.Context;
import android.content.Intent;
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
    public interface GetMovieImageListener {
        void onGetMovieImage(ImageView imageView);
    }
    private GetMovieImageListener mGetMovieImageListener;

    public static final String MOVIEID_KEY = "movieid_key";
    public static final String IMAGE_ID_KEY = "image_id_key";

    @BindView(R.id.tvMovieTitle) TextView mTvMovieTitle;
    @BindView(R.id.tvMovieOverview) TextView mTvMovieOverview;
    @BindView(R.id.ivMovieHeader) ImageView mIvMovieHeader;
    @BindView(R.id.flFooterBackground) FrameLayout mFlFooterBackground;

    @Inject ApplicationModule applicationModule;

    public MovieViewHolderPortrait(View itemView, final MovieListAdapter movieListAdapter, final Context context) {
        super(itemView);
        mGetMovieImageListener = (GetMovieImageListener)context;
        ButterKnife.bind(MovieViewHolderPortrait.this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("MovieId: %d", movieListAdapter.getMovieId(getAdapterPosition()));

                /* Start activity passing the movie ID */
                /* for the shared element transition pass in the id of the view that will be shared */
                final Intent intent = new Intent(context, MovieDetailActivity.class)
                        .putExtra(MOVIEID_KEY, movieListAdapter.getMovieId(getAdapterPosition()))
                        .putExtra(IMAGE_ID_KEY, R.id.ivMovieHeader);

                mGetMovieImageListener.onGetMovieImage(mIvMovieHeader);
                context.startActivity(intent);
            }
        });
    }
}
