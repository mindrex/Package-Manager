package com.android.pm;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class CloneableActivity extends Activity implements Cloneable {
    private final long uniqueId = System.currentTimeMillis();

    @NonNull
    private final CloneableContext base = new CloneableContext();
    public CloneableActivity() {  }
    public void attachBaseContext(@NonNull Context base) { this.base.attachBaseContext(base);super.attachBaseContext(this.base); }

    // override to change method visibility
    @Override public void onStart() { super.onStart(); }
    @Override public void onStop() { super.onStop(); }
    @Override public void onCreate(@Nullable Bundle savedInstanceState) { super.onCreate(savedInstanceState); }
    @Override public void onPause() { super.onPause(); }
    @Override public void onResume() { super.onResume(); }
    @Override public void onDestroy() { super.onDestroy(); }

    public int hashCode() { return Long.valueOf(uniqueId).hashCode(); }
    public boolean isClonedInstance() { return uniqueId < 0; }
    @NonNull public Context getBaseContext() { return base; }
    @NonNull public Context getClonedContext() { return base.clone(); }

    @NonNull
    public final CloneableActivity clone() {
        try {
            final CloneableActivity a = (CloneableActivity) super.clone();
            try {
                final Field f = CloneableActivity.class.getDeclaredField("uniqueId");
                f.setAccessible(true);
                f.set(a, -System.currentTimeMillis());
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
            }
            return a;
        } catch (Exception e) {
            //noinspection ConstantConditions
            return null; // internal error
        }
    }

    private static final class CloneableContext extends ContextWrapper implements Cloneable {
        public CloneableContext() { super(null); }
        // change method visibility to public
        public void attachBaseContext(Context base) { super.attachBaseContext(base); }

        @NonNull
        public final Context clone() {
            try {
                return (Context) super.clone();
            } catch (Exception e) {
                //noinspection ConstantConditions
                return null; // internal error
            }
        }
    }
}