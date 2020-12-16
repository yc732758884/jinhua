package com.hzwc.intelligent.lock.model.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.LatLng;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 *
 * @author Administrator
 * @date 2017/7/6
 */

public class FunctionUtils {

    /**
     * 暴力点击按钮
     */
    private static long lastClickTime;
    /**
     * 防暴力点击
     *
     * @return
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }



    /**
     * 版本名
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
    //去空格等
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete(); // 删除所有文件
            } else if (file.isDirectory()) {
                deleteDirWihtFile(file); // 递规的方式删除文件夹
            }
        }
        dir.delete();// 删除目录本身
    }

    public static boolean fileIsExists(String path){
        try{
            File f=new File(path);
            if(!f.exists()){
                return false;
            }

        }catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    public static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            LogUtils.e("文件不存在！"+"\n");
        }
    }


    /**
     * 截取小数点后两位
     */

    public static String getPointEnd(double s) {
        String str = String.valueOf(s);
        String[] mStr = str.split("\\.");
        if (mStr.length > 2) {
            int i = Integer.parseInt(mStr[1].substring(0, 2));
            str = i + "kw·h";
        } if (mStr.length == 2) {
            int i = Integer.parseInt(mStr[1].substring(0, 1));
            str = i + "kw·h";
        }else if (mStr.length == 0) {
            int i = Integer.parseInt(mStr[1].substring(0, 1));
            str = 0 + "kw·h";
        } else if (mStr.length == 1) {
            int i = Integer.parseInt(mStr[1].substring(0, 1));
            str = i + "kw·h";
        }
        return str;
    }
//    public static String getVersionName(Context context)
//    {
//        // 获取packagemanager的实例
//        PackageManager packageManager = context.getPackageManager();
//        // getPackageName()是你当前类的包名，0代表是获取版本信息
//        PackageInfo packInfo = null;
//        try {
//            packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        String version = packInfo.versionName;
//        return version;
//    }
    public static String getVersionName(Activity activity){
        // 获取packagemanager的实例
        PackageManager packageManager = activity.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(activity.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }


    /**
     * 判断高德地图app是否已经安装
     */
    public boolean getAppIn(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    "com.autonavi.minimap", 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        // 本手机没有安装高德地图app
        if (packageInfo != null) {
            return true;
        }
        // 本手机成功安装有高德地图app
        else {
            return false;
        }
    }


    /**
     * by moos on 2017/09/05
     * func:获取屏幕中心的经纬度坐标
     *
     * @return
     */
    public LatLng getMapCenterPoint(MapView mapView) {
        int left = mapView.getLeft();
        int top = mapView.getTop();
        int right = mapView.getRight();
        int bottom = mapView.getBottom();
        // 获得屏幕点击的位置
        int x = (int) (mapView.getX() + (right - left) / 2);
        int y = (int) (mapView.getY() + (bottom - top) / 2);
        AMap aMap = mapView.getMap();
        Projection projection = aMap.getProjection();
        LatLng pt = projection.fromScreenLocation(new Point(x, y));

        return pt;
    }
}
