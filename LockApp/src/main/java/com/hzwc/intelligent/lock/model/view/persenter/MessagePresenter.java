package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.MessageBean;
import com.hzwc.intelligent.lock.model.http.request.MessageRequest;
import com.hzwc.intelligent.lock.model.view.view.MessageView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public class MessagePresenter extends BaseMvpPresenter<MessageView> {
    private final MessageRequest mMessageRequest;

    public MessagePresenter() {
        this.mMessageRequest = new MessageRequest();
    }

    public void clickMessageRequest(final String mobile, String sendDepartment) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mMessageRequest.request(mobile, sendDepartment, new Callback<MessageBean>() {
            @Override
            public void onResponse(Call<MessageBean> call, Response<MessageBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj",response.message()+"=====");
                    getMvpView().dataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<MessageBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }
    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
    }

    public void interruptHttp(){
        mMessageRequest.interruptHttp();
    }
}
