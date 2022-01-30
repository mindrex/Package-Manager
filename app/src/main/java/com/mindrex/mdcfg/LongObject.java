package com.mindrex.mdcfg;

import androidx.annotation.NonNull;
import com.mindrex.data.Data;

import java.io.IOException;

public final class LongObject extends NumberObject {
    private static final long serialVersionUID = 4268156940129364691L;

    public static final byte ID = Data.Long.id;
    public static final byte LENGTH = Data.Long.bytes;

    public final long value;

    public LongObject(long value) {
        this.value = value;
    }

    static LongObject decode(@NonNull MdcfgInputStream a) throws IOException {
        return new LongObject(a.readLong());
    }

    @Override
    public final Long value() {
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
    public final LongObject clone() {
        return (LongObject) super.clone();
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof LongObject)
            return value == ((LongObject) o).value;
        else return false;
    }
}