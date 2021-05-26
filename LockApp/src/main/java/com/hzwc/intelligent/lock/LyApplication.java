package com.hzwc.intelligent.lock;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.hzwc.intelligent.lock.model.activity.SettingActivity;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.MediaLoader;
import com.hzwc.intelligent.lock.model.utils.MyCrashHandler;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Locale;

import javax.crypto.NoSuchPaddingException;

import cn.jpush.android.api.JPushInterface;
import devliving.online.securedpreferencestore.DefaultRecoveryHandler;
import devliving.online.securedpreferencestore.SecuredPreferenceStore;


/**
 * 2017-12-18
 * Anna
 *
 * @author apple
 */

public class LyApplication extends Application {

    private RefWatcher refWatcher;

    private static LyApplication instance;
    // 获取到主线程的上下文
    private static LyApplication mContext = null;
    private static Context context = null;
    // 获取到主线程的handler
    private static Handler mMainThreadHandler = null;
    boolean isDebug = true;
    // 获取到主线程
    private static Thread mMainThread = null;
    // 获取到主线程的id
    private static int mMainThreadId;
    // 获取到主线程的looper
    private static Looper mMainThreadLooper = null;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        context = getApplicationContext();

        mMainThreadHandler = new Handler();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mMainThreadLooper = getMainLooper();
        LeakCanary.install(this);
        refWatcher = setupLeakCanary();



        String storeFileName = "securedStore";

        String keyPrefix = "vss";
        byte[] seedKey = "SecuredSeedData".getBytes();
        try {
            SecuredPreferenceStore.init(getApplicationContext(), new DefaultRecoveryHandler());
        } catch (Exception e){

        }
//                MyCrashHandler handler = new MyCrashHandler();
//        Thread.setDefaultUncaughtExceptionHandler(handler);
//
//        CrashReport.initCrashReport(getApplicationContext());
        String  packageName = context.getPackageName();
        String processName = getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.initDelay = 3*1000;

        Beta.canShowUpgradeActs.add(SettingActivity.class);

//        CrashReport.initCrashReport(context,"d31bc26234",isDebug,strategy);
        Bugly.init(getApplicationContext(), "6633cb6ff6", true);


        JPushInterface.setDebugMode(true);

        JPushInterface.init(this);

        if (instance == null) {
            instance = this;

            Album.initialize(AlbumConfig.newBuilder(this)
                    .setAlbumLoader(new MediaLoader())
                    .setLocale(Locale.getDefault())
                    .build()
            );
        }

        registerActivityLifecycleCallbacks(mCallbacks);
    }

    // 对外暴露上下文
    public static LyApplication getApplication() {
        return mContext;
    }

    // 对外暴露主线程的handler
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    // 对外暴露主线程
    public static Thread getMainThread() {
        return mMainThread;
    }

    // 对外暴露主线程id
    public static int getMainThreadId() {
        return mMainThreadId;
    }




    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        LyApplication leakApplication = (LyApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    public static LyApplication getInstance() {
        if (null == instance) {
            instance = new LyApplication();
        }
        return instance;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            ActivityUtils.addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
           ActivityUtils.removeActivity(activity);
        }
    };


}
