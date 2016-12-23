package me.androidbox.flicks.moviedetail;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
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

    @Inject MovieDetailPresenterImp mMovieDetailPresenterImp;

    @BindView(R.id.tvMovieTitle) TextView mTvMovieTitle;
    @BindView(R.id.tvReleaseDate) TextView mTvReleaseDate;
    @BindView(R.id.tvMovieOverview) TextView mTvMovieOverview;
    @BindView(R.id.ivMovieDetailThumbnail) ImageView mIvMovieDetailThumbnail;
    @BindView(R.id.ivBackdropPoster) ImageView mIvBackdropPoster;
    @BindView(R.id.tvRunningTime) TextView mTvRunningTime;

    private Unbinder mUnbinder;
    private int mMovieId;
    private YouTubePlayerFragment mYouTubePlayerFragment;

    public MovieDetailView() {
        // Required empty public constructor
    }

    public static MovieDetailView getNewInstance(int data) {
        MovieDetailView movieDetailView = new MovieDetailView();
        Bundle bundle = new Bundle();
        bundle.putInt(MovieViewHolderPortrait.MOVIEID_KEY, data);
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
        }

    //    setupYoutube();

        return view;
    }

    /* Setup youtube fragment in container */
    private void setupYoutube() {
        mYouTubePlayerFragment = YouTubePlayerFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.youtubePlayerFragment, mYouTubePlayerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerInjector.getsAppComponent().inject(MovieDetailView.this);

        if(mMovieDetailPresenterImp != null) {
            Timber.d("mMovieDetailPresenterImp != null");
            mMovieDetailPresenterImp.attachView(MovieDetailView.this);
            if(mMovieId != -1) {
                /* Ask the presenter to get the movie detail */
                mMovieDetailPresenterImp.loadMovieDetail(mMovieId);
         //       mMovieDetailPresenterImp.loadMovieTrailer(mMovieId);
            }
        }
    }

    @Override
    public void playMovieTrailer(final String videoCode) {
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

    @Override
    public void displayMovieThumbnail(String imageUrl) {
        Glide.with(getActivity())
                .load(ImageBuilder.buildImagePath(Constants.W92, imageUrl))
                .bitmapTransform(new RoundedCornersTransformation(getActivity(), 8, 0))
                .into(mIvMovieDetailThumbnail);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
//                getActivity().supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
