package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LockByIdBean;
import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.bean.WarnTypesBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 * @author
 * @date 2017/11/17
 * @description
 */
public interface AddOtherWarnView extends BaseMvpView {
    void requestLoading();
    void RimSuccess(MarkerBean result);
    void searchCabinetsByNoSuccess(LockByIdBean result);
    void getWarnTypeSuccess(WarnTypesBean result);
    void installLocationWarnSuccess(BaseBean result);
    void resultFailure(String result);
}
