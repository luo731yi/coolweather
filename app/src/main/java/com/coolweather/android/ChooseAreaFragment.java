package com.coolweather.android;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.unit.HttpUtil;
import com.coolweather.android.unit.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by hongpeng on 2017/7/27.
 */

public class ChooseAreaFragment extends Fragment{
    private static final int LEVEL_PTOVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY= 2;
    private ProgressDialog progressDialog;
    private TextView titleView;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> datalist = new ArrayList<>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectProvince;
    private City selectCity;
    private int currentLevel;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);
        titleView = (TextView) view.findViewById(R.id.text_title);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView ) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,datalist);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVEL_PTOVINCE){
                    selectProvince = provinceList.get(i);
                    queryCity();
                }else if (currentLevel == LEVEL_CITY){
                    selectCity = cityList.get(i);
                    queryCounty();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == LEVEL_CITY){
                    queryProvince();
                }else if (currentLevel == LEVEL_COUNTY){
                    queryCity();
                }
            }
        });
        queryProvince();
    }

    /**
     * 查询所有省份，优先查数据库，没有则在服务器上查询
     */
    private void queryProvince(){
        titleView.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0){
            datalist.clear();
            for (Province province:provinceList){
                datalist.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PTOVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address,"province");
        }
    }

    /**
     * 查询选中省份所有的城市
     */
    private void queryCity(){
        titleView.setText(selectProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceId = ?",String.valueOf(selectProvince.getId())).find(City.class);
        if (cityList.size() > 0){
            datalist.clear();
            for (City city:cityList){
                datalist.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else {
            String address = "http://guolin.tech/api/china"+selectProvince.getId();
            queryFromServer(address,"city");
        }
    }

    /**
     * 查询选中城市所有的县
     */
    private void queryCounty(){
        titleView.setText(selectCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityId = ?",String.valueOf(selectCity
        .getId())).find(County.class);
        if (countyList.size() > 0){
            datalist.clear();
            for (County county:countyList){
                datalist.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }else {
            String address = "http://guolin.tech/api/china"+selectProvince.getId()+"/"+selectCity.getId();
            queryFromServer(address,"county");
        }
    }
    /**
     * 根据传入的地址，在服务器上查询数据
     * @param address
     * @param type
     */
    private void queryFromServer(String address,final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().toString();
                boolean result = false;
                if ("province".equals(type)){
                    result = Utility.handleProvinceResponse(responseText);
                }else if ("city".equals(type)){
                    result = Utility.handleCityResponse(responseText,selectProvince.getId());
                }else if ("county".equals(type)){
                    result = Utility.handleCountyResponse(responseText,selectCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvince();
                            }else if ("city".equals(type)){
                                queryCity();
                            }else if ("county".equals(type)){
                                queryCounty();
                            }
                        }
                    });
                }
            }
        });
    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载。。。");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
