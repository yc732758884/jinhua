package com.hzwc.intelligent.lock.model.http;


import android.content.Entity;

import com.hzwc.intelligent.lock.model.bean.AdCodeBean;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.CloseTakePic;
import com.hzwc.intelligent.lock.model.bean.CompanyListBean;
import com.hzwc.intelligent.lock.model.bean.DepartmentListBean;
import com.hzwc.intelligent.lock.model.bean.LocationWarnsBean;
import com.hzwc.intelligent.lock.model.bean.LockByIdBean;
import com.hzwc.intelligent.lock.model.bean.LockMacBean;
import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.bean.MessageBean;
import com.hzwc.intelligent.lock.model.bean.MineBean;
import com.hzwc.intelligent.lock.model.bean.NewArea;
import com.hzwc.intelligent.lock.model.bean.NewAreaInfo;
import com.hzwc.intelligent.lock.model.bean.NoCloseInfo;
import com.hzwc.intelligent.lock.model.bean.OCRBean;
import com.hzwc.intelligent.lock.model.bean.Page;
import com.hzwc.intelligent.lock.model.bean.PostListBean;
import com.hzwc.intelligent.lock.model.bean.PowerWarnsBean;
import com.hzwc.intelligent.lock.model.bean.RegisterBean;
import com.hzwc.intelligent.lock.model.bean.RestsWarnBean;
import com.hzwc.intelligent.lock.model.bean.TypeBean;
import com.hzwc.intelligent.lock.model.bean.UnlockApplyBean;
import com.hzwc.intelligent.lock.model.bean.UnlocksBean;
import com.hzwc.intelligent.lock.model.bean.Update;
import com.hzwc.intelligent.lock.model.bean.UserLockBean;
import com.hzwc.intelligent.lock.model.bean.UserRamBean;
import com.hzwc.intelligent.lock.model.bean.WarnTypesBean;
import com.hzwc.intelligent.lock.model.bean.areaBean;
import com.hzwc.intelligent.lock.model.bean.cityBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.FilePicker;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 2017-12-18 09:27:47
 * Anna.
 * ????????????
 *
 * @author apple
 */

public interface HttpService {


    //????????????
    @POST("app/login")
    Call<LoginBean> login(@Query("mobile") String username, @Query("password") String password,@Query("appId") String ID,@Query("regesterId")  String  rid);

    //?????? ???????????? ?????? ?????? ??????id  ??????id ??????id ?????????
    @POST("app/register")
    Call<BaseBean> register( @Query("mobile") String mobile,
                             @Query("password") String password,
                             @Query("name") String name,
                             @Query("areaId") int areaId ,
                             @Query("postId") int postId,
                             @Query("verifyCode") String verifyCode,
                             @Query("appId") String  id
                             );

    // ???????????????  ???????????? ??????
    @POST("app/sms/sendMessage")
    Call<RegisterBean> sendMessage(@Query("token") String token, @Query("phonenumber") String phonenumber, @Query("sendDepartment") String sendDepartment);

    // ?????
    @POST("app/sms/sendMessage")
    Call<MessageBean> sendMessage(@Query("phonenumber") String phonenumber, @Query("sendDepartment") String sendDepartment);

    // ?????????????????????
    @POST("app/sms/verification")
    Call<BaseBean> verification( @Query("phoneNumber") String phonenumber, @Query("verifyCode") String verifyCode);

    // ????????????  ????????????
    @POST("app/forgetPassword")
    Call<BaseBean> forgetPassword(@Query("mobile") String mobile,@Query("verifyCode") String verifyCode, @Query("password") String password,@Query("appId") String id);

    // ????????????  ??????
    @POST("app/renameByPassword")
    Call<BaseBean> renameByPassword(@Query("token") String token,@Query("mobile") String mobile,@Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword,@Query("appId") String id );

    // ???????????? ?????? ?????? ?????? ?????? ?????? ???????????? ??????id
    @POST("app/unlockApply/unlockApply")
    Call<BaseBean> unlockApply(@Query("token") String token, @Query("lockNo") String lockNo, @Query("userId") int userId);

    // ???????????? ?????? ???????????? ??????id ?????? ?????? ????????????  ????????????
    @POST("app/unlockApply/unlockRecord")
    Call<BaseBean> unlockRecord(@Query("token") String token, @Query("lockNo") String lockNo, @Query("userId") int userId, @Query("dept") String dept, @Query("post") String post, @Query("cabinetName") String cabinetName, @Query("power") int power);

    // ???????????? ?????? ???????????? ??????id ?????? ?????? ????????????  ????????????
    @POST("app/unlockApply/lockRecord")
    Call<BaseBean> lockRecord(@Query("token") String token, @Query("lockNo") String lockNo, @Query("userId") int userId);

    // ?????????????????????
    @POST("app/unlockApply/unlockAfter")
    Call<BaseBean> unlockAfter(@Query("token") String token, @Query("lockId") int lockId, @Query("userId") int userId, @Query("power") int power, @Query("lon") double lon, @Query("lat") double lat);

