package br.ufc.caps.Geofence;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by messias on 6/1/16.
 *
 * @author Messias Lima
 */
public class GeofencingManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static GeofencingManager instance;
    private GoogleApiClient googleApiClient;
    private Context context;
    private List<Geofence> geofenceList;

    /**
     * Restorna sempre a mesma instancia de GeofencingManager
     */
    public GeofencingManager getInstance(Context context) {
        if (instance == null) {
            instance = new GeofencingManager(context);
        }
        return instance;
    }

    private GeofencingManager(Context context){
        this.context = context;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();

        geofenceList = new ArrayList<>();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
