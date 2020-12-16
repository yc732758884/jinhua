package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.UnlockApplyBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public interface RecordView extends BaseMvpView {
    void dataLoading();
    void dataSuccess(UnlockApplyBean result);
    void onEndTimePicker(String endTime);
    void onStartTimePicker(String startTime);
    void dataFailure(String result);

}
