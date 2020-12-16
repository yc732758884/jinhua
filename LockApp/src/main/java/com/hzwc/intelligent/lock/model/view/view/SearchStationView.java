package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BeanList;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 * Created by Administrator on 2018/1/15.
 */

public interface SearchStationView extends BaseMvpView {
    void dataLoading();
    void dataSuccess(BeanList beanList);
    void dataFailure(String result);
    void onItemClick(String context);
}
