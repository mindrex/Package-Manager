package com.android.pm;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public abstract class StubInterface {
    @NonNull
    public abstract PackageManager pm();

    @NonNull
    public abstract DevicePolicyManager dpm();
}