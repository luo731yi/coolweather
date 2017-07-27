package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by hongpeng on 2017/7/27.
 */

public class City extends DataSupport {
    private int id;//市ID
    private String cityName;//市名
    private String cityCode;//市代号
    private int privinceId;//所属省ID

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
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

    public int getPrivinceId() {
        return privinceId;
    }

    public void setPrivinceId(int privinceId) {
        this.privinceId = privinceId;
    }
}
