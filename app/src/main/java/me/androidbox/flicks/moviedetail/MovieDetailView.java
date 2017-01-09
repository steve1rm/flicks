package me.androidbox.flicks.moviedetail;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.androidbox.flicks.R;
import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.movielist.MovieViewHolderPortrait;
import me.androidbox.flicks.utils.Constants;
import me.androidbox.flicks.utils.ImageBuilder;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailView extends Fragment implements MovieDetailViewContract {
    public static final String TAG = MovieDetailView.class.getSimpleName();

    private static final String MOVIEID_KEY = "movieid_key";

    @Inject MovieDetailPresenterImp mMovieDetailPresenterImp;

    @BindView(R.id.tvMovieTitle) TextView mTvMovieTitle;
    @BindView(R.id.tvReleaseDate) TextView mTvReleaseDate;
    @BindView(R.id.tvMovieOverview) TextView mTvMovieOverview;
    @BindView(R.id.ivBackdropPoster) ImageView mIvBackdropPoster;
    @BindView(R.id.tvRunningTime) TextView mTvRunningTime;
    @BindView(R.id.rbMovieRatings) RatingBar mRbMovieRatings;
    @BindView(R.id.ivDetailThumbnail) ImageView mIvDetailThumbnail;
    @BindView(R.id.vPalette) View mVPalette;

    private Unbinder mUnbinder;
    private int mMovieId;
    private YouTubePlayerFragment mYouTubePlayerFragment;

    public MovieDetailView() {
        // Required empty public constructor
    }

    public static MovieDetailView getNewInstance(int data) {
        MovieDetailView movieDetailView = new MovieDetailView();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIEID_KEY, data);
        movieDetailView.setArguments(bundle);

        return movieDetailView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.movie_detail_view, container, false);
        mUnbinder = ButterKnife.bind(MovieDetailView.this, view);

        if(getArguments() != null) {
            Bundle bundle = getArguments();
            mMovieId = bundle.getInt(MovieViewHolderPortrait.MOVIEID_KEY, -1);
            Timber.d("movieId: %d", mMovieId);

            DaggerInjector.getsAppComponent().inject(MovieDetailView.this);

            if(mMovieDetailPresenterImp != null) {
                Timber.d("mMovieDetailPresenterImp != null");
                mMovieDetailPresenterImp.attachView(MovieDetailView.this);
                if(mMovieId != -1) {
                /* Ask the presenter to get the movie detail */
                    mMovieDetailPresenterImp.loadMovieDetail(mMovieId);
                }
            }
        }

        return view;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.ivPlayMediaImage)
    public void playTrailer() {
        mMovieDetailPresenterImp.loadMovieTrailer(mMovieId);
    }

    /* Setup youtube fragment in container */
    private void setupYoutube() {
        mYouTubePlayerFragment = YouTubePlayerFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.youtubePlayerFragment, mYouTubePlayerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void playMovieTrailer(final String videoCode) {
        setupYoutube();

        mYouTubePlayerFragment.initialize(Constants.YOUTUBE_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Timber.d("onInitializationSuccess %s", videoCode);
                youTubePlayer.loadVideo(videoCode);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Timber.e("Youtube initialization failure: %s is recoverable: %b", youTubeInitializationResult.name(), youTubeInitializationResult.isUserRecoverableError());
            }
        });
    }

    @Override
    public void playMovieTrailerFailure() {
        Toast.makeText(getActivity(), "Failed to get video trailer", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayOverview(String overview) {
        mTvMovieOverview.setText(overview);
    }

    private void scheduledStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                ActivityCompat.startPostponedEnterTransition(getActivity());
                Timber.d("scheduledStartPostponedTransition");
                return true;
            }
        });
    }

    @Override
    public void displayMovieThumbnail(String imageUrl) {

        final Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mIvDetailThumbnail.setImageBitmap(bitmap);
                    scheduledStartPostponedTransition(mIvDetailThumbnail);

                    Palette.from(bitmap).maximumColorCount(6).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                            if(swatch != null) {
                                mVPalette.setBackgroundColor(swatch.getRgb());
                                mTvMovieTitle.setTextColor(swatch.getTitleTextColor());
                                mTvMovieOverview.setTextColor(swatch.getBodyTextColor());
                                mTvReleaseDate.setTextColor(swatch.getTitleTextColor());
                                mTvRunningTime.setTextColor(swatch.getTitleTextColor());
                            }
                        }
                    });
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            mVPalette.setTag(target);
            Picasso.with(getActivity()).load(ImageBuilder.buildImagePath(Constants.W92, imageUrl)).into(target);

/*
            mSdvMovieDetailThumbnail.
                    setImageURI(ImageBuilder.buildImagePath(Constants.W92, imageUrl));
*/
    }

    @Override
    public void displayMovieBackdropPoster(String imageUri) {
        Glide.with(getActivity())
                .load(ImageBuilder.buildImagePath(Constants.W300, imageUri))
                .into(mIvBackdropPoster);
    }

    @Override
    public void displayTagline(String tagline) {

    }

    @Override
    public void displayTitle(String title) {
        mTvMovieTitle.setText(title);
    }

    @Override
    public void displayGetMovieRating(float rating) {
        mRbMovieRatings.setRating(rating);
    }

    @Override
    public void displayReleasedate(String releasedate) {
        SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputDate = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());

        try {
            Date date = inputDate.parse(releasedate);
            mTvReleaseDate.setText(outputDate.format(date));
        }
        catch(ParseException ex) {
            mTvReleaseDate.setText(releasedate);
        }
    }

    @Override
    public void displayRunningTime(int runningtime) {
        String minutes = String.valueOf(runningtime).concat(" mintues");
        mTvRunningTime.setText(minutes);
    }

    @Override
    public void displayFailureMessage() {
        Toast.makeText(getActivity(), "Failed to get movie details", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mMovieDetailPresenterImp.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
