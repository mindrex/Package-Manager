package com.mindrex.mdcfg;

import androidx.annotation.NonNull;
import com.mindrex.data.Data;

import java.io.IOException;

public final class DoubleObject extends NumberObject {
    private static final long serialVersionUID = 614051179454743226L;

    public static final byte ID = Data.Double.id;
    public static final byte LENGTH = Data.Double.bytes;

    public final double value;

    public DoubleObject(double value) {
        this.value = value;
    }

    static DoubleObject decode(@NonNull MdcfgInputStream a) throws IOException {
        return new DoubleObject(a.readDouble());
    }

    @Override
    public final Double value() {
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
    public final DoubleObject clone() {
        return (DoubleObject) super.clone();
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof DoubleObject)
            return value == ((DoubleObject) o).value;
        else return false;
    }
}