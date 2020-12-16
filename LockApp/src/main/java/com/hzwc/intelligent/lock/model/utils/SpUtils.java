package com.hzwc.intelligent.lock.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 轻量级的存储类SharePregerence：本地xml文件存储软件数据参数
 * @author apple
 */
public class SpUtils {


    private static String FILE_NAME = "WangCe";
    private static SharedPreferences sp;
    //存储布尔类型
    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }
    //存储字符串类型
    public static String getString(Context context, String key,
                                   String defValue){
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public static void setString(Context context, String key, String value){
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }
    //存储数字类型
    public static int getInt(Context context, String key,
                             int defValue){
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    public static void setInt(Context context, String key, int value){
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }


    // 存储长正型的数据，较大数据
    public static long getLong(Context context, String key,
                               long defValue){
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getLong(key, defValue);
    }

    public static void setLong(Context context, String key, long value){
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).commit();
    }
    public static void clearData() {
        sp.edit().clear().commit();
    }


}
