package com.hzwc.intelligent.lock.mvpframework.view.bezier;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.hzwc.intelligent.lock.R;


/**
 * Created by Administrator on 2017/11/22.
 */

public class DialogTool {

    private static PopupWindow popWindow;

    @SuppressLint("WrongConstant")
    public static View initPopWindow(final Activity mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_view, null, false);

        //构造一个PopupWindow，参数依次是加载的View，宽高
        popWindow = new PopupWindow(view, dip2px(mContext, 300), WindowManager.LayoutParams.WRAP_CONTENT, true);
        //设置背景透明度  0.5f
//        backgroundAlpha(0f,mContext);

        /**
         * 这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
         * 代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
         *PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
         */
        popWindow.setTouchable(true);
        popWindow.setAnimationStyle(R.style.Popup_anim_style);
        //避免输入法阻挡
        popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截拦截,PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f,mContext);
            }
        });
//        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //为popWindow设置一个背景使back键生效

        popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


//        pop = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);
        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
//        popWindow.showAsDropDown(v, 0, 0);
        popWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
        return view;
    }


    public static void close(){
        popWindow.dismiss();
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    private static void backgroundAlpha(float bgAlpha, Activity context) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }
}
