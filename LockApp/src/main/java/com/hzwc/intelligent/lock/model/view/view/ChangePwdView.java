package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public interface ChangePwdView extends BaseMvpView {
    void dataLoading();
    void dataSuccess(BaseBean result);
    void dataFailure(String result);

}
