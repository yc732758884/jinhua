package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.CompanyListBean;
import com.hzwc.intelligent.lock.model.bean.DepartmentListBean;
import com.hzwc.intelligent.lock.model.bean.PostListBean;
import com.hzwc.intelligent.lock.model.bean.areaBean;
import com.hzwc.intelligent.lock.model.bean.cityBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public interface RegisterNextView extends BaseMvpView {
    void dataLoading();
    void registerSuccess(BaseBean result);
    void companySuccess(CompanyListBean result);
    void departmentSuccess(DepartmentListBean result);
    void postSuccess(PostListBean result);
    void dataFailure(String result);

    void  city(cityBean cb);

      void   area(areaBean areaBean);

}
