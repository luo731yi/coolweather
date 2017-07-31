package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hongpeng on 2017/7/31.
 */

public class Basic {
    @SerializedName("city")
    public String CityName;
    @SerializedName("id")
    public String weather_id;
    public Update update;
    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
