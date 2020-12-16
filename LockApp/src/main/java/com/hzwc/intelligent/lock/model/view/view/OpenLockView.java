package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.UnlocksBean;
import com.hzwc.intelligent.lock.model.bean.Update;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public interface OpenLockView extends BaseMvpView {
    void dataLoading();
    void unLockSuccess(UnlocksBean result);
    void unlockApplySuccess(BaseBean result);
    void onSearchStarted();
    void onDeviceFounded(String lockNo, boolean isLock);
    void onSearchStopped();
    void dataFailure(String result);


    void    onUpdate(Update update);

}
