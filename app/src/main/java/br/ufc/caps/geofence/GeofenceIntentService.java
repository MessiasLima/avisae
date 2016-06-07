package br.ufc.caps.geofence;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import br.ufc.caps.database.BD;
import br.ufc.caps.util.NotificationUtil;

public class GeofenceIntentService extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private String TAG = "GEOFENCING INTENT SERVICE";

    public GeofenceIntentService() {
        super(GeofenceIntentService.class.getSimpleName());
    }

    /**
     * Metdo que executa quando o usuário entra no local definido
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geoFenceEvent = GeofencingEvent.fromIntent(intent);
        if (geoFenceEvent.hasError()) {
            int errorCode = geoFenceEvent.getErrorCode();
            Log.e(TAG, "Location Services error: " + errorCode);
        } else {
            for (Geofence geofence : geoFenceEvent.getTriggeringGeofences()) {
                BD database = new BD(this);
                Local local = database.buscar(geofence.getRequestId());
                if (local.getAtivo()==Local.VERDADEIRO){
                    if (local.getAviso()==Local.NOTIFICACAO){
                        NotificationUtil.sendNotification(local.getNome(),local.getTexto(),this,local);
                    }else{

                    }
                }
            }
        }
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