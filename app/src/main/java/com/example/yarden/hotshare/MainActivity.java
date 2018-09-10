package com.example.yarden.hotshare;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private GetWifi getWifi;
    private ShareWifi shareWifi;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        getWifi = new GetWifi(wifiManager);
        final DataUsage dataUsage = new DataUsage(wifiManager);
        final Button getWifibutton = findViewById(R.id.GetWifiButton);
        final ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        getWifibutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    getWifi.enableWifi();
                    getWifi.ConnectToWifi();
                    dataUsage.StartCountDataUsage(); // in anthor thred!
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        shareWifi = new ShareWifi(wifiManager);
        final Button shareWifibutton = findViewById(R.id.ShareWifiButton);
        shareWifibutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    if(!shareWifi.GetHotspotStatus())
                        HotSpotDialog();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void HotSpotDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("HotSpot is disable");

        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Hotspot Dialog", "ok");
                    }
                });
        builder.show();
    }


}
