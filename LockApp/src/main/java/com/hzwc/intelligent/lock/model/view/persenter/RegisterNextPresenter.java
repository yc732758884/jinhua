package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.CompanyListBean;
import com.hzwc.intelligent.lock.model.bean.DepartmentListBean;
import com.hzwc.intelligent.lock.model.bean.PostListBean;
import com.hzwc.intelligent.lock.model.bean.areaBean;
import com.hzwc.intelligent.lock.model.bean.cityBean;
import com.hzwc.intelligent.lock.model.http.request.RegisterNextRequest;
import com.hzwc.intelligent.lock.model.view.view.RegisterNextView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Administrator
 * @date 2018/1/15
 */

public class RegisterNextPresenter extends BaseMvpPresenter<RegisterNextView> {
    private final RegisterNextRequest mRegisterNextRequest;

    public RegisterNextPresenter() {
        this.mRegisterNextRequest = new RegisterNextRequest();
    }

    public void register( String mobile, String password, String name, int areaid, int postId,String code,String id) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mRegisterNextRequest.register( mobile, password, name, areaid, postId, code,id, new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().registerSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    public void getCity() {


        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mRegisterNextRequest.cityRequest(new Callback<cityBean>() {
            @Override
            public void onResponse(Call<cityBean> call, Response<cityBean> response) {

                if (getMvpView() != null) {
                    getMvpView().city(response.body());
                }
            }

            @Override
            public void onFailure(Call<cityBean> call, Throwable t) {

                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    public void getArea(String id) {


        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mRegisterNextRequest.areaRequest(id,new Callback<areaBean>() {
            @Override
            public void onResponse(Call<areaBean> call, Response<areaBean> response) {

                if (getMvpView() != null) {
                    getMvpView().area(response.body());
                }
            }

            @Override
            public void onFailure(Call<areaBean> call, Throwable t) {

                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }


    public void getCompany() {


        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mRegisterNextRequest.companyRequest(new Callback<CompanyListBean>() {
            @Override
            public void onResponse(Call<CompanyListBean> call, Response<CompanyListBean> response) {

                if (getMvpView() != null) {
                    getMvpView().companySuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<CompanyListBean> call, Throwable t) {
                Log.e("111111","~~~"+t.getMessage());
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    public void getDepartment(String id) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mRegisterNextRequest.departmentRequest(id,new Callback<DepartmentListBean>() {
            @Override
            public void onResponse(Call<DepartmentListBean> call, Response<DepartmentListBean> response) {
                if (getMvpView() != null) {
                    getMvpView().departmentSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<DepartmentListBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    public void getPost(String id) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mRegisterNextRequest.postRequest(id,new Callback<PostListBean>() {
            @Override
            public void onResponse(Call<PostListBean> call, Response<PostListBean> response) {
                if (getMvpView() != null) {
                    getMvpView().postSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostListBean> call, Throwable t) {
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
        mRegisterNextRequest.interruptHttp();
    }
}
