package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 * @author
 * @date 2017/11/17
 * @description
 */
public interface InstallInfoLockView extends BaseMvpView {
    void requestLoading();
    void installLockInfoSuccess(BaseBean result);
    void resultFailure(String result);
}
