package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by hongpeng on 2017/7/27.
 */

public class Province extends DataSupport{
    private int id;//省份ID
    private String provinceName;//省份名称
    private int provinceCode;//省份代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
