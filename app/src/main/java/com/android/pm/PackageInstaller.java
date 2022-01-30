package com.android.pm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class PackageInstaller extends BasePmActivity implements Handler.Callback {
    private static final int parsePackageFinished = 0;
    private static final int parsePackageFailed = 1;
    private static final int installationFinished = 2;
    private static final int installationFailed = 3;
    private String path;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(() -> {
            try {
                PackageInfo pi = parsePackage();
                final Message msg = new Message();
                msg.what = parsePackageFinished;
                msg.obj = pi;
                handler.sendMessage(msg);
            } catch (Exception e) {
                final Message msg = new Message();
                msg.what = parsePackageFailed;
                msg.obj = e.getMessage();
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case parsePackageFinished: {
                final PackageManager pm = getPackageManager();
                final PackageInfo pi = (PackageInfo) msg.obj;
                ((TextView) findViewById(R.id.appName)).setText(pm.getApplicationLabel(pi.applicationInfo));
                ((TextView) findViewById(R.id.packageName)).setText(pi.packageName);
                ((TextView) findViewById(R.id.appVersion)).setText(pi.versionName);
                ((ImageView) findViewById(R.id.appIcon)).setImageDrawable(pm.getApplicationIcon(pi.applicationInfo));
                final TextView progressMessage = findViewById(R.id.progressMessage);
                final ImageView progressIcon = findViewById(R.id.progressIcon);
                final Button install = findViewById(R.id.install);
                progressMessage.setText(R.string.installation_confirm_message);
                progressIcon.setVisibility(View.GONE);
                install.setVisibility(View.VISIBLE);
                install.setOnClickListener(v -> {
                    progressMessage.setText(R.string.installing);
                    progressIcon.setVisibility(View.VISIBLE);
                    v.setVisibility(View.GONE);
                    new Thread(() -> {
                        // install package
                        try {
                            installPackage();
                            handler.sendEmptyMessage(installationFinished);
                        } catch (Exception e) {
                            final Message m = new Message();
                            m.what = installationFailed;
                            m.obj = e.getMessage();
                            handler.sendMessage(m);
                        }
                    }).start();
                });
                return true;
            }
            case parsePackageFailed: {
                Toast.makeText(this, getResources().getString(R.string.apk_parse_failed_message)
                        .replace("$msg", msg.obj.toString()), Toast.LENGTH_SHORT).show();
                ((TextView) findViewById(R.id.progressMessage)).setText(R.string.apk_parse_failure);
                ((TextView) findViewById(R.id.hints)).setText(R.string.hints_apk_parse_failure);
                ((TextView) findViewById(R.id.cancel)).setText(R.string.close);
                ((ImageView) findViewById(R.id.progressIcon)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.failed));
                return true;
            }
            case installationFinished: {
                ((TextView) findViewById(R.id.progressMessage)).setText(R.string.apk_installed);
                ((TextView) findViewById(R.id.cancel)).setText(R.string.close);
                ((ImageView) findViewById(R.id.progressIcon)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.success));
                return true;
            }
            case installationFailed: {
                Toast.makeText(this, getResources().getString(R.string.apk_install_failed_message)
                        .replace("$msg", msg.obj.toString()), Toast.LENGTH_SHORT).show();
                ((TextView) findViewById(R.id.progressMessage)).setText(R.string.apk_installation_failure);
                ((TextView) findViewById(R.id.cancel)).setText(R.string.close);
                ((ImageView) findViewById(R.id.progressIcon)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.failed));
                return true;
            }
            default:
                return false;
        }
    }

    private void installPackage() throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final android.content.pm.PackageInstaller pi = getPackageManager().getPackageInstaller();
            final int sessionId = pi.createSession(new android.content.pm.PackageInstaller.SessionParams(android.content.pm.PackageInstaller.SessionParams.MODE_FULL_INSTALL));
            try (android.content.pm.PackageInstaller.Session session = pi.openSession(sessionId)) {
                try (InputStream is = new FileInputStream(path);
                     OutputStream os = session.openWrite("aaa", 0, -1)) {
                    final byte[] c = new byte[8192];
                    int d;
                    while ((d = is.read(c, 0, 8192)) >= 0) {
                        os.write(c, 0, d);
                        os.flush();
                    }
                    session.fsync(os);
                }
                final PendingIntent i = PendingIntent.getBroadcast(this, sessionId, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
                session.commit(i.getIntentSender());
            }
        } else StubApp.getStubInterface().pm().install(path);
    }

    private PackageInfo parsePackage() throws Exception {
        final Intent i = getIntent();
        if (i.getAction().equals(Intent.ACTION_VIEW)) {
            final Uri uri = i.getData();
            if (uri != null) {
                final String path = saveTmpFile(getContentResolver().openInputStream(uri), "tmp.apk").getAbsolutePath();
                final PackageInfo pi = getPackageManager().getPackageArchiveInfo(path, 0);
                if (pi == null)
                    throw new IOException("parse failure");
                createIconThumbnail(this, getPackageManager().getApplicationIcon(pi.applicationInfo));
                this.path = path;
                return pi;
            } else throw new IOException("URI data has not been set");
        } else throw new IOException("Invalid intent action");
    }

    @SuppressWarnings("UnusedReturnValue")
    private static File createIconThumbnail(Context ctx, Drawable dr) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && dr.canApplyTheme())
            dr.applyTheme(ctx.getTheme());
        final Bitmap bm = Bitmap.createBitmap(dr.getIntrinsicWidth(), dr.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas c = new Canvas(bm);
        dr.draw(c);
        final File f = new File(ctx.getCacheDir(), System.currentTimeMillis() + ".png");
        bm.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(f));
        bm.recycle();
        return f;
    }

    @SuppressWarnings("SameParameterValue")
    private File saveTmpFile(InputStream str, String name) throws Exception {
        final File tmp = new File(getCacheDir(), name);
        try (InputStream is = str;
             OutputStream os = new FileOutputStream(tmp, false)) {
            final byte[] c = new byte[8192];
            int d;
            while ((d = is.read(c, 0, 8192)) >= 0) {
                os.write(c, 0, d);
                os.flush();
            }
        }
        return tmp;
    }
}