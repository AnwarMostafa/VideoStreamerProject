package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.anwar.magineproject.R;

import java.util.ArrayList;

import Adapters.MoviesAdapter;
import DataBase.DataBaseAdapter;
import Model.Movies;

/**
 * Created by Anwar on 16-Oct-16.
 * Movies List holder
 * holds a list with all movies
 */

public class ChannelListFragment extends Fragment {

    private ListView moviesList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moviesList = (ListView) getActivity().findViewById(R.id.channel_list_view);
        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(getActivity());
        ArrayList<Movies> movies = dataBaseAdapter.getAllMovies();
        MoviesAdapter moviesAdapter = new MoviesAdapter(getActivity(), R.layout.list_item, movies);
        moviesList.setAdapter(moviesAdapter);
        if (movies.size() == 0) {
            Toast.makeText(getActivity(), "No movies To show", Toast.LENGTH_LONG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.channel_list, container, false);
    }
}
