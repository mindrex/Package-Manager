package com.android.pm;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

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

    static StubInterface getStubInterface() {
        return StubInterfaceImpl.getInstance();
    }
}