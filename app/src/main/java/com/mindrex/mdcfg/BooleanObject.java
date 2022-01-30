package com.mindrex.mdcfg;

import androidx.annotation.NonNull;
import com.mindrex.data.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class BooleanObject implements MdcfgObject {
    private static final long serialVersionUID = -6216965264135341827L;

    public static final byte ID = Data.Boolean.id;
    public static final byte LENGTH = Data.Boolean.bytes;

    public final boolean value;

    public BooleanObject(boolean value) {
        this.value = value;
    }

    static BooleanObject decode(@NonNull MdcfgInputStream a) throws IOException {
        return new BooleanObject(a.readByte() != 0);
    }

    @Override
    public final Boolean value() {
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
    public final void readObject(@NonNull ObjectInputStream a) throws Throwable {
        a.defaultReadObject();
    }

    @Override
    public final void writeObject(@NonNull ObjectOutputStream a) throws Throwable {
        a.defaultWriteObject();
    }

    @Override
    public final BooleanObject clone() {
        try {
            return (BooleanObject) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof BooleanObject)
            return value == ((BooleanObject) o).value;
        else return false;
    }

    @Override
    public final int hashCode() {
        return value().hashCode();
    }

    @Override
    public final String toString() {
        return value().toString();
    }
}