package br.ufc.caps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        
        //Testar Mexer no Wifi
        //WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //boolean enabled = wifiManager.isWifiEnabled();
        //Log.i("Wifi","Enabled: " + enabled);
        //wifiManager.setWifiEnabled(!enabled);
        //Log.i("Wifi", "Enabled: " + wifiManager.isWifiEnabled());


    }
}
