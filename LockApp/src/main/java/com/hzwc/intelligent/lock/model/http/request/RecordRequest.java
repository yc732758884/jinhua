package com.hzwc.intelligent.lock.model.http.request;


import com.hzwc.intelligent.lock.model.bean.UnlockApplyBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;

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

public class RecordRequest {
    private Call<UnlockApplyBean> mRecordCall;

    public void getUserUnlockRecord(String token, int page, int limit, int userId, String cabinetName, String lockNo, String beginTime, String endTime, Callback<UnlockApplyBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mRecordCall = apiService.getUserUnlockRecord(token, page, limit, userId, cabinetName, lockNo, beginTime, endTime);
        mRecordCall.enqueue(callback);
    }

    public void interruptHttp(){
        if(mRecordCall != null && !mRecordCall.isCanceled()){
            mRecordCall.cancel();
        }
    }
}
