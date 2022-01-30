package com.mindrex.mdcfg;

import androidx.annotation.NonNull;
import com.mindrex.data.Data;

import java.io.IOException;

public final class IntObject extends NumberObject {
    private static final long serialVersionUID = -2735710667091204323L;

    public static final byte ID = Data.Int.id;
    public static final byte LENGTH = Data.Int.bytes;

    public final int value;

    public IntObject(int value) {
        this.value = value;
    }

    static IntObject decode(@NonNull MdcfgInputStream a) throws IOException {
        return new IntObject(a.readInt());
    }

    @Override
    public final Integer value() {
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
    public final IntObject clone() {
        return (IntObject) super.clone();
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof IntObject)
            return value == ((IntObject) o).value;
        else return false;
    }

    @Override
    public final int hashCode() {
        return value;
    }
}