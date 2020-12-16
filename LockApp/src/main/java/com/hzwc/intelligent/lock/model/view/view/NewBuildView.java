package com.hzwc.intelligent.lock.model.view.view;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.bean.NewArea;
import com.hzwc.intelligent.lock.model.bean.NewAreaInfo;
import com.hzwc.intelligent.lock.model.bean.OCRBean;
import com.hzwc.intelligent.lock.model.bean.TypeBean;
import com.hzwc.intelligent.lock.mvpframework.view.BaseMvpView;

/**
 * @author
 * @date 2017/11/17
 * @description
 */
public interface NewBuildView extends BaseMvpView {
    void requestLoading();
    void newBuildSuccess(BaseBean result);
    void newTypeSuccess(TypeBean result);
    void newImageSuccess(OCRBean result);
    void resultFailure(String result);


    void  getNewArea(NewAreaInfo result);

}
