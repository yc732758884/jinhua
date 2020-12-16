package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 * @author
 * @date 2017/11/17
 * @description
 */
public interface InstallLockView extends BaseMvpView {
    void requestLoading();
    void RimSuccess(MarkerBean result);
    void resultFailure(String result);
}
