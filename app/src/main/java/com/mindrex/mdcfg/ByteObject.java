package com.mindrex.mdcfg;

import androidx.annotation.NonNull;
import com.mindrex.data.Data;

import java.io.IOException;

public final class ByteObject extends NumberObject {
    private static final long serialVersionUID = -1100900971288187722L;

    public static final byte ID = Data.Byte.id;
    public static final byte LENGTH = Data.Byte.bytes;

    public final byte value;

    public ByteObject(byte value) {
        this.value = value;
    }

    static ByteObject decode(@NonNull MdcfgInputStream a) throws IOException {
        return new ByteObject(a.readByte());
    }

    @Override
    public final Byte value() {
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
        return new byte[]{value};
    }

    @Override
    public final ByteObject clone() {
        return (ByteObject) super.clone();
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof ByteObject)
            return value == ((ByteObject) o).value;
        else return false;
    }

    @Override
    public final int hashCode() {
        return value;
    }
}