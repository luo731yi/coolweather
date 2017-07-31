package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hongpeng on 2017/7/31.
 */

public class Now {
    @SerializedName("tmp")
    public String tenmperature;
    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt")
        public String info;
    }
}
