package br.ufc.caps.Geofence;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

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

    private String TAG = "GEOFENCING MANAGER";
    // Request code to attempt to resolve Google Play services connection failures.
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
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

    /**
     * Inicia e se connecta no Google API's
     */
    private GeofencingManager(Context context) {
        this.context = context;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();

        geofenceList = new ArrayList<>();
    }

    /**
     * Adiciona a lista de locais no listenet. A partir daqui, a API começa a monitorar os locais
     */
    public void registerGeofences() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        } else {
            LocationServices.GeofencingApi.addGeofences(googleApiClient, geofenceList, getGeofenceTransitionPendingIntent());
        }
    }

    /**
     * Adiciona o local na lista de locais
     */
    public void addGeofence(Geofence geofence) {
        geofenceList.add(geofence);
    }

    /**
     * Retira um local da lista e do Listenet
     */
    public void renoveGeofence(Geofence geofence) {
        geofenceList.remove(geofence);
        List<String> geofencesToRemove = new ArrayList<>();
        geofencesToRemove.add(geofence.getRequestId());
        LocationServices.GeofencingApi.removeGeofences(googleApiClient, geofencesToRemove);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // If the error has a resolution, start a Google Play services activity to resolve it.
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(((Activity) context), CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "Exception while resolving connection error.", e);
            }
        } else {
            int errorCode = connectionResult.getErrorCode();
            Log.e(TAG, "Connection to Google Play services failed with error code " + errorCode);
        }
    }

    /**
     * Cria uma pending intent que roda quando entramos em algum lugar
     */
    private PendingIntent getGeofenceTransitionPendingIntent() {
        Intent intent = new Intent(context, GeofenceIntentService.class);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
