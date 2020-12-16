package com.hzwc.intelligent.lock.model.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hzwc.intelligent.lock.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by 2017-12-18 09:26:23
 * @author anna
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final String TAG = BaseActivity.class.getSimpleName();
    public final float BASE_LOADING_DIALOG_BG_ALPHA = 0.8f;
    private Dialog mDialog;
    private Object cancelObject = new Object();
    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        initIntentData();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        FrameLayout container = (FrameLayout) findViewById(R.id.fl_container);
        LayoutInflater.from(this).inflate(layoutResID, container);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * 初始化intent数据
     */
    protected abstract void initIntentData();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化data
     */
    protected abstract void initData();

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @SuppressLint("ObsoleteSdkInt")
    public void showProgressDialog(Context context, String content) {
        try {
            DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_HOME
                            || keyCode == KeyEvent.KEYCODE_SEARCH;
                }
            };
            // 存在new多个dialog但是没有关闭的情况，在此判断如果有对话框正在加载则不new
            if (null != mDialog && mDialog.isShowing()) {
                return;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) { // 11
                mDialog = new AlertDialog.Builder(context).create();
            } else {
                mDialog = new AlertDialog.Builder(context, R.style.loadDialog)
                        .create();
            }
            Window window = mDialog.getWindow();
            if (null == window) {
                return;
            }
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = BASE_LOADING_DIALOG_BG_ALPHA;
            mDialog.getWindow().setAttributes(lp);
            mDialog.setOnKeyListener(keyListener);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
            // 注意此处要放在show之后 否则会报异常
            mDialog.setContentView(R.layout.loading_process_dialog_color);
            if (!TextUtils.isEmpty(content)) {
                ((TextView) mDialog.findViewById(R.id.process_dialog_content_tv)).setText(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "showProgressDialog e = " + e);
        }
    }


    public void dissmissProgressDialog() {

        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
