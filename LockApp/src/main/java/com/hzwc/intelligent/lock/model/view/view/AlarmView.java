package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LocationWarnsBean;
import com.hzwc.intelligent.lock.model.bean.NoCloseInfo;
import com.hzwc.intelligent.lock.model.bean.Page;
import com.hzwc.intelligent.lock.model.bean.PowerWarnsBean;
import com.hzwc.intelligent.lock.model.bean.RestsWarnBean;
import com.hzwc.intelligent.lock.model.bean.WarnTypesBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public interface AlarmView extends BaseMvpView {
    void dataLoading();
    void getWarnTypeSuccess(WarnTypesBean result);
    void getPowerWarnSuccess(PowerWarnsBean result);
    void getLocationWarnSuccess(LocationWarnsBean result);
    void getRestsWarnSuccess(RestsWarnBean result);
    void getNoCloseWarnSuccess(BaseBean<Page<NoCloseInfo>> result);

    void dataFailure(String result);


}
