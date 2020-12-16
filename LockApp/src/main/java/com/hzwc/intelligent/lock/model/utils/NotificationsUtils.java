package com.hzwc.intelligent.lock.model.utils;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.hzwc.intelligent.lock.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Administrator on 2017/5/9.
 */
public class NotificationsUtils {
    /**
     * Notification构造器
     */
    public static NotificationCompat.Builder mBuilder;
    /**
     * Notification管理
     */
    public static NotificationManager mNotificationManager;
    /**
     * Notification的ID
     */
//    static int notifyId = (int) System.currentTimeMillis();

    /**
     * 初始化通知栏
     */

    public static void initNotify(Context context, String title, String text, String ticker) {
        mBuilder = new NotificationCompat.Builder(context, null);
        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL, context))
//				.setNumber(number)//显示数量
                .setTicker(ticker)//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
//				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);
    }

    public static PendingIntent getDefalutIntent(int flags, Context context) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(), flags);
        return pendingIntent;
    }

    /**
     * 显示通知栏
     */
    public static void showNotify(Context context, String title, String text, String ticker) {
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mBuilder.setContentTitle(title)
                .setContentText(text)
//				.setNumber(number)//显示数量
                .setTicker(ticker);//通知首次出现在通知栏，带上升动画效果的
        int notifyId = (int) System.currentTimeMillis();
        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    /** 显示通知栏点击跳转到指定Activity */
    public static void showIntentActivityNotify(Context context, String title, String text, String ticker, Class clazz){
        // Notification.FLAG_ONGOING_EVENT --设置常驻 Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
//		notification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, null);

//或者
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        builder.setAutoCancel(true)//点击后让通知将消失
                .setContentTitle(title)
                .setContentText(text)
                .setTicker(ticker);
//        mBuilder.setSound(Uri.parse("android.resource://com.hzwc.maintain/" + R.raw.xwx ));
        //点击的意图ACTION是跳转到Intent
        Intent resultIntent = new Intent(context,clazz);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        int notifyId = (int) System.currentTimeMillis();
        //notifyId 只要不一样就显示多个
        mNotificationManager.notify(notifyId, mBuilder.build());
    }


    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    public static boolean isNotificationEnabled(Context context) {

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        ApplicationInfo appInfo = context.getApplicationInfo();

        String pkg = context.getApplicationContext().getPackageName();

        int uid = appInfo.uid;

        Class appOpsClass = null; /* Context.APP_OPS_MANAGER */

        try {

            appOpsClass = Class.forName(AppOpsManager.class.getName());

            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (int)opPostNotificationValue.get(Integer.class);

            return ((int)checkOpNoThrowMethod.invoke(mAppOps,value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


}
