package com.hzwc.intelligent.lock.model.http;


import android.content.Entity;
import android.database.Observable;

import com.hzwc.intelligent.lock.model.bean.AdCodeBean;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
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
 * 联网接口
 *
 * @author apple
 */

public interface HttpService {


    //用户登录
    @POST("app/login")
    Call<LoginBean> login(@Query("mobile") String username, @Query("password") String password,@Query("appId") String ID);

    //注册 手机号码 密码 名字 公司id  部门id 职位id 验证码
    @POST("app/register")
    Call<BaseBean> register( @Query("mobile") String mobile,
                             @Query("password") String password,
                             @Query("name") String name,
                             @Query("areaId") int areaId ,
                             @Query("postId") int postId,
                             @Query("verifyCode") String verifyCode,
                             @Query("appId") String  id
                             );

    // 短信验证码  手机号码 部门
    @POST("app/sms/sendMessage")
    Call<RegisterBean> sendMessage(@Query("token") String token, @Query("phonenumber") String phonenumber, @Query("sendDepartment") String sendDepartment);

    // ?????
    @POST("app/sms/sendMessage")
    Call<MessageBean> sendMessage(@Query("phonenumber") String phonenumber, @Query("sendDepartment") String sendDepartment);

    // 短信验证码验证
    @POST("app/sms/verification")
    Call<BaseBean> verification(@Query("token") String token, @Query("phonenumber") String phonenumber, @Query("verifyCode") String verifyCode);

    // 修改密码  忘记密码
    @POST("app/forgetPassword")
    Call<BaseBean> forgetPassword(@Query("mobile") String mobile,@Query("verifyCode") int verifyCode, @Query("password") String password,@Query("appId") String id);

    // 修改密码  设置
    @POST("app/renameByPassword")
    Call<BaseBean> renameByPassword(@Query("token") String token,@Query("mobile") String mobile,@Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword,@Query("appId") String id );

    // 开锁申请 令牌 姓名 电话 部门 职位 锁的编号 用户id
    @POST("app/unlockApply/unlockApply")
    Call<BaseBean> unlockApply(@Query("token") String token, @Query("lockNo") String lockNo, @Query("userId") int userId);

    // 记录开锁 令牌 锁的编号 用户id 部门 职位 机柜名称  锁的电量
    @POST("app/unlockApply/unlockRecord")
    Call<BaseBean> unlockRecord(@Query("token") String token, @Query("lockNo") String lockNo, @Query("userId") int userId, @Query("dept") String dept, @Query("post") String post, @Query("cabinetName") String cabinetName, @Query("power") int power);

    // 记录关锁 令牌 锁的编号 用户id 部门 职位 机柜名称  锁的电量
    @POST("app/unlockApply/lockRecord")
    Call<BaseBean> lockRecord(@Query("token") String token, @Query("lockNo") String lockNo, @Query("userId") int userId);

    // 开锁后事件处理
    @POST("app/unlockApply/unlockAfter")
    Call<BaseBean> unlockAfter(@Query("token") String token, @Query("lockId") int lockId, @Query("userId") int userId, @Query("power") int power, @Query("lon") double lon, @Query("lat") double lat);

    //开锁
    @POST("app/unlockApply/unlock")
    Call<UnlocksBean> unlock(@Query("token") String token, @Query("lockNo") String lockNo, @Query("userId") int userId);

    //获取所有开锁记录 token 页码 每页条数 id 环网柜名称 开始时间 结束时间
    @GET("app/log/getUserUnlockRecord")
    Call<UnlockApplyBean> getUserUnlockRecord(@Header("token") String token, @Query("page") int page, @Query("limit") int limit, @Query("userId") int userId, @Query("cabinetName") String cabinetName, @Query("lockNo") String lockNo, @Query("beginTime") String beginTime, @Query("endTime") String endTime);

    // 搜索环网柜
    @GET("app/search/searchCabinetsByNo")
    Call<MarkerBean> searchCabinetsByNo(@Header("token") String token, @Query("cabinetName") String cabinetName);

    // 根据环网柜id获取环网柜
    @GET("app/search/getCabinetsById")
    Call<LockByIdBean> getCabinetsById(@Header("token") String token, @Query("cabinetId") int cabinetId);

    // 周边环网柜
    @GET("app/install/getCabinetsByLocation")
    Call<MarkerBean> getCabinetsByLocation(@Header("token") String token, @Query("locationX") double locationX, @Query("locationY") double locationY);
    // 查找Mck
    @GET("app/install/getLockNoByCode")
    Call<LockMacBean> getLockNoByCode(@Header("token") String token, @Query("lockCode") String lockCode );
    //
    @GET("app/install/checkLockNo")
    Call<ResponseBody> checkLockNo(@Header("token") String token, @Query("lockNo") String lockCode );

    // 安装环网柜
    @POST("app/install/InstallCabinet")
    Call<BaseBean> InstallCabinet(@Header("token") String token, @Query("addr") String addr, @Query("locationX") double locationX, @Query("locationY") double locationY, @Query("cabinetName") String cabinetName, @Query("areaId") String areaId, @Query("typeId") int typeId);

    // 用户安装的所有锁
    @GET("app/install/getInstallUserLock")
    Call<UserLockBean> getInstallUserLock(@Header("token") String token, @Query("userId") int userId);

    // 更新所
    @POST("app/install/updateLocks")
    Call<BaseBean> updateLocks(@Header("token") String token, @Query("lockNo") String lockNo, @Query("lockId") int lockId, @Query("cabinetId") int cabinetId);

