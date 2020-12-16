package com.hzwc.intelligent.lock.model.adapter;

import android.view.View;

import com.amap.api.maps.model.Marker;

/**
 * Created by mayn on 2018/7/11.
 */
public interface InfoWindowAdapter {
    View getInfoWindow(Marker marker);
    View getInfoContents(Marker marker);
}