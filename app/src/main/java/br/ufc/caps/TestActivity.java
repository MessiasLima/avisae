package br.ufc.caps;

import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import br.ufc.caps.database.BD;
import br.ufc.caps.geofence.GeofencingManager;
import br.ufc.caps.geofence.Local;
import br.ufc.caps.settings.BluetoothSettings;
import br.ufc.caps.settings.WifiSettings;

public class TestActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Switch switchWifi, switchBT;
    GoogleApiClient googleApiClient;

    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "TEST ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        googleApiClient.connect();

        //testDatabase();
        initComponents();
    }

    private void testDatabase() {
        //EXEMPLO
        BD bd = new BD(this);
        // bd.adicionar(new Local(Local.ALARME, "aasaa", "adsdsa", "aaddf", 1, 1, 1, 1.7, 1.7));
        // bd.adicionar(new Local(Local.ALARME, "abshabs", "abshabs", "asyags", 1, 1, 1, 1.7, 1.7));
        //bd.adicionar(new Local(Local.ALARME,"asa","asa","asa",1,1,1,1.7,1.7));
        ArrayList<Local> t = bd.buscar();

        for (Local l : t) {
            Log.e(TAG, l.getId() + l.getNome());
        }

        //bd.atualizar((new Local(t.get(0).getId(), Local.ALARME, "hausa", "sjais", "ansa", 1, 1, 1, 1.7, 1.7)));
        Log.e("eiiiiiiiiiiiiiii", t.size() + "");
        boolean b=true;
        try{
             b = bd.excluir(t.get(1));
        }catch (Exception e){

        }
        t = bd.buscar();
        Log.e("eiiiiiiiiiiiiiii", t.size() + "");
        Log.e("eiiiiiiiiiiiiiii", b + "");
    }

    private void testGeofencing() {
        //TODO: testar Geofence
        //Local local = new Local(Local.NOTIFICACAO, "15;17", "Residencia Universitaria", "Deu certo fi", Local.FALSO, Local.VERDADEIRO, 60, -3.739984, -38.569949);
        Local local = new Local();
        local.setAviso(Local.ALARME);
        local.setTempo("15;17");
        local.setNome("Residencia Universitaria");
        local.setTexto("Testing... 123");
        local.setFavorito(Local.FALSO);
        local.setAtivo(Local.VERDADEIRO);
        local.setRaio(60f);
        local.setLatitude(-3.739984d);
        local.setLongitude(-38.569949d);


        BD bd = new BD(this);
        if (bd.buscar(local.getNome()) == null) {
            try{
                bd.adicionar(local);
            }catch (Exception e){

            }
        }

        Geofence localGeofence = local.getGeofence();
        GeofencingManager geofencingManager = GeofencingManager.getInstance(this, googleApiClient);
        geofencingManager.addGeofence(localGeofence);
        geofencingManager.registerGeofences();
    }


    private void initComponents() {

        //Controle do Wifi
        switchWifi = (Switch) findViewById(R.id.sw_wifi);
        final WifiSettings wifiSettings = new WifiSettings(this);
        switchWifi.setChecked(wifiSettings.isWifiEnabled());
        switchWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiSettings.setWifiEnabled(switchWifi.isChecked());
            }
        });

        //Controles do Bluetooth
        switchBT = (Switch) findViewById(R.id.sw_bt);
        final BluetoothSettings bluetoothSettings = new BluetoothSettings(this);
        switchBT.setEnabled(bluetoothSettings.isBluetoothAvailable());
        if (bluetoothSettings.isBluetoothAvailable()) {
            switchBT.setChecked(bluetoothSettings.isBluetoothEnabled());
            switchBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bluetoothSettings.setBluetoothEnabled(switchBT.isChecked());
                }
            });
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        testGeofencing();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // If the error has a resolution, start a Google Play services activity to resolve it.
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "Exception while resolving connection error.", e);
            }
        } else {
            int errorCode = connectionResult.getErrorCode();
            Log.e(TAG, "Connection to Google Play services failed with error code " + errorCode);
        }
    }
}
