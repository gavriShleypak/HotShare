package com.example.yarden.hotshare.Client;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yarden.hotshare.Client.Utils.WiFiClientBroadcastReceiver;
import com.example.yarden.hotshare.R;

import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity {

    private GetWifi getWifi;
    private WifiManager wifiManager;
    private WifiP2pManager wifiP2pManager;
    private DataUsage clientDataUsage;
    private WifiP2pDevice targetDevice;
    private WifiP2pManager.Channel wifichannel;
    private WiFiClientBroadcastReceiver wifiClientReceiver;
    private IntentFilter wifiClientReceiverIntentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        getWifi = new GetWifi(wifiManager);
        clientDataUsage = new DataUsage(wifiManager);

        wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);


        wifichannel = wifiP2pManager.initialize(this, getMainLooper(), null);
        wifiClientReceiver = new WiFiClientBroadcastReceiver(wifiP2pManager, wifichannel, this);

        wifiClientReceiverIntentFilter = new IntentFilter();;
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

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

    public void setClientStatus(String message) {
        Log.d("Client:", message);
    }

    public void setClientWifiStatus(String message) {
        Log.d("ClientWifi:", message);
    }


    public void displayPeers(final WifiP2pDeviceList peers) {
        //Dialog to show errors/status
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("WiFi Direct File Transfer");

        //Get list view
        ListView peerView = (ListView) findViewById(R.id.peers_listview);

        //Make array list
        ArrayList<String> peersStringArrayList = new ArrayList<String>();

        //Fill array list with strings of peer names
        for (WifiP2pDevice wd : peers.getDeviceList()) {
            peersStringArrayList.add(wd.deviceName);
        }

        //Set list view as clickable
        peerView.setClickable(true);

        //Make adapter to connect peer data to list view
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, peersStringArrayList.toArray());

        //Show peer data in listview
        peerView.setAdapter(arrayAdapter);


        peerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {

                //Get string from textview
                TextView tv = (TextView) view;

                WifiP2pDevice device = null;

                //Search all known peers for matching name
                for (WifiP2pDevice wd : peers.getDeviceList()) {
                    if (wd.deviceName.equals(tv.getText()))
                        device = wd;
                }

                if (device != null) {
                    //Connect to selected peer
                    connectToPeer(device);

                } else {
                    dialog.setMessage("Failed");
                    dialog.show();

                }
            }

        });

    }

    public void searchForPeers(View view){
        wifiP2pManager.discoverPeers(wifichannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("ClientSearchPeer", "sucesses");
            }

            @Override
            public void onFailure(int i) {
                Log.d("ClientSearchPeer", "failure");
            }
        });
    }

    public void connectToPeer(final WifiP2pDevice wifiPeer){

        this.targetDevice =  wifiPeer;

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = wifiPeer.deviceAddress;

        wifiP2pManager.connect(wifichannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("ClientWifiConStatus:", "Sucesseful");
            }

            @Override
            public void onFailure(int i) {
                Log.d("ClientWifiConStatus", "failed");

            }
        });
    }

    // debate when the service should be turned off
    @Override
    protected void onResume() {
        super.onResume();
    }
}