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
    @SerializedName("tem")
    public Tenmperature tenmperature;
    public class More{
        @SerializedName("txt_d")
        public String info;
    }
    public class Tenmperature{
        public String max;
        public String min;
    }
}
