package com.android.pm;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DialogActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private ViewGroup view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Transparent);
        view = new RelativeLayout(this);
        dialog = new AlertDialog.Builder(this)
                .setTitle(getTitle())
                .setView(view)
                .show();
        dialog.setOnDismissListener(dialog -> finish());
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
    }

    @Override
    public void onPause() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dialog != null && !dialog.isShowing())
            dialog.show();
    }

    @Override
    public void onDestroy() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void setContentView(@NonNull View v, @Nullable ViewGroup.LayoutParams params) {
        if (params == null) {
            params = v.getLayoutParams();
            if (params == null) {
                params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                v.setLayoutParams(params);
            }
        }
        view.addView(v, 0, params);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return view.findViewById(id);
    }
}