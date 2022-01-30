package com.android.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.mindrex.android.appsupport.MdcfgPrefs;

// TEST ONLY
// This activity is not part of package manager
// it will be removed in th future
// @Deprecated
public class WifiHotspot extends DialogActivity implements WifiP2pManager.ChannelListener, WifiP2pManager.ActionListener, WifiP2pManager.GroupInfoListener {
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private MdcfgPrefs prefs;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_hotspot);
        wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        prefs = (MdcfgPrefs) getSharedPreferences("wifi_hotspot", 0);
        ((TextView) findViewById(R.id.wifiName)).setText(prefs.getString("wifi-name", "DIRECT-NS-example"));
        ((TextView) findViewById(R.id.wifiPassword)).setText(prefs.getString("wifi-passphrase", "example12345"));
        //noinspection InlinedApi

        ((CheckBox) findViewById(R.id.enable5ghz)).setChecked(prefs.getBoolean("enable-5ghz", false));
        registerReceiver();
        ((CheckBox) findViewById(R.id.mainSwitch)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                startHotspot();
            else stopHotspot();
        });
    }

    @Override
    public void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
        if (wifiP2pGroup != null && wifiP2pGroup.isGroupOwner()) {
            ((TextView) findViewById(R.id.wifiName)).setText(wifiP2pGroup.getNetworkName());
            ((TextView) findViewById(R.id.wifiPassword)).setText(wifiP2pGroup.getPassphrase());
        }
    }

    private void registerReceiver() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public final void onReceive(Context context, Intent intent) {
            }
        }, filter);
    }

    private void toggleViewEnabledState(boolean s) {
        final TextView wn = findViewById(R.id.wifiName);
        final TextView wp = findViewById(R.id.wifiPassword);
        final CheckBox e = findViewById(R.id.enable5ghz);
        prefs.putString("wifi-name", wn.getText().toString());
        prefs.putString("wifi-passphrase", wp.getText().toString());
        prefs.putBoolean("enable-5ghz", e.isChecked());
        prefs.apply();
        wn.setEnabled(s);
        wp.setEnabled(s);
        e.setEnabled(s);
    }

    private void startHotspot() {
        toggleViewEnabledState(false);
        channel = wifiP2pManager.initialize(this, Looper.myLooper(), this);
        final WifiP2pConfig config = createWifiP2pConfig();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ((TextView) findViewById(R.id.wifiName)).setText(config.getNetworkName());
            ((TextView) findViewById(R.id.wifiPassword)).setText(config.getPassphrase());
        }
        createGroupCompat(channel, config, this);
        wifiP2pManager.requestGroupInfo(channel, this);
        wifiP2pManager.connect(channel, config, this);
        ((TextView) findViewById(R.id.state)).setText(R.string.connected);
    }

    private WifiP2pConfig createWifiP2pConfig() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return new WifiP2pConfig.Builder()
                    .setGroupOperatingBand(prefs.getBoolean("enable-5ghz", false) ? WifiP2pConfig.GROUP_OWNER_BAND_5GHZ : WifiP2pConfig.GROUP_OWNER_BAND_2GHZ)
                    .setNetworkName(prefs.getString("wifi-name", "DIRECT-NS-example"))
                    .setPassphrase(prefs.getString("wifi-passphrase", "example12345"))
                    .enablePersistentMode(true)
                    .build();
        } else return new WifiP2pConfig();
    }

    private void createGroupCompat(WifiP2pManager.Channel c, WifiP2pConfig config, WifiP2pManager.ActionListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            wifiP2pManager.createGroup(c, config, listener);
        else wifiP2pManager.createGroup(c, listener);
    }

    private void stopHotspot() {
        wifiP2pManager.removeGroup(channel, this);
        wifiP2pManager.cancelConnect(channel, this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)
            channel.close();
        ((TextView) findViewById(R.id.state)).setText(R.string.disconnected);
        toggleViewEnabledState(true);
    }

    @Override
    public void onChannelDisconnected() {
        Toast.makeText(this, R.string.channel_disconnected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void onFailure(int i) {
    }
}