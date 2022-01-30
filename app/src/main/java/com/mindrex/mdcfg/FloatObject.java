package com.mindrex.mdcfg;

import androidx.annotation.NonNull;
import com.mindrex.data.Data;

import java.io.IOException;

public final class FloatObject extends NumberObject {
    private static final long serialVersionUID = 8973792708344588899L;

    public static final byte ID = Data.Float.id;
    public static final byte LENGTH = Data.Float.bytes;

    public final float value;

    public FloatObject(float value) {
        this.value = value;
    }

    static FloatObject decode(@NonNull MdcfgInputStream a) throws IOException {
        return new FloatObject(a.readFloat());
    }

    @Override
    public final Float value() {
        return value;
    }

    @Override
    public final byte id() {
        return ID;
    }

    @Override
    public final int length() {
        return LENGTH;
    }

    @Override
    public final byte[] data() {
        return Data.toByteArray(value);
    }

    @Override
    public final FloatObject clone() {
        return (FloatObject) super.clone();
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof FloatObject)
            return value == ((FloatObject) o).value;
        else return false;
    }
}