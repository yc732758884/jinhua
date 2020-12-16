package com.hzwc.intelligent.lock.model.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import java.io.Serializable;

/**
 * Activity工具类
 * @author apple
 * 2017-12-18
 * Anna
 */
public class ActivityUtils {
    /**
     * 启动一个Activity并关闭当前Activity
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity
     */
    public static void startActivityAndFinish(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 启动Activity
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity Class
     */
    public static void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    /**
     * 启动Activity并传int数据 key:"data"
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity Class
     * @param data     int型数据
     */
    public static void startActivityForIntData(Activity activity, Class<?> cls, int data) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("data", data);
        activity.startActivity(intent);
    }

    /**
     * 启动Activity并传String数据 key:"data"
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity Class
     * @param data     String型数据
     */
    public static void startActivityForData(Activity activity, Class<?> cls, String data) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("data", data);
        activity.startActivity(intent);
    }

    public static void startActivityForDatas(Activity activity, Class<?> cls, String data, String key,String value) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("data", data);
        intent.putExtra("key", key);
        intent.putExtra("value", value);
        activity.startActivity(intent);
    }

    /**
     * 启动Activity传String数据并接收返回结果 key:"data"
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity Class
     * @param data     String型数据
     * @param flag     int标记
     */
    public static void startActivityForResult(Activity activity, Class<?> cls, String data, int flag) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("data", data);
        intent.setFlags(flag);
        activity.startActivityForResult(intent, flag);
    }

    /**
     * 启动Activity并传序列化对象数据 key:"Serializable"
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity Class
     * @param data     String型数据
     */
    public static void startActivityForSerializable(Activity activity, Class<?> cls, Serializable data) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("Serializable", data);
        activity.startActivity(intent);
    }

    /**
     * 启动Activity并传String对象数据
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity Class
     */
    public static void startActivityForStringDatas(Activity activity, Class<?> cls, String name1,
                                                   String name2, String name3, String name4, String name5, String name6, String name7,String name8) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra("name1", name1);
        intent.putExtra("name2", name2);
        intent.putExtra("name3", name3);
        intent.putExtra("name4", name4);
        intent.putExtra("name5", name5);
        intent.putExtra("name6", name6);
        intent.putExtra("name7", name7);
        intent.putExtra("name8", name8);
        activity.startActivity(intent);
    }

    /**
     * 启动网络设置
     *
     * @param activity 当前Activity
     */
    public static void startSetNetActivity(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        activity.startActivity(intent);
    }

    /**
     * 启动系统设置
     *
     * @param activity 当前Activity
     */
    public static void startSetActivity(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }

    /**
     * 自定义Intent数据
     *
     * @param activity
     * @param intent
     */
    public static void startIntentActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }
}
