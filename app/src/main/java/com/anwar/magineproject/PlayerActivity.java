package com.anwar.magineproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnBufferUpdateListener;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.listener.OnSeekCompletionListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import Control.MainControl;
import Fragments.NetworkDialoge;

/**
 * An activity for playing video
 * with library called EmVideo Player from Exo player
 */
public class PlayerActivity extends FragmentActivity {
    public static final String MY_PREF = "MyPREF";
    public static final String POSITION = "position";
    public static final String ISROTATED = "is_rotated";
    private EMVideoView emVideoView;
    private TextView movieName;
    private String url;
    private int i;
    private boolean isRotated;
    SharedPreferences.Editor editor;
    private MainControl mainControl;
    public static FragmentActivity playerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusColor();
        setContentView(R.layout.activity_player);
        mainControl = new MainControl(this);
        if (mainControl.checkInternetConnection()) {

        } else {
            showDialog();
        }
        movieName = (TextView) findViewById(R.id.movie_name);
        emVideoView = (EMVideoView) findViewById(R.id.video_view);
        final SharedPreferences myPref = this.getSharedPreferences(MY_PREF, 0);
        editor = myPref.edit();
        //https://archive.org/details/more_animation
        movieName.setText(getIntent().getExtras().get("movieName").toString());
        url = getIntent().getExtras().get("url").toString().replaceAll("/", "");
        try {
            //simple library for video rendering
            emVideoView.setVideoURI(Uri.parse(URLDecoder.decode(url.substring(2, url.length() - 2), "utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        emVideoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                //if there already value then seekto to it
                if (myPref.getBoolean(ISROTATED, true)) {
                    emVideoView.start();
                    emVideoView.seekTo(myPref.getInt(POSITION, 0));
                } else {
                    //else start new video
                    emVideoView.start();
                }
            }
        });

    }


    /**
     * listen to rotaion screen for saving the current position and holding it in a shared preferences
     * for retrieval in other rotations
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isRotated = true;
        i = emVideoView.getCurrentPosition();
        editor.putInt(POSITION, i).commit();
        editor.putBoolean(ISROTATED, isRotated).commit();
        i = emVideoView.getCurrentPosition();
//        emVideoView.seekTo(emVideoView.getCurrentPosition());
    }

    /**
     * network errors
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

    /**
     * a method for change status color
     */
    private void setStatusColor() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
    }
}
