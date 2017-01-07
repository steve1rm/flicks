package me.androidbox.flicks.movielist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
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
import me.androidbox.flicks.model.Contact;
import me.androidbox.flicks.moviedetail.DetailsActivity;
import me.androidbox.flicks.moviedetail.MovieDetailActivity;
import me.androidbox.flicks.moviedetail.MovieDetailView;
import timber.log.Timber;

import static me.androidbox.flicks.R.id.ivProfile;

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

                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(
                                (Activity)context,
                                mIvMoviePoster,
                                context.getString(R.string.image_transition));

                context.startActivity(intent, options.toBundle());

/*                final Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDetailView.EXTRA_CONTACT, new Contact("steve", R.drawable.contact_eight, "123456789"));

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(
                                (Activity)context,
                                mIvMoviePoster,
                                context.getString(R.string.image_transition));

                context.startActivity(intent, optionsCompat.toBundle());
*/
            }

        });
    }
}
