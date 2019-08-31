package com.angcyo.uikitdemo.component;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.angcyo.uiview.less.utils.NetStateChangeObserver;
import com.angcyo.uiview.less.utils.NetworkType;
import com.angcyo.uiview.less.utils.RNetwork;

import org.jetbrains.annotations.NotNull;

/**
 * 系统位置回调, 获取处理类
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019-8-31
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class RLocation {

    private static final long REFRESH_TIME = 5000L;
    private static final float METER_POSITION = 0.0f;
    private static ILocationListener mLocationListener;
    private static LocationListener listener = new MyLocationListener();

    private static class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {//定位改变监听
            if (mLocationListener != null) {
                mLocationListener.onSuccessLocation(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {//定位状态监听

        }

        @Override
        public void onProviderEnabled(String provider) {//定位状态可用监听

        }

        @Override
        public void onProviderDisabled(String provider) {//定位状态不可用监听

        }
    }

    /**
     * GPS获取定位方式
     */
    public static Location getGPSLocation(@NonNull Context context) {
        Location location = null;
        LocationManager manager = getLocationManager(context);
        //高版本的权限检查
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//是否支持GPS定位
            //获取最后的GPS定位信息，如果是第一次打开，一般会拿不到定位信息，一般可以请求监听，在有效的时间范围可以获取定位信息
            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return location;
    }

    /**
     * network获取定位方式
     */
    public static Location getNetWorkLocation(Context context) {
        Location location = null;
        LocationManager manager = getLocationManager(context);
        //高版本的权限检查
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {//是否支持Network定位
            //获取最后的network定位信息
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return location;
    }

    /**
     * 获取最好的定位方式
     */
    public static Location getBestLocation(Context context, Criteria criteria) {
        Location location;
        LocationManager manager = getLocationManager(context);
        if (criteria == null) {
            criteria = new Criteria();
        }
        String provider = chooseProvider(context, criteria);

        if (TextUtils.isEmpty(provider)) {
            //如果找不到最适合的定位，使用network定位
            location = getNetWorkLocation(context);
        } else {
            //高版本的权限检查
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            //获取最适合的定位方式的最后的定位权限
            location = manager.getLastKnownLocation(provider);
        }
        return location;
    }

    /**
     * 智能选择 位置服务商
     */
    @NonNull
    public static String chooseProvider(@NonNull Context context,
                                        @Nullable Criteria criteria) {
        if (criteria == null) {
            criteria = new Criteria();
        }

        String provider;

        if (RNetwork.INSTANCE.isWifi(context)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            //智能选择 位置服务商 best is GPS
            provider = getLocationManager(context).getBestProvider(criteria, true);
        }

        if (TextUtils.isEmpty(provider)) {
            //如果找不到最适合的定位
            provider = LocationManager.GPS_PROVIDER;
        }
        return provider;
    }

    /**
     * 定位监听
     */
    public static void addLocationListener(Context context, String provider, ILocationListener locationListener) {

        addLocationListener(context, provider, REFRESH_TIME, METER_POSITION, locationListener);
    }

    /**
     * 定位监听
     */
    public static void addLocationListener(Context context, String provider, long time, float meter, ILocationListener locationListener) {
        if (locationListener != null) {
            mLocationListener = locationListener;
        }
        if (listener == null) {
            listener = new MyLocationListener();
        }
        LocationManager manager = getLocationManager(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.requestLocationUpdates(provider, time, meter, listener);
    }

    /**
     * 取消定位监听
     */
    public static void unRegisterListener(Context context) {
        if (listener != null) {
            LocationManager manager = getLocationManager(context);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //移除定位监听
            manager.removeUpdates(listener);
        }
    }

    private static LocationManager getLocationManager(@NonNull Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /*类的方法*/

    private ILocationListener locationListener;

    private Context context;
    private LocationManager locationManager;

    //两次定位, 最小间隔时间 毫秒
    private long minTime = 1000;
    //两次定位, 最小距离 米
    private float minDistance = 1f;

    public RLocation(Context context) {
        this.context = context;
        locationManager = getLocationManager(context);
        RNetwork.INSTANCE.registerObserver(netStateChangeObserver);
    }

    public void release() {
        stopLocationListener();
        RNetwork.INSTANCE.unregisterObserver(netStateChangeObserver);
    }

    /**
     * 停止监听
     */
    public void stopLocationListener() {
        if (internalLocationListener != null) {
            locationManager.removeUpdates(internalLocationListener);
        }
    }

    /**
     * 开始监听位置信息改变
     */
    public void startLocationListener(@NonNull ILocationListener listener) {

        locationListener = listener;

        startLocationListenerInternal(chooseProvider(context, null));

        Location bestLocation = getBestLocation(context, null);
        if (bestLocation != null) {
            listener.onSuccessLocation(bestLocation);
        }
    }

    /**
     * @see LocationManager#requestLocationUpdates(String, long, float, LocationListener)
     */
    private void startLocationListenerInternal(@NonNull String provider) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationListener != null) {
            locationListener.onLocationStart(provider);
        }
        locationManager.requestLocationUpdates(provider, minTime, minDistance, internalLocationListener);
    }

    //切换定位方式
    private void switchLocationProvider(@NonNull String provider) {
        stopLocationListener();
        startLocationListenerInternal(provider);
    }

    //监听网络状态
    private NetStateChangeObserver netStateChangeObserver = new NetStateChangeObserver() {
        @Override
        public void onNetDisconnected() {
            switchLocationProvider(LocationManager.GPS_PROVIDER);
        }

        @Override
        public void onNetConnected(@NotNull NetworkType networkType) {
            if (networkType == NetworkType.NETWORK_AVAILABLE) {
                switchLocationProvider(LocationManager.NETWORK_PROVIDER);
            } else {
                switchLocationProvider(LocationManager.GPS_PROVIDER);
            }
        }
    };

    //位置监听回调
    private LocationListener internalLocationListener = new LocationListener() {

        /**
         * 数据结构
         * Location[gps 22.570636,114.060309 hAcc=24 et=+10d20h11m57s460ms alt=154.0521240234375 vel=0.29 bear=119.8 vAcc=48 sAcc=2 bAcc=96 {Bundle[mParcelledData.dataSize=96]}]
         * */
        @Override
        public void onLocationChanged(Location location) {//定位改变监听
            if (locationListener != null) {
                locationListener.onSuccessLocation(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {//定位状态监听

        }

        @Override
        public void onProviderEnabled(String provider) {//定位状态可用监听

        }

        @Override
        public void onProviderDisabled(String provider) {//定位状态不可用监听

        }
    };

    /**
     * 自定义接口
     */
    public interface ILocationListener {
        void onLocationStart(@NonNull String provider);

        void onSuccessLocation(@NonNull Location location);
    }
}