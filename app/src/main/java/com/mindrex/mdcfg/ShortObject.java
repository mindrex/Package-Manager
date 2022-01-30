package com.mindrex.mdcfg;

import androidx.annotation.NonNull;
import com.mindrex.data.Data;

import java.io.IOException;

public final class ShortObject extends NumberObject {
    private static final long serialVersionUID = -1266816201062624720L;

    public static final byte ID = Data.Short.id;
    public static final byte LENGTH = Data.Short.bytes;

    public final short value;

    public ShortObject(short value) {
        this.value = value;
    }

    static ShortObject decode(@NonNull MdcfgInputStream a) throws IOException {
        return new ShortObject(a.readShort());
    }

    @Override
    public final Short value() {
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
    public final ShortObject clone() {
        return (ShortObject) super.clone();
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof ShortObject)
            return value == ((ShortObject) o).value;
        else return false;
    }

    @Override
    public final int hashCode() {
        return value;
    }
}