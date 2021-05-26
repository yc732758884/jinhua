package com.hzwc.intelligent.lock.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hzwc.intelligent.lock.LyApplication;

import devliving.online.securedpreferencestore.SecuredPreferenceStore;

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
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        return  prefStore.getBoolean(key,defValue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }


        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        prefStore.edit().putBoolean( key ,value).apply();


    }
    //存储字符串类型
    public static String getString(Context context, String key,
                                   String defValue){
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();


        return  prefStore.getString(key,defValue);
    }

    public static void setString(Context context, String key, String value){
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        prefStore.edit().putString( key ,value).apply();
    }
    //存储数字类型
    public static int getInt(Context context, String key,
                             int defValue){
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        return prefStore.getInt(key,defValue);
    }

    public static void setInt(Context context, String key, int value){
        if(sp==null){
            sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        prefStore.edit().putInt( key ,value).apply();
    }










}
