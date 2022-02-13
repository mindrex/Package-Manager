package com.android.pm;

import android.content.pm.IPackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

abstract class BasePmActivity extends DialogActivity implements Handler.Callback {
    protected final Handler handler = new Handler(Looper.myLooper(), this);
    protected final IPackageManager ipm = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));

    public BasePmActivity() throws Exception {
        if (!ipm.setBlockUninstallForUser("com.android.pm", true, 0))
            Log.e("aaa", "aaa");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_installer);
        findViewById(R.id.cancel).setOnClickListener(view -> finish());
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }
}