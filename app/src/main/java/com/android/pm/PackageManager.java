package com.android.pm;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public abstract class PackageManager {
    public abstract void install(@NonNull String path) throws Exception;
    public abstract void install(@NonNull String path, int user) throws Exception;
    public abstract void uninstall(@NonNull String packageName) throws Exception;
    public abstract void uninstall(@NonNull String packageName, int user) throws Exception;
    public abstract void installExisting(@NonNull String packageName) throws Exception;
    public abstract void installExisting(@NonNull String packageName, int user) throws Exception;
    public abstract void enable(@NonNull String packageName) throws Exception;
    public abstract void enable(@NonNull String packageName, int user) throws Exception;
    public abstract void disable(@NonNull String packageName) throws Exception;
    public abstract void disable(@NonNull String packageName, int user) throws Exception;
    public abstract void grant(@NonNull String packageName, @NonNull String permission) throws Exception;
    public abstract void grant(@NonNull String packageName, @NonNull String permission, int user) throws Exception;
    public abstract void revoke(@NonNull String packageName, @NonNull String permission) throws Exception;
    public abstract void revoke(@NonNull String packageName, @NonNull String permission, int user) throws Exception;
    public abstract String[] listPackages() throws Exception;
    public abstract String[] listPackages(int user) throws Exception;
}