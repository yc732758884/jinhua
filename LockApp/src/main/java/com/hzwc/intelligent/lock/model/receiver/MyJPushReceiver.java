package com.hzwc.intelligent.lock.model.receiver;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.model.activity.LoginActivity;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;

import java.security.acl.LastOwnerException;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyJPushReceiver extends JPushMessageReceiver {

    public MyJPushReceiver() {
        super();
    }

    private  Context context;

    public Handler handler=new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==1){
               ActivityUtils.finnishAll();
                SpUtils.setBoolean(context, "isLogin", false);
                Intent intent =new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }

            if (msg.what==2){

            }
        }
    };

    @Override
    public Notification getNotification(Context context, NotificationMessage notificationMessage) {
        return super.getNotification(context, notificationMessage);

    }

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        this.context=context;
        loginout(context);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {

        super.onNotifyMessageOpened(context, notificationMessage);
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {

        super.onNotifyMessageArrived(context, notificationMessage);




    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageDismiss(context, notificationMessage);
    }

    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);

    }

    @Override
    public void onConnected(Context context, boolean b) {

        super.onConnected(context, b);

    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        super.onCommandResult(context, cmdMessage);
    }

     public  void   loginout(Context context){

         Retrofit retrofit = new Retrofit.Builder()
                 .addConverterFactory(GsonConverterFactory.create())
                 .baseUrl(ConstantUrl.PUBLIC_URL)
                 .build();

         HttpService service = retrofit.create(HttpService.class);

         Call<BaseBean> call = service.loginout(SpUtils.getString(context, "token", ""));

         call.enqueue(new Callback<BaseBean>() {
             @Override
             public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {


                 handler.sendEmptyMessage(1);

                 //ToastUtil.show(context,new Gson().toJson(call)+"~~~~");

             }

             @Override
             public void onFailure(Call<BaseBean> call, Throwable t) {
                 handler.sendEmptyMessage(2);

             }
         });


     }



}
