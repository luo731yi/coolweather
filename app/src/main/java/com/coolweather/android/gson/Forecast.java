package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hongpeng on 2017/7/31.
 */

public class Forecast {
    @SerializedName("date")
    public String date;
    @SerializedName("cond")
    public More more;
    @SerializedName("tmp")
    public Temperature temperature;
    public class More{
        @SerializedName("txt_d")
        public String info;
    }
    public class Temperature{
        @SerializedName("max")
        public String max;
        @SerializedName("min")
        public String min;
    }
}
