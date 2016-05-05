package br.ufc.caps.settings;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by messias on 5/5/16.
 *
 * @author Messias Lima
 *         <p/>
 *         Classe responsável pelo acesso ao Wifi
 */
public class WifiSettings {
    WifiManager wifiManager;

    public WifiSettings(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * Verifica se o Wifi está ativo
     */
    public boolean isWifiEnabled() {
        return wifiManager.isWifiEnabled();
    }

    /**
     * Ativa ou desativa o Wifi
     *
     * @return se a operação foi realizada com sucesso
     */
    public boolean setWifiEnabled(boolean enable) {
        return wifiManager.setWifiEnabled(enable);
    }
}
