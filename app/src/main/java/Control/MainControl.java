package Control;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.anwar.magineproject.MainActivity;

import Fragments.NetworkDialoge;

/**
 * Created by Anwar on 18-Oct-16.
 * class to holde the internet connection
 */

public class MainControl {
    private final Context context;

    public MainControl(Activity context) {
        this.context = context;
    }

    public boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        // ARE WE CONNECTED TO THE NET
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected();
        } else
            return false;
    }

}

