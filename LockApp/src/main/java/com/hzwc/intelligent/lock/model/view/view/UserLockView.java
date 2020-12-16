package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.bean.UserLockBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 * @author
 * @date 2017/11/17
 * @description
 */
public interface UserLockView extends BaseMvpView {
    void requestLoading();
    void userLockSuccess(UserLockBean result);
    void resultFailure(String result);
}
