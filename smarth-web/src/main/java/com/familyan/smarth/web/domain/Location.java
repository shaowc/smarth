package com.familyan.smarth.web.domain;

import com.lotus.core.web.cookyjar.Writable;
import com.lotus.core.web.cookyjar.util.WritableUtil;

/**
 * Created by shaowenchao on 16/9/21.
 */
public class Location implements Writable {

    private String city;
    private String longitude;
    private String latitude;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String write() {
        return WritableUtil.write(city, longitude, latitude);
    }

    @Override
    public void read(String value) {
        String[] values = WritableUtil.read(value);
        city = values[0];
        longitude = values[1];
        latitude = values[2];
    }
}
