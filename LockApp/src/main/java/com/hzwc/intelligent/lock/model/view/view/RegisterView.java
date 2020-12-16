package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.RegisterBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public interface RegisterView extends BaseMvpView {
    void dataLoading();
    void sendMessageSuccess(RegisterBean result);
    void verificationSuccess(BaseBean result);
    void dataFailure(String result);

}
