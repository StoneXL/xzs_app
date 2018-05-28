package com.yxld.xzs.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yxld.xzs.entity.LocationEntity;

import java.util.ArrayList;
import java.util.List;

public class MapService extends Service implements BDLocationListener {
    public static List<LocationEntity> mLocations;

    static {
        mLocations = new ArrayList<>();
    }

    private LocationClient mLocClient;

    public MapService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLocationListener();
    }

    private void initLocationListener() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.setLocOption(option);
        mLocClient.registerLocationListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        mLocClient.start();
        return new MapBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        if(!mLocClient.isStarted()){
            mLocClient.start();
        }
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mLocClient.stop();
        Log.e("Tag","Service unbind");
        return super.onUnbind(intent);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        mLocClient.stop();
        Log.e("Tag","Service unbindService");
        super.unbindService(conn);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null) {
            return;
        }
        LocationEntity latLng = new LocationEntity(bdLocation.getLatitude(), bdLocation.getLongitude());

        if (duplicateOrOverDistance(latLng)) {
            return;
        }
        Log.e("Service", "纬度 : " + latLng.latitude + " ,经度 : " + latLng.longitude);
        mLocations.add(latLng);

    }

    private boolean duplicateOrOverDistance(LocationEntity latLng) {
        if (mLocations.size() == 0) {
            return false;
        }

        LocationEntity oldLatLng = mLocations.get(mLocations.size() - 1);
        return Math.abs(oldLatLng.latitude - latLng.latitude) > 0.01
                || Math.abs(oldLatLng.longitude - latLng.longitude) > 0.01
                || (oldLatLng.latitude == latLng.latitude && oldLatLng.longitude == latLng.longitude);
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }

    @Override
    public void onDestroy() {
        if(mLocClient.isStarted()){
            mLocClient.stop();
        }
        Log.e("Tag","Service onDestroy");
        super.onDestroy();
    }
    public class MapBinder extends Binder {
        public MapService getService(){
            return MapService.this;
        }
    }
}
