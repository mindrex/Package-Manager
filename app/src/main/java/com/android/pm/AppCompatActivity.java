package com.android.pm;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mindrex.android.appsupport.MdcfgPrefs;

import java.io.File;

public class AppCompatActivity extends CloneableActivity {
    @Override
    public void setContentView(int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null, false));
    }

    @Override
    public void setContentView(@NonNull View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(@NonNull View view, @Nullable ViewGroup.LayoutParams params) {
        if (params == null) {
            params = view.getLayoutParams();
            if (params == null) {
                params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(params);
            }
        }
        super.setContentView(view, params);
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return new MdcfgPrefs(new File(getFilesDir(), name));
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}