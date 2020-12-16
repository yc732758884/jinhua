package com.hzwc.intelligent.lock.model.view.persenter;

import com.hzwc.intelligent.lock.model.view.view.SearchStationView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;
import com.hzwc.intelligent.lock.mvpframework.view.bezier.FlowLayout;

/**
 * Created by Administrator on 2018/1/15.
 */

public class SearchStationPresenter extends BaseMvpPresenter<SearchStationView> implements FlowLayout.OnItemClickListener{

    /*private final SearchStationRequest mSearchStationRequest;

    public SearchStationPresenter() {
        this.mSearchStationRequest = new SearchStationRequest();
    }

    public void clickRequest(final String token, Long userID) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mSearchStationRequest.request(token, userID, new Callback<BeanList>() {
            @Override
            public void onResponse(Call<BeanList> call, Response<BeanList> response) {
                if (getMvpView() != null) {
                    getMvpView().dataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<BeanList> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    public String[] getStr(BeanList beanList) {
        String[] str = new String[beanList.getData().size()];
        for (int i = 0; i < beanList.getData().size(); i++) {
            str[i] = beanList.getData().get(i).getStationName();
        }
        return str;
    }

    public List<String> getCacheList(Context context) {
        List<String> data = SpUtils.getList(context, "data");
        if (data != null && data.size() != 0) {
            return data;
        }
        return new ArrayList<>();
    }

    public List<String> searchSave(Context context, String content, List<BeanList.DataBean> stationList, List<String> cacheList){
        for (int i = 0; i < cacheList.size(); i++) {
            String searchHistory = cacheList.get(i);
            if (searchHistory.equals(content)) {
                cacheList.remove(i);
                break;
            }
        }
        if (cacheList.size() >= 10) {
            cacheList.remove(9);
        }


        for (BeanList.DataBean bean : stationList) {
            if (bean.getStationName().equals(content)) {
                cacheList.add(0, content);
            }
        }
        SpSQUtils.putList(context, "data", cacheList);
        return cacheList;
    }

    public void  searchStationById(Activity activity, String string, List<BeanList.DataBean> stationList, int sign){
        int stationId = -1;
        for (int j = 0; j < stationList.size(); j++) {
            BeanList.DataBean dataBean = stationList.get(j);
            String name = dataBean.getStationName();
            if (name.equals(string)) {
                stationId = dataBean.getStationId();
            }
        }
        if (stationId == -1) {
            Toast.makeText(activity, "have no such station"+stationId, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent;
            if(sign == 1){
                intent =new Intent();
                intent.putExtra("stationId", stationId);
                activity.setResult(0,intent);
                activity.finish();
            }else if (sign == 2){
                intent = new Intent(activity,PlantDetailsActivity.class);
                intent.putExtra("station_name", string);
                intent.putExtra("station_id",stationId);
                activity.startActivity(intent);
            }
            Toast.makeText(activity, "search by id = "+stationId, Toast.LENGTH_SHORT).show();
        }
    }

*/
    @Override
    public void onItemClick(String content) {
        getMvpView().onItemClick(content);
    }


}
