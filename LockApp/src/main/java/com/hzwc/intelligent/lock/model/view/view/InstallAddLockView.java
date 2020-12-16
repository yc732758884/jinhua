package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LockMacBean;
import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 * @author
 * @date 2017/11/17
 * @description
 */
public interface InstallAddLockView extends BaseMvpView {
    void requestLoading();
    void installAddLockSuccess(BaseBean result);
    void RimSuccess(MarkerBean result);
    void macSuccess(LockMacBean result);
    void resultFailure(String result);

    void installSuccess(String  mac,boolean install);
}
