package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by mayn on 2018/7/9.
 */

public class LocationBean {
  private   Double latitude;
    private   Double longitude;

    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public LocationBean(Double mLatitude, Double mLongitude) {
        this.latitude = mLatitude;
        this.longitude = mLongitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
