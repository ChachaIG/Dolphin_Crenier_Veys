package com.henallux.dolphin_crenier_veys.InternetConnection;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.henallux.dolphin_crenier_veys.exception.ConnexionException;

public class VerificationConnexionInternet  {
    
    public static boolean estConnecteAInternet(Activity activity) throws ConnexionException {
        ConnectivityManager connexion = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connexion.getActiveNetworkInfo();
        if (info != null) {
            NetworkInfo.State networkState = info.getState();
            if (networkState.compareTo(NetworkInfo.State.CONNECTED) == 0) {

                return true;
            } else {
                throw new ConnexionException(activity);

            }
        }
        throw new ConnexionException(activity);

    }
}