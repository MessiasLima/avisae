package br.ufc.caps;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import br.ufc.caps.settings.WifiSettings;

/**
 * Essa Activity foi criada apenas para testar as funcionalidades criadas e não deve ser usada na versão final do app
 * TODO:Modo Avião;
 * TODO:Wifi;
 * TODO:3G;
 * TODO:Bluetooth;
 * TODO:Ringtone(Ligação)
 * TODO:Vibração(Ligação)
 * TODO:Brilho de Tela
 * TODO:Wallpaper
 */
public class TestActivity extends AppCompatActivity {

    Switch switchWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initComponents();
    }

    private void initComponents() {
        switchWifi = (Switch) findViewById(R.id.sw_wifi);
        final WifiSettings wifiSettings = new WifiSettings(this);
        switchWifi.setChecked(wifiSettings.isWifiEnabled());
        switchWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiSettings.setWifiEnabled(switchWifi.isChecked());
            }
        });
    }
}
