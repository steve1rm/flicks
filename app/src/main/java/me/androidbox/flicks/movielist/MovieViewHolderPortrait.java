package me.androidbox.flicks.movielist;

import android.content.Context;
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
import timber.log.Timber;

/**
 * Created by steve on 10/15/16.
 */

public class MovieViewHolderPortrait extends RecyclerView.ViewHolder {
    public interface GetMovieListener {
        void onGetMovie(int movieId);
    }
    private GetMovieListener mGetMovieImageListener;

    public static final String MOVIEID_KEY = "movieid_key";

    @BindView(R.id.tvMovieTitle) TextView mTvMovieTitle;
    @BindView(R.id.ivMoviePoster) ImageView mIvMoviePoster;
    @BindView(R.id.flFooterBackground) FrameLayout mFlFooterBackground;

    @Inject ApplicationModule applicationModule;

    public MovieViewHolderPortrait(View itemView, final MovieListAdapter movieListAdapter, final Context context) {
        super(itemView);

        mGetMovieImageListener = (GetMovieListener)context;
        ButterKnife.bind(MovieViewHolderPortrait.this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("MovieId: %d", movieListAdapter.getMovieId(getAdapterPosition()));
                mGetMovieImageListener.onGetMovie(movieListAdapter.getMovieId(getAdapterPosition()));
            }
        });
    }
}
