package com.mindrex.android.appsupport;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.mindrex.mdcfg.MapObject;
import com.mindrex.mdcfg.MdcfgInputStream;
import com.mindrex.mdcfg.MdcfgObject;
import com.mindrex.mdcfg.MdcfgOutputStream;
import com.mindrex.mdcfg.NamedObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MdcfgPrefs extends com.mindrex.util.MdcfgPrefs implements SharedPreferences, SharedPreferences.Editor {
    private final File file;

    public MdcfgPrefs(@NonNull File f) {
        super(fromFrom(f));
        this.file = f;
    }

    private static MapObject fromFrom(File f) {
        if (f.exists())
            try (MdcfgInputStream a = new MdcfgInputStream(new FileInputStream(f), false)) {
                return (MapObject) a.readObject().value();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return new MapObject();
    }

    @Override
    public Map<String, ?> getAll() {
        final Map<String, Object> a = new HashMap<>();
        for (NamedObject b : root)
            a.put(b.name(), b.value().value());
        return a;
    }

    @Override
    public Set<String> getStringSet(@NonNull String key, Set<String> set) {
        try {
            final Set<String> a = new HashSet<>();
            final String b = getString(key, "");
            if (b.length() <= 2)
                return a;
            Collections.addAll(a, b.substring(1, b.length() - 1).split(", "));
            return a;
        } catch (Exception e) {
            return set;
        }
    }

    @Override
    public MdcfgPrefs putStringSet(@NonNull String key, Set<String> set) {
        return putString(key, Arrays.toString(set.toArray(new String[0])));
    }

    @Deprecated
    @Override
    public final Editor edit() {
        return this;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }

    @Override
    public MdcfgPrefs put(@NonNull NamedObject obj) {
        super.put(obj);
        return this;
    }

    @Override
    public MdcfgPrefs put(@NonNull String key, @NonNull MdcfgObject value) {
        super.put(key, value);
        return this;
    }

    @Override
    public MdcfgPrefs putBoolean(@NonNull String key, boolean value) {
        super.putBoolean(key, value);
        return this;
    }

    @Override
    public MdcfgPrefs putString(@NonNull String key, @NonNull String value) {
        super.putString(key, value);
        return this;
    }

    @Override
    public MdcfgPrefs putByte(@NonNull String key, byte value) {
        super.putByte(key, value);
        return this;
    }

    @Override
    public MdcfgPrefs putShort(@NonNull String key, short value) {
        super.putShort(key, value);
        return this;
    }

    @Override
    public MdcfgPrefs putInt(@NonNull String key, int value) {
        super.putInt(key, value);
        return this;
    }

    @Override
    public MdcfgPrefs putLong(@NonNull String key, long value) {
        super.putLong(key, value);
        return this;
    }

    @Override
    public MdcfgPrefs putFloat(@NonNull String key, float value) {
        super.putFloat(key, value);
        return this;
    }

    @Override
    public MdcfgPrefs putDouble(@NonNull String key, double value) {
        super.putDouble(key, value);
        return this;
    }

    @Override
    public MdcfgPrefs remove(@NonNull String key) {
        super.remove(key);
        return this;
    }

    @Override
    public MdcfgPrefs clear() {
        super.clear();
        return this;
    }

    @Override
    public boolean commit() {
        try (MdcfgOutputStream a = new MdcfgOutputStream(new FileOutputStream(file), false)) {
            a.writeElement(new NamedObject("<root>", root));
            a.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void apply() {
        commit();
    }

    @Override
    protected final void finalize() {
        commit();
    }
}