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

import org.joda.time.LocalTime;

import java.util.Calendar;

import br.ufc.caps.R;
import br.ufc.caps.activity.LocalDetail;
import br.ufc.caps.database.BD;
import br.ufc.caps.util.NotificationUtil;

public class GeofenceIntentService extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private String TAG = "GEOFENCING SERVICE";
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
            Log.e(TAG, "error: " + errorCode);
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
            Intent intentDetail = new Intent(this, LocalDetail.class);
            intentDetail.putExtra(Local.KEY, local);
            intentDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentDetail);
        }

    }

    private boolean isDentroDoHorario(Local local) {
        if (local.getTempo().equals("--")) {
            hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            minute = Calendar.getInstance().get(Calendar.MINUTE);
            return true;
        } else {
            String[] hours = local.getTempo().split(";");
            int initialHour = Integer.parseInt(hours[0].split(":")[0]);
            int initialMinute = Integer.parseInt(hours[0].split(":")[1]);
            int finalHour = Integer.parseInt(hours[1].split(":")[0]);
            int finalMinute = Integer.parseInt(hours[1].split(":")[1]);

            hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            minute = Calendar.getInstance().get(Calendar.MINUTE);

            LocalTime now = new LocalTime(hour, minute);
            LocalTime initialTime = new LocalTime(initialHour, initialMinute);
            LocalTime finalTime = new LocalTime(finalHour, finalMinute);
            LocalTime endDay = new LocalTime(23, 59);
            LocalTime beginDay = new LocalTime(0, 0);

            if (finalTime.isAfter(initialTime)) {
                return isBetweenSimpleComparison(initialTime, finalTime, now);
            } else {
                boolean b = (isBetweenSimpleComparison(initialTime, endDay, now) || isBetweenSimpleComparison(beginDay, finalTime, now));
                return b;
            }
        }
    }

    private boolean isBetweenSimpleComparison(LocalTime initialTime, LocalTime finalTime, LocalTime actualTime) {
        if (actualTime.isAfter(initialTime) && actualTime.isBefore(finalTime)) {
            return true;
        }
        return false;
    }

}
