package br.ufc.caps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import java.util.ArrayList;

import br.ufc.caps.Database.BD;
import br.ufc.caps.Geofence.Local;
import br.ufc.caps.settings.BluetoothSettings;
import br.ufc.caps.settings.WifiSettings;

/**
 * Essa Activity foi criada apenas para testar as funcionalidades criadas e não deve ser usada na versão final do app
 * TODO:Modo Avião;
 * Feito: Wifi;
 * TODO:3G;
 * Feito: Bluetooth;
 * TODO:Ringtone(Ligação)
 * TODO:Vibração(Ligação)
 * TODO:Brilho de Tela
 * TODO:Wallpaper
 */
public class TestActivity extends AppCompatActivity {

    Switch switchWifi, switchBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //EXEMPLO
        BD bd = new BD(this);
        bd.adicionar(new Local(Local.ALARME,"aasaa","adsdsa","aaddf",1,1,1,1.7,1.7));
        bd.adicionar(new Local(Local.ALARME,"abshabs","abshabs","asyags",1,1,1,1.7,1.7));
        //bd.adicionar(new Local(Local.ALARME,"asa","asa","asa",1,1,1,1.7,1.7));
        ArrayList<Local> t = bd.buscar();
        bd.atualizar((new Local(t.get(0).getId(),Local.ALARME,"hausa","sjais","ansa",1,1,1,1.7,1.7)));
        Log.e("eiiiiiiiiiiiiiii",t.size()+"");
        boolean b =bd.excluir(t.get(1));
        t = bd.buscar();
        Log.e("eiiiiiiiiiiiiiii",t.size()+"");
        Log.e("eiiiiiiiiiiiiiii",b+"");
        initComponents();
        testGeofencing();
    }

    private void testGeofencing() {
        //TODO: testar Geofence
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
}