    // 更新环网柜
    @POST("app/install/updateCabinet")
    Call<BaseBean> updateCabinet(@Header("token") String token, @Query("addr") String addr, @Query("cabinetId") int cabinetId, @Query("cabinetName") String cabinetName, @Query("locationX") double locationX, @Query("locationY") double locationY,@Query("areaId") int areaId);

    // 安装
    @POST("app/install/installLocks")
    Call<BaseBean> installLocks(@Header("token") String token, @QueryMap HashMap<String, String> cabinetId, @QueryMap HashMap<String, String> lockNo, @Query("userId") int userId);

    // 用户安装的所有环网柜
    @GET("app/install/getInstallUserCabinet")
    Call<UserRamBean> getInstallUserCabinet(@Header("token") String token, @Query("userId") int userId);

    // 获取告警类型
    @GET("app/warn/getWarnType")
    Call<WarnTypesBean> getWarnType(@Header("token") String token);

    //获取电量告警(欠压告警)  token 页码 每页条数 id  告警id
    @GET("app/warn/getPowerWarn")
    Call<PowerWarnsBean> getPowerWarn(@Header("token") String token, @Query("page") int page, @Query("limit") int limit, @Query("userId") int userId, @Query("warnInfoId") int warnInfoId);

    //获取位置告警  token 页码 每页条数 id  告警id
    @GET("app/warn/getLocationWarn")
    Call<LocationWarnsBean> getLocationWarn(@Header("token") String token, @Query("page") int page, @Query("limit") int limit, @Query("userId") int userId, @Query("warnInfoId") int warnInfoId);

    //获取其他告警  token 页码 每页条数 id  告警id
    @GET("app/warn/getRestsWarn")
    Call<RestsWarnBean> getRestsWarn(@Header("token") String token, @Query("page") int page,
                                     @Query("limit") int limit, @Query("userId") int userId,
                                     @Query("warnInfoId") int warnInfoId);

    //获取锁未关报警
    @GET("app/warn/getLockNotClosedByType")
    Call<BaseBean<Page<NoCloseInfo>>> getNoCloseWarn(@Header("token") String token, @Query("page") int page,
                                                     @Query("limit") int limit, @Query("userId") int userId,
                                                     @Query("warnInfoId") int warnInfoId);



    //添加位置告警  token 锁的id 用户id  环网柜id  异常类型ID 经度 纬度 概述 详情
    @POST("app/warn/installLocationWarn")
    Call<BaseBean> installLocationWarn(@Header("token") String token, @Query("lockId") String lockId, @Query("userId") int userId, @Query("cabinetId") int cabinetId, @Query("warnInfoId") int warnInfoId, @Query("locationLon") double locationLon, @Query("locationLat") double locationLat, @Query("infos") String infos, @Query("details") String details);

    //添加电量告警  token 锁的id 用户id  环网柜id  异常类型ID 概述 电量 详情
    @POST("app/warn/installPowerWarn")
    Call<BaseBean> installPowerWarn(@Header("token") String token, @Query("lockId") String lockId, @Query("userId") int userId, @Query("cabinetId") int cabinetId, @Query("warnInfoId") int warnInfoId, @Query("infos") String infos, @Query("power") int power, @Query("details") String details);

    //添加其他告警  token 锁的id 用户id  环网柜id  异常类型ID 经度 纬度 概述 详情
    @POST("app/warn/installRestsWarn")
    Call<BaseBean> installRestsWarn(@Header("token") String token, @Query("lockId") String lockId, @Query("userId") int userId, @Query("cabinetId") int cabinetId, @Query("warnInfoId") int warnInfoId, @Query("infos") String infos, @Query("details") String details);

    // 个人信息
    @POST("app/employee/getUserInfo")
    Call<MineBean> getUserInfo(@Query("token") String token);


    // 文字识别
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


    //  获取所有分公司信息
    @GET("app/employee/getCompany")
    Call<CompanyListBean> getCompany();

    //获取所有的市
    @GET("app/area/getCities")
    Call<cityBean> getCitys();


    //获取所有的区域
    @GET("app/area/getArea")
    Call<areaBean> getAreas(@Query("cityId") String  id);




    //  获取所有部门信息
    @GET("app/employee/getDepartments")
    Call<DepartmentListBean> getDepartments(@Query("companyId") String id);

    //  获取所有部门职位信息
    @GET("app/employee/getPosts")
    Call<PostListBean> getPosts(@Query("departmentId") String id);


    // 根据区县code获取区县id
    @GET("app/install/getAreaIdByCode")
    Call<AdCodeBean> getAreaIdByCode(@Header("token") String token, @Query("adcode") String adcode);


    // 获取类型
    @GET("app/install/getCabinetType")
    Call<TypeBean> getCabinetType(@Header("token") String token);



    //关锁上报
    @POST("app/unlockApply/closeLockAfter")
    Call<BaseBean> closeLock(@Header("token") String token,
                             @Query("lockNo") String lockid,
                             @Query("userId") String userId,
                             @Query("lockId")  String power);





    //检查更新
    @POST("app/version/getLatestVersion")
    Call<Update> update();


    //获取新的区县
    @GET("app/area/getNewArea")
    Call<NewAreaInfo> getNewArea(@Query("token") String token);


    //guansuoxinxishangbao
    @POST("app/unlockApply/saveLockLog")
    Call<BaseBean> saveLockLog(@Header("token") String token,
                                 @Query("lockNo") String lockid,
                                 @Query("userId") String userId,
                                 @Query("data")  String power);

}
