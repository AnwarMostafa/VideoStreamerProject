package com.anwar.magineproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Control.JsonParser;
import Control.MainControl;
import DataBase.DataBaseAdapter;
import Fragments.ChannelListFragment;
import Fragments.NetworkDialoge;
import Model.Movies;
import Model.URLs;

public class MainActivity extends FragmentActivity {

    private SharedPreferences sharedpreferences;
    private TextView retry;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retry = (TextView) findViewById(R.id.retry);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        MainControl mainControl = new MainControl(this);
        if (mainControl.checkInternetConnection()) {
            DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(this);
            callJson();
        } else {
            showDialog();
        }

        clearShared();
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callJson();
            }
        });

//        ArrayList<Movies> mm = dataBaseAdapter.getAllMovies();
//        mm.get(0).getSourceUrl();

    }

    private void callJson() {
        progressBar.setVisibility(View.VISIBLE);
        JsonParser json = new JsonParser(this);
        json.execute();
        json.setOnUserRetrieve(new JsonParser.OnUserRetrieve() {
            @Override
            public void onFinished(boolean successfully) {
                if (successfully) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    ChannelListFragment channelListFragment = new ChannelListFragment();
                    transaction.replace(R.id.main_activity_fragment_holder, channelListFragment);
                    transaction.commit();
                    retry.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    retry.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Error please press Retry", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * onActivity result for returning from second activity
     * clear shared prefrences for resetting value to restart the values again
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        clearShared();
    }

    private void clearShared() {

        sharedpreferences = this.getSharedPreferences(PlayerActivity
                .MY_PREF, 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(PlayerActivity.POSITION, 0).commit();
//        editor.remove(PlayerActivity.POSITION);
        editor.putBoolean(PlayerActivity.ISROTATED, false).commit();

    }

    /**
     * open network dialoge if issue happpend or wifi not connected
     */
    void showDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        final NetworkDialoge commentFrag = new NetworkDialoge();
        commentFrag.show(ft, "dialog");
        commentFrag.setCancelable(false);
        commentFrag.OnDialogClickListener(new NetworkDialoge.OnDialogListener() {
            @Override
            public void onNegativeClickListener() {
                finish();
            }
        });

    }


}
