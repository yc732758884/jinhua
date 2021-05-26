package com.hzwc.intelligent.lock.model.http.request;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.CompanyListBean;
import com.hzwc.intelligent.lock.model.bean.DepartmentListBean;
import com.hzwc.intelligent.lock.model.bean.PostListBean;
import com.hzwc.intelligent.lock.model.bean.areaBean;
import com.hzwc.intelligent.lock.model.bean.cityBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;
import com.hzwc.intelligent.lock.model.utils.SecurityRSA;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public class RegisterNextRequest {


    private Call<BaseBean> mRegisterCall;
    private Call<CompanyListBean> mCompanyCall;
    private Call<DepartmentListBean> mDepartmentCall;
    private Call<PostListBean> mPostCall;
    private Call<cityBean>  cityBeanCall;
    private Call<areaBean>  areaBeanCall;

    public RegisterNextRequest() {
    }

    public void register(String mobile, String password, String name,  int areaid,int postId, String code,String id,Callback<BaseBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mRegisterCall = apiService.register(SecurityRSA.encode(mobile), SecurityRSA.encode(password), name, areaid, postId,code,id);
        mRegisterCall.enqueue(callback);
    }

    public void companyRequest(  Callback<CompanyListBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mCompanyCall = apiService.getCompany();
        mCompanyCall.enqueue(callback);
    }

    public void cityRequest(  Callback<cityBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        cityBeanCall = apiService.getCitys();
        cityBeanCall.enqueue(callback);
    }


    public void areaRequest(  String id,Callback<areaBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        areaBeanCall = apiService.getAreas(id);
        areaBeanCall.enqueue(callback);
    }


    public void departmentRequest( String id, Callback<DepartmentListBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mDepartmentCall = apiService.getDepartments(id);
        mDepartmentCall.enqueue(callback);
    }
    public void postRequest(  String id,Callback<PostListBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mPostCall = apiService.getPosts(id);
        mPostCall.enqueue(callback);
    }

    public void interruptHttp(){
        if(mRegisterCall != null && !mRegisterCall.isCanceled()){
            mRegisterCall.cancel();
        }
    }
}
