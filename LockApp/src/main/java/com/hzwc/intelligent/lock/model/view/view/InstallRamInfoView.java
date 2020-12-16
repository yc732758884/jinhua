package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.AdCodeBean;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 * @author
 * @date 2017/11/17
 * @description
 */
public interface InstallRamInfoView extends BaseMvpView {
    void requestLoading();
    void installRamInfoSuccess(BaseBean result);
    void dataSuccess(AdCodeBean result);
    void resultFailure(String result);
}