    //??????
    @POST("app/unlockApply/unlock")
    Call<UnlocksBean> unlock(@Query("token") String token, @Query("lockNo") String lockNo, @Query("userId") int userId);

    //???????????????????????? token ?????? ???????????? id ??????????????? ???????????? ????????????
    @GET("app/log/getUserUnlockRecord")
    Call<UnlockApplyBean> getUserUnlockRecord(@Header("token") String token, @Query("page") int page, @Query("limit") int limit, @Query("userId") int userId, @Query("cabinetName") String cabinetName, @Query("lockNo") String lockNo, @Query("beginTime") String beginTime, @Query("endTime") String endTime);

    // ???????????????
    @GET("app/search/searchCabinetsByNo")
    Call<MarkerBean> searchCabinetsByNo(@Header("token") String token, @Query("cabinetName") String cabinetName);

    // ???????????????id???????????????
    @GET("app/search/getCabinetsById")
    Call<LockByIdBean> getCabinetsById(@Header("token") String token, @Query("cabinetId") int cabinetId);

    // ???????????????
    @GET("app/install/getCabinetsByLocation")
    Call<MarkerBean> getCabinetsByLocation(@Header("token") String token, @Query("locationX") double locationX, @Query("locationY") double locationY);
    // ??????Mck
    @GET("app/install/getLockNoByCode")
    Call<LockMacBean> getLockNoByCode(@Header("token") String token, @Query("lockCode") String lockCode );
    //
    @GET("app/install/checkLockNo")
    Call<ResponseBody> checkLockNo(@Header("token") String token, @Query("lockNo") String lockCode );

    // ???????????????
    @POST("app/install/InstallCabinet")
    Call<BaseBean> InstallCabinet(@Header("token") String token, @Query("addr") String addr, @Query("locationX") double locationX, @Query("locationY") double locationY, @Query("cabinetName") String cabinetName, @Query("areaId") String areaId, @Query("typeId") int typeId);

    // ????????????????????????
    @GET("app/install/getInstallUserLock")
    Call<UserLockBean> getInstallUserLock(@Header("token") String token, @Query("userId") int userId);

    // ?????????
    @POST("app/install/updateLocks")
    Call<BaseBean> updateLocks(@Header("token") String token, @Query("lockNo") String lockNo, @Query("lockId") int lockId, @Query("cabinetId") int cabinetId);

    // ???????????????
    @POST("app/install/updateCabinet")
    Call<BaseBean> updateCabinet(@Header("token") String token, @Query("addr") String addr, @Query("cabinetId") int cabinetId, @Query("cabinetName") String cabinetName, @Query("locationX") double locationX, @Query("locationY") double locationY,@Query("areaId") int areaId);

    // ??????
    @POST("app/install/installLocks")
    Call<BaseBean> installLocks(@Header("token") String token, @QueryMap HashMap<String, String> cabinetId, @QueryMap HashMap<String, String> lockNo, @Query("userId") int userId);

    // ??????????????????????????????
    @GET("app/install/getInstallUserCabinet")
    Call<UserRamBean> getInstallUserCabinet(@Header("token") String token, @Query("userId") int userId);

    // ??????????????????
    @GET("app/warn/getWarnType")
    Call<WarnTypesBean> getWarnType(@Header("token") String token);

    //??????????????????(????????????)  token ?????? ???????????? id  ??????id
    @GET("app/warn/getPowerWarn")
    Call<PowerWarnsBean> getPowerWarn(@Header("token") String token, @Query("page") int page, @Query("limit") int limit, @Query("userId") int userId, @Query("warnInfoId") int warnInfoId);

    //??????????????????  token ?????? ???????????? id  ??????id
    @GET("app/warn/getLocationWarn")
    Call<LocationWarnsBean> getLocationWarn(@Header("token") String token, @Query("page") int page, @Query("limit") int limit, @Query("userId") int userId, @Query("warnInfoId") int warnInfoId);

    //??????????????????  token ?????? ???????????? id  ??????id
    @GET("app/warn/getRestsWarn")
    Call<RestsWarnBean> getRestsWarn(@Header("token") String token, @Query("page") int page,
                                     @Query("limit") int limit, @Query("userId") int userId,
                                     @Query("warnInfoId") int warnInfoId);

    //?????????????????????
    @GET("app/warn/getLockNotClosedByType")
    Call<BaseBean<Page<NoCloseInfo>>> getNoCloseWarn(@Header("token") String token, @Query("page") int page,
                                                     @Query("limit") int limit, @Query("userId") int userId,
                                                     @Query("warnInfoId") int warnInfoId);



    //??????????????????  token ??????id ??????id  ?????????id  ????????????ID ?????? ?????? ?????? ??????
    @POST("app/warn/installLocationWarn")
    Call<BaseBean> installLocationWarn(@Header("token") String token, @Query("lockId") String lockId, @Query("userId") int userId, @Query("cabinetId") int cabinetId, @Query("warnInfoId") int warnInfoId, @Query("locationLon") double locationLon, @Query("locationLat") double locationLat, @Query("infos") String infos, @Query("details") String details);

