package com.yxld.xzs.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/8.
 */

public class JSONUtil {
    public static JSONObject getData(Context context,Response response) throws IOException,NoDataException {
        String strResp=response.body().string();
        Log.e("info",strResp);
        try {
            return new JSONObject(strResp).getJSONObject("data");
        }catch (JSONException e){
            e.printStackTrace();
            ToastUtil.show(context, strResp);
            throw new NoDataException(strResp);
        }
    }
    public static JSONArray getArrayData(Context context, Response response) throws IOException,NoDataException {
        String strResp=response.body().string();
        Log.e("info",strResp);
        try {
            return new JSONObject(strResp).getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
            ToastUtil.show(context, strResp);
            throw new NoDataException(strResp);
        }
    }
    public static class NoDataException extends Exception{
        public NoDataException(String message) {
            super(message);
        }
    }
}
