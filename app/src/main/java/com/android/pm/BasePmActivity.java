package com.android.pm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

abstract class BasePmActivity extends DialogActivity implements Handler.Callback {
    final Handler handler = new Handler(Looper.myLooper(), this);

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