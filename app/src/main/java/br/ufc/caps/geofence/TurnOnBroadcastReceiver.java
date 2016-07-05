package br.ufc.caps.geofence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import br.ufc.caps.activity.MainActivity;
import br.ufc.caps.database.BD;

/**
 * Created by messias on 7/4/16.
 *
 * @author Messias Lima
 */
public class TurnOnBroadcastReceiver extends BroadcastReceiver implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient googleApiClient;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getSimpleName(),"Entrou no OnReceive");
        this.context = context;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(getClass().getSimpleName(),"Entrou no OnConnected");
        BD bd = new BD(context);
        MainActivity mainActivity = MainActivity.getInstance();
        mainActivity.atualizarGeofences(bd.buscar(), googleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
