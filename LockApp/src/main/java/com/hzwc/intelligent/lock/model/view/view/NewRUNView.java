package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.MessageBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public interface NewRUNView extends BaseMvpView {
    void dataLoading();
    void dataSuccess(MessageBean result);
    void dataFailure(String result);

}
