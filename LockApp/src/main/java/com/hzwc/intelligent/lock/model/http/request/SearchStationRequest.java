package com.hzwc.intelligent.lock.model.http.request;


import com.hzwc.intelligent.lock.model.bean.BeanList;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2018/1/15.
 */

public class SearchStationRequest {
    private Call<BeanList> mSearchStationCall;

    public void request(String token, Long userID, Callback<BeanList> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
//        mSearchStationCall = apiService.stationFind(token, userID);
        mSearchStationCall.enqueue(callback);
    }

    public void interruptHttp(){
        if(mSearchStationCall != null && !mSearchStationCall.isCanceled()){
            mSearchStationCall.cancel();
        }
    }
}
