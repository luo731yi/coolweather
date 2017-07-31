package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by hongpeng on 2017/7/27.
 */

public class City extends DataSupport {
    private int id;//市ID
    private String cityName;//市名
    private int cityCode;//市代号
    private int provinceId;//所属省ID

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
