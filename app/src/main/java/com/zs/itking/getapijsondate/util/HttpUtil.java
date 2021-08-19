package com.zs.itking.getapijsondate.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by on 2021/8/19
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-08-19-15:01
 */
public class HttpUtil {
    public static void sendRequestWithOkhttp(String address,okhttp3.Callback callback) {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

//    public static String sendRequestWithOkhttp(String address)
//    {
//        OkHttpClient client=new OkHttpClient();
//        Request request = new Request.Builder().url(address) .build();
//        Response response= null;
//        try {
//            response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String message= null;
//        try {
//            message = response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return message;
//    }


}
