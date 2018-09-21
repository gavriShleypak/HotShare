package com.example.yarden.hotshare.Client;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yarden.hotshare.R;

public class ClientActivity extends AppCompatActivity {

    private GetWifi getWifi;
    private WifiManager wifiManager;
    private DataUsage clientDataUsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        getWifi = new GetWifi(wifiManager);
        clientDataUsage = new DataUsage(wifiManager);

        Button GetWifiButton = (Button) findViewById(R.id.cl_ac_get_wifi_bu);
        GetWifiButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    getWifi.enableWifi();
                    getWifi.ConnectToWifi();
                    clientDataUsage.StartCountDataUsage(); // in anthor thred!
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
