package com.hzwc.intelligent.lock.model.bluetooth;

import com.hzwc.intelligent.lock.LyApplication;
import com.inuker.bluetooth.library.BluetoothClient;

public class ClientManager {

    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(LyApplication.getApplication());
                }
            }
        }
        return mClient;
    }
}