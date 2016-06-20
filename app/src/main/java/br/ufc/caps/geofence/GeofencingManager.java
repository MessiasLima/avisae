package br.ufc.caps.geofence;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

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
public class GeofencingManager {

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
    public static GeofencingManager getInstance(Context context, GoogleApiClient googleApiClient) {
        if (instance == null) {
            instance = new GeofencingManager(context, googleApiClient);
        }
        return instance;
    }

    /**
     * Inicia e se connecta no Google API's
     */
    private GeofencingManager(Context context, GoogleApiClient googleApiClient) {
        this.context = context;
        this.googleApiClient = googleApiClient;
        geofenceList = new ArrayList<>();
    }

    /**
     * Adiciona a lista de locais no listenet. A partir daqui, a API começa a monitorar os locais
     */
    public void registerGeofences() {
        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
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
    public void removeGeofence(Geofence geofence) {
        geofenceList.remove(geofence);
        List<String> geofencesToRemove = new ArrayList<>();
        geofencesToRemove.add(geofence.getRequestId());
        LocationServices.GeofencingApi.removeGeofences(googleApiClient, geofencesToRemove);
    }


    /**
     * Cria uma pending intent que roda quando entramos em algum lugar
     */
    private PendingIntent getGeofenceTransitionPendingIntent() {
        Intent intent = new Intent(context, GeofenceIntentService.class);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
