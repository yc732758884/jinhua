package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.bean.NewArea;
import com.hzwc.intelligent.lock.model.bean.NewAreaInfo;
import com.hzwc.intelligent.lock.model.bean.OCRBean;
import com.hzwc.intelligent.lock.model.bean.TypeBean;
import com.hzwc.intelligent.lock.model.http.request.LoginRequest;
import com.hzwc.intelligent.lock.model.http.request.NewBuildRequest;
import com.hzwc.intelligent.lock.model.view.view.LoginView;
import com.hzwc.intelligent.lock.model.view.view.NewBuildView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2018-6-7 16:47:06
 */
public class NewBuildPresenter extends BaseMvpPresenter<NewBuildView> {
    private final NewBuildRequest mNewBuildRequest;

    public NewBuildPresenter() {
        this.mNewBuildRequest = new NewBuildRequest();
    }

    public void clickRequest( String token,String addr,double locationX,double locationY,String cabinetName,String areaId,int typeId) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mNewBuildRequest.request(token, addr, locationX,locationY,cabinetName,areaId,typeId, new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().newBuildSuccess(response.body());
                }
            }


            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().resultFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

   public void typeRequest( String token) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mNewBuildRequest.typeRequest(token, new Callback<TypeBean>() {
            @Override
            public void onResponse(Call<TypeBean> call, Response<TypeBean> response) {
                if (getMvpView() != null) {
                    getMvpView().newTypeSuccess(response.body());
                }
            }


            @Override
            public void onFailure(Call<TypeBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().resultFailure(Log.getStackTraceString(t));
                }
            }
        });
    }


    public   void   getNewArea(String token){

        mNewBuildRequest.getNewArea(token, new Callback<NewAreaInfo>() {
            @Override
            public void onResponse(Call<NewAreaInfo> call, Response<NewAreaInfo> response) {
                if (getMvpView() != null) {
                    getMvpView().getNewArea(response.body());
                }
            }


            @Override
            public void onFailure(Call<NewAreaInfo> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().resultFailure(Log.getStackTraceString(t));
                }
            }
        });

    }


   public void imageRequest( String token,MultipartBody.Part file) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }




//       UploadService uploadService = retrofit.create(UploadService.class);
//       Call<String> call  = uploadService.uploadOne(params,body);
//       // Call<String> call  = uploadService.uploadOne(SIGN,APP_KEY,OS_NAME,"13410111258",body);
//       call.enqueue(new Callback<String>() {
//           @Override
//           public void onResponse(Call<String> call, Response<String> response) {
//               L.d("vivi",response.message()+"    "+response.body());
//           }
//
//           @Override
//           public void onFailure(Call<String> call, Throwable t) {
//               t.printStackTrace();
//               L.d("vivi",t.getMessage());
//           }
//       });


        mNewBuildRequest.ImageRequest(token, file, new Callback<OCRBean>() {
            @Override
            public void onResponse(Call<OCRBean> call, Response<OCRBean> response) {
                if (getMvpView() != null) {
                    getMvpView().newImageSuccess(response.body());
                }
            }


            @Override
            public void onFailure(Call<OCRBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().resultFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
    }

    public void interruptHttp(){
        mNewBuildRequest.interruptHttp();
    }
}
