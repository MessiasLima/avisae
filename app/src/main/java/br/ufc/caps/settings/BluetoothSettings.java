package br.ufc.caps.settings;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

/**
 * Created by messias on 5/5/16.
 *
 * @author Messias Lima
 *         <p/>
 *         Classe responsável pelo acesso ao Bluetooth
 */
public class BluetoothSettings {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;

    /**
     * Cria objeto de manipulação do Bluetooth
     */
    public BluetoothSettings(Context context) {
        bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    /**
     * Retorna se o bluetooth está ativo
     */
    public boolean isBluetoothEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    /**
     * Ativa ou desativa o bluetooth
     *
     * @return se a operação foi realizada com sucesso
     */
    public boolean setBluetoothEnabled(boolean enabled) {
        if (enabled) {
            return bluetoothAdapter.enable();
        } else {
            return bluetoothAdapter.disable();
        }
    }
}
