package com.mindrex.mdcfg;

import androidx.annotation.NonNull;

import com.mindrex.data.Data;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class VoidObject implements MdcfgObject {
    private static final long serialVersionUID = 5685857216269636604L;

    public static final byte ID = Data.Void.id;
    public static final byte LENGTH = Data.Void.bytes;
    public static final VoidObject INSTANCE = new VoidObject();

    private VoidObject() {
    }

    @Override
    public final Void value() {
        return null;
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
        return new byte[0];
    }

    @Override
    public final void readObject(@NonNull ObjectInputStream a) throws Exception {
        a.defaultReadObject();
    }

    @Override
    public final void writeObject(@NonNull ObjectOutputStream a) throws Exception {
        a.defaultWriteObject();
    }

    @Override
    public final int hashCode() {
        return 0;
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof VoidObject;
    }

    @Override
    public final VoidObject clone() {
        try {
            return (VoidObject) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public final String toString() {
        return "";
    }
}