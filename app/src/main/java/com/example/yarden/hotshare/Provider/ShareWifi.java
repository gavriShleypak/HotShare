package com.example.yarden.hotshare.Provider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ShareWifi {

    private WifiManager m_wifiManager;
    private static Method getWifiApState;
    private  int AP_STATE_DISABLING = 10;
    private  int AP_STATE_DISABLED = 11;
    private  int AP_STATE_ENABLING = 12;
    private  int AP_STATE_ENABLED = 13;
    private  int AP_STATE_FAILED = 14;


    static {
        // lookup methods and fields not defined publicly in the SDK.
        Class<?> cls = WifiManager.class;
        for (Method method : cls.getDeclaredMethods()) {
            String methodName = method.getName();
            if (methodName.equals("getWifiApState")) {
                getWifiApState = method;
            }
        }
    }

    public ShareWifi(WifiManager _wifiManager) {
        m_wifiManager = _wifiManager;
    }

    public boolean GetHotspotStatus() {
        int actualState = 0;


        getWifiApState.setAccessible(true);
        try {
            actualState = (Integer) getWifiApState.invoke(m_wifiManager, (Object[]) null);
            String str = "" + actualState;//just for debuging
            Log.d("actualState", str); //just for debuging
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if(actualState != AP_STATE_ENABLING &&  actualState != AP_STATE_ENABLED) // HotSpot Not available
            return false;
        else
            return true;

    }

}
