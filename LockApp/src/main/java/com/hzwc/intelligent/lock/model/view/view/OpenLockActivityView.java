package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;
import com.inuker.bluetooth.library.search.SearchResult;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public interface OpenLockActivityView extends BaseMvpView {
    void onDeviceFounded(SearchResult device);
    void dataLoading();
    void OpenLockRecord(BaseBean result);
    void bigLockNotify(byte[] bytes);
    void littleLockNotify(byte[] bytes);
    void CloseLockRecord(BaseBean result);
    void dataFailure(String result);

    void saveLockLog(BaseBean result);

}
