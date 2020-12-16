package com.hzwc.intelligent.lock.model.view.persenter;

import android.app.Activity;
import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.UnlockApplyBean;
import com.hzwc.intelligent.lock.model.http.request.RecordRequest;
import com.hzwc.intelligent.lock.model.view.view.RecordView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.qqtheme.framework.picker.DateTimePicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Administrator
 * @date 2018/1/15
 */

public class RecordPresenter extends BaseMvpPresenter<RecordView> {
    private final RecordRequest mRecordRequest;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int startHour;
    private int startMinute;
    private String startPutTime;
    private String endPutTime;

    public RecordPresenter() {
        this.mRecordRequest = new RecordRequest();
    }

    public void getUserUnlockRecord(String token, int page, int limit, int userId, String cabinetName, String lockNo, String beginTime, String endTime) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mRecordRequest.getUserUnlockRecord(token, page, limit, userId, cabinetName, lockNo, beginTime, endTime, new Callback<UnlockApplyBean>() {
            @Override
            public void onResponse(Call<UnlockApplyBean> call, Response<UnlockApplyBean> response) {
                if (getMvpView() != null) {
                    getMvpView().dataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<UnlockApplyBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    public void onStartTimePicker(Activity activity) {
        DateTimePicker picker = new DateTimePicker(activity, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(2017, 1, 1);
        picker.setDateRangeEnd(Integer.parseInt(initNowData("yyyy")), Integer.parseInt(initNowData("MM")), Integer.parseInt(initNowData("dd")));
        picker.setTimeRangeStart(0, 0);
        picker.setTimeRangeEnd(23, 59);
        picker.setLabelTextColor(0xFF48B7FF);
        picker.setDividerColor(0xFF48B7FF);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                getMvpView().onStartTimePicker(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00");
                startYear = Integer.parseInt(year);
                startMonth = Integer.parseInt(month);
                startDay = Integer.parseInt(day);
                startHour = Integer.parseInt(hour);
                startMinute = Integer.parseInt(minute);
            }
        });
        picker.show();
    }

    private String initNowData(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat(s);
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }


    public void onEndTimePicker(Activity activity) {
        DateTimePicker picker = new DateTimePicker(activity, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(startYear, startMonth, startDay);
        picker.setTimeRangeStart(startHour,startMinute);
        picker.setDateRangeEnd(Integer.parseInt(initNowData("yyyy")), Integer.parseInt(initNowData("MM")), Integer.parseInt(initNowData("dd")));
        picker.setTimeRangeStart(0, 0);
        picker.setTimeRangeEnd(23, 59);
        picker.setLabelTextColor(0xFF48B7FF);
        picker.setDividerColor(0xFF48B7FF);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {

            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                getMvpView().onEndTimePicker(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00");
                startYear = Integer.parseInt(year);
                startMonth = Integer.parseInt(month);
                startDay = Integer.parseInt(day);
                startHour = Integer.parseInt(hour);
                startMinute = Integer.parseInt(minute);
            }
        });
        picker.show();
    }

    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
    }

    public void interruptHttp(){
        mRecordRequest.interruptHttp();
    }
}
