package com.example.yarden.hotshare.Provider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yarden.hotshare.R;

public class ProviderActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private ShareWifi shareWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        shareWifi = new ShareWifi(wifiManager);
        final Button shareWifibutton = findViewById(R.id.pr_ac_share_wifi);
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
