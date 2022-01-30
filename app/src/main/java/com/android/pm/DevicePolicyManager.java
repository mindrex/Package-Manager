package com.android.pm;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public abstract class DevicePolicyManager {
    public abstract void setActiveAdmin(@NonNull String componentName, int user) throws Exception;
    public abstract void setProfileOwner(@NonNull String componentName, int user) throws Exception;
    public abstract void setDeviceOwner(@NonNull String componentName) throws Exception;
    public abstract void removeActiveAdmin(@NonNull String componentName, int user) throws Exception;
}