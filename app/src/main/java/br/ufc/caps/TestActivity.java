package br.ufc.caps;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

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

        initComponents();
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
