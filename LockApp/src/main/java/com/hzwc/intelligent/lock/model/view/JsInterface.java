package com.hzwc.intelligent.lock.model.view;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by anna on 2018/6/5.
 */

class JsInterface {
    private Context mContext;

    public JsInterface(Context context) {
        this.mContext = context;
    }

    /**
     * 在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
     * 此方法名称一定要和js中showInfoFromJava方法一样
     *
     * @param name
     */
    @JavascriptInterface
    public void showInfoFromJs(String name) {
        Toast.makeText(mContext, "来自js的信息:" + name, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showInfoFromJs(){
        Toast.makeText(mContext, "JS调用App方法", Toast.LENGTH_SHORT).show();
    }
}
