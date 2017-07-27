package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by hongpeng on 2017/7/27.
 */

public class Province extends DataSupport{
    private int id;//省份ID
    private String provinceName;//省份名称
    private String provinceCode;//省份代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
