package com.hzwc.intelligent.lock.model.bluetooth;

import java.util.UUID;

/**
 * Created by Administrator on 2018/7/10.
 */

public class BluetoothData {
    public static final String MAC = "0C:B2:B7:69:02:12";
    public static final UUID RX_SERVICE_UUID = UUID.fromString("6e400007-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID TX_CHAR_UUID = UUID.fromString("6e400008-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID RX_CHAR_UUID = UUID.fromString("6e400009-b5a3-f393-e0a9-e50e24dcca9e");

    public static final UUID Read_UUID0 = UUID.fromString("000036f6-0000-1000-8000-00805f9b34fb");//回调广播
    public static final UUID Write_UUID = UUID.fromString("000036f5-0000-1000-8000-00805f9b34fb");//写入
    public static final UUID Service_UUID = UUID.fromString("0000fee7-0000-1000-8000-00805f9b34fb");//服务
    public static final String PassWord = "666666";
}
