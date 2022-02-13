package com.android.pm;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.topjohnwu.superuser.Shell;

import java.util.Locale;

public final class StubApp extends Application {
    @Override
    public final void attachBaseContext(Context base) {
        Locale.setDefault(Locale.ROOT);
        final Configuration config = base.getResources().getConfiguration();
        config.locale = Locale.ROOT;
        config.fontScale = 1f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(Locale.ROOT);
        final DisplayMetrics displayMetrics = base.getResources().getDisplayMetrics();
        ((WindowManager) base.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        displayMetrics.scaledDensity = config.fontScale * displayMetrics.density;
        super.attachBaseContext(base);
        getResources().updateConfiguration(config, displayMetrics);
    }

    private void getPermission(String name) {
        final Shell sh = Shell.getShell();
        if (!sh.isRoot())
            Log.w("Application", "no root access, cannot get permission " + name);
        final Shell.Result r = sh.newJob().add("pm grant " + getPackageName() + " " + name).exec();
        if (!r.isSuccess())
            Log.w("Application", "failed to get permission " + name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final String[] perms = {
                    Manifest.permission.WRITE_SECURE_SETTINGS,
                    Manifest.permission.INSTALL_PACKAGES,
                    Manifest.permission.DELETE_PACKAGES,
                    "android.permission.INTERACT_ACROSS_USERS_FULL",
                    "android.permission.MANAGE_DEVICE_ADMINS"
            };
            for (String perm : perms) {
                if (checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED)
                    getPermission(perm);
            }
        }
    }
}