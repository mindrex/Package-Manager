package com.mindrex.util;

import androidx.annotation.NonNull;

import com.mindrex.mdcfg.ArrayObject;
import com.mindrex.mdcfg.BooleanObject;
import com.mindrex.mdcfg.ByteObject;
import com.mindrex.mdcfg.DoubleObject;
import com.mindrex.mdcfg.FloatObject;
import com.mindrex.mdcfg.IntObject;
import com.mindrex.mdcfg.LongObject;
import com.mindrex.mdcfg.MapObject;
import com.mindrex.mdcfg.MdcfgObject;
import com.mindrex.mdcfg.NamedObject;
import com.mindrex.mdcfg.ShortObject;
import com.mindrex.mdcfg.StringObject;

@SuppressWarnings("unused")
public class MdcfgPrefs {
    protected final MapObject root;

    public MdcfgPrefs(@NonNull MapObject root) {
        this.root = root;
    }

    @NonNull
    public String getString(@NonNull String key) {
        return ((StringObject) root.get(key).value()).value();
    }

    public boolean getBoolean(@NonNull String key) {
        return ((BooleanObject) root.get(key).value()).value;
    }

    public byte getByte(@NonNull String key) {
        return ((ByteObject) root.get(key).value()).value;
    }

    public short getShort(@NonNull String key) {
        return ((ShortObject) root.get(key).value()).value;
    }

    public int getInt(@NonNull String key) {
        return ((IntObject) root.get(key).value()).value;
    }

    public long getLong(@NonNull String key) {
        return ((LongObject) root.get(key).value()).value;
    }

    public float getFloat(@NonNull String key) {
        return ((FloatObject) root.get(key).value()).value;
    }

    public double getDouble(@NonNull String key) {
        return ((DoubleObject) root.get(key).value()).value;
    }

    @NonNull
    public MapObject getMapObject(@NonNull String key) {
        return (MapObject) root.get(key).value();
    }

    @NonNull
    public ArrayObject getArrayObject(@NonNull String key) {
        return (ArrayObject) root.get(key).value();
    }

    @NonNull
    public NamedObject get(@NonNull String key) {
        return root.get(key);
    }

    public MdcfgObject opt(@NonNull String key) {
        try {
            return get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public MapObject optMapObject(@NonNull String key) {
        try {
            return getMapObject(key);
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayObject optArrayObject(@NonNull String key) {
        try {
            return getArrayObject(key);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] getRawData(@NonNull String key) {
        return root.get(key).value().data();
    }

    @NonNull
    public String getString(@NonNull String key, @NonNull String def) {
        try {
            return getString(key);
        } catch (Exception e) {
            putString(key, def);
            return def;
        }
    }

    public boolean getBoolean(@NonNull String key, boolean def) {
        try {
            return getBoolean(key);
        } catch (Exception e) {
            putBoolean(key, def);
            return def;
        }
    }

    public byte getByte(@NonNull String key, byte def) {
        try {
            return getByte(key);
        } catch (Exception e) {
            putByte(key, def);
            return def;
        }
    }

    public short getShort(@NonNull String key, short def) {
        try {
            return getShort(key);
        } catch (Exception e) {
            putShort(key, def);
            return def;
        }
    }

    public int getInt(@NonNull String key, int def) {
        try {
            return getInt(key);
        } catch (Exception e) {
            putInt(key, def);
            return def;
        }
    }

    public long getLong(@NonNull String key, long def) {
        try {
            return getLong(key);
        } catch (Exception e) {
            putLong(key, def);
            return def;
        }
    }

    public float getFloat(@NonNull String key, float def) {
        try {
            return getFloat(key);
        } catch (Exception e) {
            putFloat(key, def);
            return def;
        }
    }

    public double getDouble(@NonNull String key, double def) {
        try {
            return getDouble(key);
        } catch (Exception e) {
            putDouble(key, def);
            return def;
        }
    }

    public boolean contains(@NonNull String key) {
        return root.contains(key);
    }

    public MdcfgPrefs clear() {
        root.clear();
        return this;
    }

    public MdcfgPrefs remove(@NonNull String key) {
        root.remove(key);
        return this;
    }

    public MdcfgPrefs put(@NonNull String key, @NonNull MdcfgObject value) {
        root.put(key, value);
        return this;
    }

    public MdcfgPrefs put(@NonNull NamedObject obj) {
        root.put(obj);
        return this;
    }

    public MdcfgPrefs putString(@NonNull String key, String value) {
        root.put(key, new StringObject(value));
        return this;
    }

    public MdcfgPrefs putBoolean(@NonNull String key, boolean value) {
        root.put(key, new BooleanObject(value));
        return this;
    }

    public MdcfgPrefs putByte(@NonNull String key, byte value) {
        root.put(key, new ByteObject(value));
        return this;
    }

    public MdcfgPrefs putShort(@NonNull String key, short value) {
        root.put(key, new ShortObject(value));
        return this;
    }

    public MdcfgPrefs putInt(@NonNull String key, int value) {
        root.put(key, new IntObject(value));
        return this;
    }

    public MdcfgPrefs putLong(@NonNull String key, long value) {
        root.put(key, new LongObject(value));
        return this;
    }

    public MdcfgPrefs putFloat(@NonNull String key, float value) {
        root.put(key, new FloatObject(value));
        return this;
    }

    public MdcfgPrefs putDouble(@NonNull String key, double value) {
        root.put(key, new DoubleObject(value));
        return this;
    }
}