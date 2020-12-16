package com.hzwc.intelligent.lock.model.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hzwc.intelligent.lock.R;


/**
 * Created by 2017-12-18 09:26:01
 * @author Anna
 */
public abstract class BaseFragment extends Fragment {
    protected View rootView;
    public Context mContext;
    private Dialog mDialog;
    public final float BASE_LOADING_DIALOG_BG_ALPHA = 0.8f;
    private final String TAG = "BaseFragment";
    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(getLayoutId(), container, false);

            findViews();
            setupListener();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
    }

    /**
     * @return layoutId
     */
    protected abstract int getLayoutId();

    /**
     * initView
     */
    protected abstract void findViews();

    /**
     * setListener
     */
    protected abstract void setupListener();

    protected final View findViewById(@IdRes int id) {
        return rootView.findViewById(id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
