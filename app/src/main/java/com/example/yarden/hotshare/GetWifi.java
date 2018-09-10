package com.example.yarden.hotshare;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GetWifi {

    private WifiManager m_wifiManager;
    private WifiConfiguration m_wifiConf;
    private  String m_ssid;
    private String m_key;

    public GetWifi(WifiManager wifiManager){
        m_wifiManager = wifiManager;
        m_wifiConf = new WifiConfiguration();
    }

    public void SetSSID(String ssid){
        m_ssid = ssid;
    }

    public void SetKey(String key){
        m_key = key;
    }

    public void ConnectToWifi() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String ssid = "yarden"; // temp for debuging
        String key = "Aa123456"; // temp for debuging

        m_wifiManager.setWifiEnabled(true);

        m_wifiConf.SSID = "\"" + ssid + "\"";
        m_wifiConf.preSharedKey = "\""+ key +"\"";
        int netId = m_wifiManager.addNetwork(m_wifiConf);
        m_wifiManager.saveConfiguration();
        m_wifiManager.disconnect();
        m_wifiManager.enableNetwork(netId, true);
        m_wifiManager.reconnect();


    }

    public void enableWifi() {
        if (!m_wifiManager.isWifiEnabled())
            m_wifiManager.setWifiEnabled(true);
    }
}
