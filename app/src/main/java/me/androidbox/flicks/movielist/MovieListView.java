package me.androidbox.flicks.movielist;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.androidbox.flicks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListView extends Fragment {


    public MovieListView() {
        // Required empty public constructor
    }

    public static MovieListView getInstance() {
        return new MovieListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

}
