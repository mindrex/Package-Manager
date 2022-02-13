package com.android.pm;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public final class PackageUninstaller extends BasePmActivity {
    public PackageUninstaller() throws Exception {
    }

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final PackageInfo pi;
        try {
            pi = parsePackage();
        } catch (Exception e) {
            ((TextView) findViewById(R.id.progressMessage)).setText(R.string.invalid_package_info);
            ((TextView) findViewById(R.id.cancel)).setText(R.string.close);
            ((ImageView) findViewById(R.id.progressIcon)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.failed));
            return;
        }
        final PackageManager pm = getPackageManager();
        ((TextView) findViewById(R.id.appName)).setText(pm.getApplicationLabel(pi.applicationInfo));
        ((TextView) findViewById(R.id.packageName)).setText(pi.packageName);
        ((TextView) findViewById(R.id.appVersion)).setText(pi.versionName);
        ((ImageView) findViewById(R.id.appIcon)).setImageDrawable(pm.getApplicationIcon(pi.applicationInfo));
        final TextView progressMessage = findViewById(R.id.progressMessage);
        final ImageView progressIcon = findViewById(R.id.progressIcon);
        final Button uninstall = findViewById(R.id.install);
        progressMessage.setText(R.string.uninstall_confirm_message);
        progressIcon.setVisibility(View.GONE);
        uninstall.setText(R.string.uninstall);
        uninstall.setVisibility(View.VISIBLE);
        uninstall.setOnClickListener(null);
    }

    private PackageInfo parsePackage() throws Exception {
        final Uri uri = getIntent().getData();
        if (uri != null) {
            final String packageName = uri.getEncodedSchemeSpecificPart();
            if (packageName == null)
                throw new IOException("Cannot get package name");
            return getPackageManager().getPackageInfo(packageName, 0);
        } else throw new IOException("URI data has not been set");
    }
}