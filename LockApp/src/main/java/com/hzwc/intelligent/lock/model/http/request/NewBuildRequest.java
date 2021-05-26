package com.hzwc.intelligent.lock.model.http.request;


import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.bean.NewArea;
import com.hzwc.intelligent.lock.model.bean.NewAreaInfo;
import com.hzwc.intelligent.lock.model.bean.OCRBean;
import com.hzwc.intelligent.lock.model.bean.TypeBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public class NewBuildRequest {
    private Call<BaseBean> mNewBuildCall;
    private Call<TypeBean> mTypeCall;
    private Call<OCRBean> mImageCall;

    private  Call<NewAreaInfo> call;

    public void request(String token, String addr, double locationX, double locationY, String cabinetName,String areaId,int typeId, Callback<BaseBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mNewBuildCall = apiService.InstallCabinet(token, addr,locationX,locationY,cabinetName,areaId,typeId);
        mNewBuildCall.enqueue(callback);
    }
 public void typeRequest(String token , Callback<TypeBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
     mTypeCall = apiService.getCabinetType(token);
     mTypeCall.enqueue(callback);
    }


    public  void   getNewArea(String  token,Callback<NewAreaInfo> callback){


        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        call = apiService.getNewArea(token);
        call.enqueue(callback);
    }

public void ImageRequest(String token , MultipartBody.Part file, Callback<OCRBean> callback){
        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HttpService apiService = retrofit.create(HttpService.class);
    mImageCall =  apiService.imageOCRClient(token,file);
    mImageCall.enqueue(callback);






}

    public void interruptHttp(){
        if(mNewBuildCall != null && !mNewBuildCall.isCanceled()){
            mNewBuildCall.cancel();
        }
    }
}
