package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.UserLockBean;
import com.hzwc.intelligent.lock.model.bean.UserRamBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 * @author
 * @date 2017/11/17
 * @description
 */
public interface UserRamView extends BaseMvpView {
    void requestLoading();
    void userRamSuccess(UserRamBean result);
    void resultFailure(String result);
}
