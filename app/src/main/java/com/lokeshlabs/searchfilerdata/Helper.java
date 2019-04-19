package com.lokeshlabs.searchfilerdata;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by rajkumar on 20/7/18.
 */

public class Helper  {

    public static  boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
