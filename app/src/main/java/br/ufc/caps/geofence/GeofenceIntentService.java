package br.ufc.caps.geofence;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.Calendar;

import br.ufc.caps.activity.LocalDetail;
import br.ufc.caps.database.BD;
import br.ufc.caps.util.NotificationUtil;

public class GeofenceIntentService extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private String TAG = "GEOFENCING INTENT SERVICE";
    private int hour;
    private int minute;

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
                if (local != null && local.getAtivo() == Local.VERDADEIRO) {
                    if (isDentroDoHorario(local)) {
                        notificar(local);
                        local.setAtivo(Local.FALSO);
                        try {
                            database.atualizar(local);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    public void notificar(Local local) {
        if (local.getAviso() == Local.NOTIFICACAO) {
            NotificationUtil.sendNotification(local.getNome(), local.getTexto(), this, local);
        } else {
            Intent intentClock = new Intent(AlarmClock.ACTION_SET_ALARM);
            intentClock.putExtra(AlarmClock.EXTRA_MESSAGE, local.getTexto());
            intentClock.putExtra(AlarmClock.EXTRA_HOUR, hour);
            intentClock.putExtra(AlarmClock.EXTRA_MINUTES, minute + 1);
            intentClock.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
            intentClock.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent intentDetail = new Intent(this, LocalDetail.class);
            intentDetail.putExtra(Local.KEY, local);
            intentDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intentClock);
            Log.i("Intent Service", "Disparou Relogio");
            startActivity(intentDetail);
            Log.i("Intent Service", "Disparou Activity");
        }

    }

    private boolean isDentroDoHorario(Local local) {
        hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        minute = Calendar.getInstance().get(Calendar.MINUTE);
        if (local.getTempo().equals("--")) {
            return true;
        } else {
            String[] hours = local.getTempo().split(";");
            int initialHour = Integer.parseInt(hours[0].split(":")[0]);
            int initialMinute = Integer.parseInt(hours[0].split(":")[1]);
            if (hour >= initialHour && minute >= initialMinute) {
                int finalHour = Integer.parseInt(hours[1].split(":")[0]);
                int finalMinute = Integer.parseInt(hours[1].split(":")[1]);
                if (hour <= finalHour && minute <= finalMinute) {
                    return true;
                }
            }
            return false;

        }
    }
}
