package com.hzwc.intelligent.lock.model.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2019/12/20.
 */

public class Comutil {

    public  static byte[]  string2Byte(String s){

        try {
            String[]  strs= s.split(",");
            byte[]  bytes=new byte[strs.length];
            int temp;

            for (int i = 0; i < strs.length; i++)  {
                temp=Integer.parseInt(strs[i]);
                   bytes[i]= (byte) temp;
               // bytes[i]= Byte.parseByte(Integer.toHexString(temp));
            }

            return bytes;
        }catch (Exception e){
            Log.e("awj  excaption",e.getMessage());
            return null;
        }


    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }






}