    //??????????????????  token ??????id ??????id  ?????????id  ????????????ID ?????? ?????? ??????
    @POST("app/warn/installPowerWarn")
    Call<BaseBean> installPowerWarn(@Header("token") String token, @Query("lockId") String lockId, @Query("userId") int userId, @Query("cabinetId") int cabinetId, @Query("warnInfoId") int warnInfoId, @Query("infos") String infos, @Query("power") int power, @Query("details") String details);

    //??????????????????  token ??????id ??????id  ?????????id  ????????????ID ?????? ?????? ?????? ??????
    @POST("app/warn/installRestsWarn")
    Call<BaseBean> installRestsWarn(@Header("token") String token, @Query("lockId") String lockId, @Query("userId") int userId, @Query("cabinetId") int cabinetId, @Query("warnInfoId") int warnInfoId, @Query("infos") String infos, @Query("details") String details);

    // ????????????
    @POST("app/employee/getUserInfo")
    Call<MineBean> getUserInfo(@Query("token") String token);

    @POST("app/employee/getUserInfo")
    Observable<MineBean> getUserInfo1(@Query("token") String token);
    // ????????????
//    @POST("app/image/imageOCRClient")
//    Call<MineBean> imageOCRClient(@Query("token") String token);

//    @POST("app/image/imageOCRClient")
//    Call<OCRBean> imageOCRClient(@Header("token") String token, @Part MultipartBody.Part imgs);


//    @POST("app/image/imageOCRClient")
//    Call<OCRBean> imageOCRClient(@Header("token") String token, @PartMap Map<String, RequestBody> map);


    @Multipart
    @POST("app/image/imageOCRClient")
//    Call<String> uploadOne(@Part("sign") String sign,@Part("appKey") String appKey,@Part("osName") String osName,@Part("memberNo") String memberNo, @Part  MultipartBody.Part file);
//    Call<String> uploadOne(@PartMap Map<String,String> params, @Part  MultipartBody.Part file);

//    Call<String> uploadOne(@Query("sign") String sign, @Query("appKey") String appKey, @Query("osName") String osName, @Query("memberNo") String memberNo, @Part  MultipartBody.Part file);
    Call<OCRBean> imageOCRClient(@Header("token") String token, @Part  MultipartBody.Part file);



//    @Multipart
//    @POST("app/image/imageOCRClient")
//    Call<OCRBean> imageOCRClient(@Header("token") String token, @PartMap Map<String, RequestBody> map);
//    @POST("app/image/imageOCRClient")
//    Call<OCRBean> imageOCRClient(@Header("token") String token, @Part MultipartBody.Part imgs);


    //  ???????????????????????????
    @GET("app/employee/getCompany")
    Call<CompanyListBean> getCompany();

    //??????????????????
    @GET("app/area/getCities")
    Call<cityBean> getCitys();


    //?????????????????????
    @GET("app/area/getArea")
    Call<areaBean> getAreas(@Query("cityId") String  id);




    //  ????????????????????????
    @GET("app/employee/getDepartments")
    Call<DepartmentListBean> getDepartments(@Query("companyId") String id);

    //  ??????????????????????????????
    @GET("app/employee/getPosts")
    Call<PostListBean> getPosts(@Query("departmentId") String id);


    // ????????????code????????????id
    @GET("app/install/getAreaIdByCode")
    Call<AdCodeBean> getAreaIdByCode(@Header("token") String token, @Query("adcode") String adcode);


    // ????????????
    @GET("app/install/getCabinetType")
    Call<TypeBean> getCabinetType(@Header("token") String token);



    //????????????
    @POST("app/unlockApply/closeLockAfter")
    Call<BaseBean> closeLock(@Header("token") String token,
                             @Query("lockNo") String lockid,
                             @Query("userId") String userId,
                             @Query("lockId")  String power);





    //????????????
    @POST("app/version/getLatestVersion")
    Call<Update> update();


    //??????????????????
    @GET("app/area/getNewArea")
    Call<NewAreaInfo> getNewArea(@Query("token") String token);


    //guansuoxinxishangbao
    @POST("app/unlockApply/saveLockLog")
    Call<BaseBean> saveLockLog(@Header("token") String token,
                                 @Query("lockNo") String lockid,
                                 @Query("userId") String userId,
                                 @Query("data")  String power);


    //tuichu
    @POST("app/logout")
    Call<BaseBean>  loginout (@Header("token") String token);

     @GET("app/install/checkCabinetCode")
    Observable<BaseBean<Boolean>>  checkCabinetCode(@Header("token") String token,@Query("cabinetCode")String code);

     // ??????  ??????
     //
     @Multipart
@POST("app/unlockApply/closeLockTakePictures")
Observable<BaseBean<CloseTakePic>>  closeLockTakePictures(@Header("token") String token, @Part("lockNo") String no,
                                                          @Part("userId") int id, @Part MultipartBody.Part pic);


}
