package com.zs.itking.getapijsondate.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zs.itking.getapijsondate.bean.WeatherBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * created by on 2021/8/19
 * 描述：Json工具类
 *
 * @author ZSAndroid
 * @create 2021-08-19-21:00
 */
public class JsonUtils {

    /**
     *
     * @param context
     * @return
     */
    public List<WeatherBean> getWeatherList(Context context, String strJson){
        List<WeatherBean> list = new ArrayList<>();

        Gson gson = new Gson();
        Type type = new TypeToken<List<WeatherBean>>() {}.getType();
        list = gson.fromJson(strJson,type);

        return list;
    }
}
