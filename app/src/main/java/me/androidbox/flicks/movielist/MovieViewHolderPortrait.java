package me.androidbox.flicks.movielist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.flicks.R;
import me.androidbox.flicks.di.ApplicationModule;
import me.androidbox.flicks.model.Contact;
import me.androidbox.flicks.moviedetail.DetailsActivity;
import me.androidbox.flicks.moviedetail.MovieDetailActivity;
import me.androidbox.flicks.moviedetail.MovieDetailView;
import timber.log.Timber;

import static me.androidbox.flicks.R.id.ivProfile;
import static me.androidbox.flicks.R.id.tvMovieTitle;

/**
 * Created by steve on 10/15/16.
 */

public class MovieViewHolderPortrait extends RecyclerView.ViewHolder {
    public interface GetMovieListener {
        void onGetMovie(ImageView imageView, int movieId);
    }
    private GetMovieListener mGetMovieImageListener;

    public static final String MOVIEID_KEY = "movieid_key";

    @BindView(R.id.tvMovieTitle) TextView mTvMovieTitle;
    @BindView(R.id.ivMoviePoster) ImageView mIvMoviePoster;
    @BindView(R.id.flFooterBackground) FrameLayout mFlFooterBackground;
    @BindView(R.id.vPalette) View mVPalette;

    @Inject ApplicationModule applicationModule;

    public MovieViewHolderPortrait(View itemView, final MovieListAdapter movieListAdapter, final Context context) {
        super(itemView);

//        mGetMovieImageListener = (GetMovieListener)context;
        ButterKnife.bind(MovieViewHolderPortrait.this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("MovieId: %d", movieListAdapter.getMovieId(getAdapterPosition()));
/*
                ViewCompat.setTransitionName(mIvMoviePoster,  "image_" + movieListAdapter.getMovieId(getAdapterPosition()));
                mGetMovieImageListener.onGetMovie(mIvMoviePoster, movieListAdapter.getMovieId(getAdapterPosition()));
*/

                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MOVIEID_KEY, movieListAdapter.getMovieId(getAdapterPosition()));

                /* Didn't add the title as it looks strange flying across the screen */
                /* Pair<View, String> title = Pair.create((View)mTvMovieTitle, context.getString(R.string.title_transition)); */
                Pair<View, String> image = Pair.create((View)mIvMoviePoster, context.getString(R.string.image_transition));
                Pair<View, String> background = Pair.create(mVPalette, context.getString(R.string.background_transition));

                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(
                                (Activity)context, image, background);

                context.startActivity(intent, options.toBundle());
            }

        });
    }
}
