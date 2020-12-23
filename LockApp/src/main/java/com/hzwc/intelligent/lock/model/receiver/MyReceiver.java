package com.hzwc.intelligent.lock.model.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.utils.NotificationsUtils;


import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MyReceiver extends BroadcastReceiver {
    private String TAG = "MyReceiver";

    public static NotificationManager mNotificationManager;
    private Notification notification;
    private final String NOTIFICATION_CHANNEL_NAME = "Service";
    private final String NOTIFICATION_DESCRIPTION = "读取位置信息";

    private NotificationManager notificationManager = null;

    private NotificationChannel notificationChannel = null;


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationsUtils.initNotify(context, "", "", "");

        Log.e("DDZTAG","收到广播了"+Build.VERSION.SDK_INT);
        Bundle bundle = intent.getExtras();





        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
           String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.e(TAG, "[MyJPushReceiver] 接收Registration Id : " + regId);
        }
        //加一个推送管理类
        if(Build.VERSION.SDK_INT >=26){
            Log.e("DDZTAG","8.0处理了");
            NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String id=context.getPackageName();
            CharSequence name=context.getString(R.string.app_name);
            int importance=NotificationManager.IMPORTANCE_HIGH;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
                if (null == notificationManager) {
                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                }
                String channelId = "channel";
                if (notificationChannel == null) {
                    notificationChannel = new NotificationChannel(channelId,
                            NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
                    notificationChannel.setDescription(NOTIFICATION_DESCRIPTION);
                    // 设置通知出现时的闪灯（如果 android 设备支持的话）
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(0);


                    notificationManager.createNotificationChannel(notificationChannel);

                }
                // 这里 针对8.0 我们如下设置 channel
                notification = new Notification.Builder(context.getApplicationContext(),channelId)
                        .setSmallIcon(R.mipmap.ic_lock_logo)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(bundle.getString(JPushInterface.EXTRA_MESSAGE)).build();
            }else {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                mBuilder.setPriority(0);// 设置该通知优先级
                mBuilder.setSmallIcon(R.mipmap.ic_lock_logo);
                mBuilder.setContentTitle(context.getString(R.string.app_name));
                mBuilder.setContentText(bundle.getString(JPushInterface.EXTRA_MESSAGE)).build();
                notification = mBuilder.build();
            }

        }
    }



}
