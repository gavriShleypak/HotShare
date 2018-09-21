package com.example.yarden.hotshare;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yarden.hotshare.Client.ClientActivity;
import com.example.yarden.hotshare.Client.DataUsage;
import com.example.yarden.hotshare.Provider.ProviderActivity;
import com.example.yarden.hotshare.Provider.ShareWifi;

public class MainActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private ShareWifi shareWifi;
    private Button ClientButton;
    private Button ProviderButton;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        final DataUsage dataUsage = new DataUsage(wifiManager);
        final Button getWifibutton = findViewById(R.id.cl_ac_get_wifi_bu);

        ProviderButton = (Button) findViewById(R.id.bt_provider);
        ProviderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProviderActivity.class);
                startActivity(intent);
            }
        });
        ClientButton = (Button) findViewById(R.id.bt_client);
        ClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                startActivity(intent);
            }
        });
    }



}
