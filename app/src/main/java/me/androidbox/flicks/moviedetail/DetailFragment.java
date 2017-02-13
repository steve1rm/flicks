package me.androidbox.flicks.moviedetail;



import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayerFragment;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.flicks.R;
import me.androidbox.flicks.movielist.MovieViewHolderPortrait;
import me.androidbox.flicks.utils.Constants;
import me.androidbox.flicks.utils.ImageBuilder;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements MovieDetailViewContract {
    private static final String TAG = DetailFragment.class.getSimpleName();

    private static final String MOVIEID_KEY = "movieid_key";

    @Inject MovieDetailPresenterImp mMovieDetailPresenterImp;

    @BindView(R.id.tvMovieDetailTitle) TextView mTvMovieTitle;
    @BindView(R.id.tvReleaseDate) TextView mTvReleaseDate;
    @BindView(R.id.tvMovieOverview) TextView mTvMovieOverview;
    @BindView(R.id.ivBackdropPoster) ImageView mIvBackdropPoster;
    @BindView(R.id.tvRunningTime) TextView mTvRunningTime;
    @BindView(R.id.rbMovieRatings) RatingBar mRbMovieRatings;
    @BindView(R.id.ivDetailThumbnail) ImageView mSdvMovieDetailThumbnail;

    private Unbinder mUnbinder;
    private int mMovieId;
    private YouTubePlayerFragment mYouTubePlayerFragment;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int data) {
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIEID_KEY, data);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.movie_detail_view, container, false);

        mUnbinder = ButterKnife.bind(DetailFragment.this, view);

        if(getArguments() != null) {
            Bundle bundle = getArguments();
            mMovieId = bundle.getInt(MovieViewHolderPortrait.MOVIEID_KEY, -1);
            Timber.d("movieId: %d", mMovieId);
        }

       // DaggerInjector.getsAppComponent().inject(DetailFragment.this);

        if(mMovieDetailPresenterImp != null) {
            Timber.d("mMovieDetailPresenterImp != null");
      //      mMovieDetailPresenterImp.attachView(DetailFragment.this);
            if(mMovieId != -1) {
                /* Ask the presenter to get the movie detail */
                mMovieDetailPresenterImp.loadMovieDetail(mMovieId);
            }
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void displayTagline(String tagline) {

    }

    @Override
    public void displayTitle(String title) {

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
    public void displayMovieThumbnail(String imageUrl) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().startPostponedEnterTransition();
            Picasso.with(getActivity()).load(ImageBuilder.buildImagePath(Constants.W92, imageUrl)).into(mSdvMovieDetailThumbnail);
        }
    }

    @Override
    public void displayMovieBackdropPoster(String imageUri) {

    }

    @Override
    public void displayOverview(String overview) {

    }

    @Override
    public void displayFailureMessage() {

    }

    @Override
    public void playMovieTrailer(String videoCode) {

    }

    @Override
    public void playMovieTrailerFailure() {

    }

    @Override
    public void displayRunningTime(int runningtime) {

    }

    @Override
    public void displayGetMovieRating(float rating) {

    }
}
