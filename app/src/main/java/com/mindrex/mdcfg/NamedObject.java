package com.mindrex.mdcfg;

import androidx.annotation.NonNull;

import com.mindrex.data.Array;
import com.mindrex.data.Data;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Objects;

public final class NamedObject implements MdcfgObject, Map.Entry<String, MdcfgObject> {
    private static final long serialVersionUID = -5188372069171684942L;

    private final String name;
    private final MdcfgObject value;

    public NamedObject(String name, MdcfgObject value) {
        this.name = name;
        this.value = value;
    }

    static NamedObject decode(@NonNull MdcfgInputStream a) throws IOException {
        final int b = a.readInt();
        final byte[] c = new byte[b];
        if (a.read(c, 0, b) < b)
            throw new EOFException();
        final String name = new String(c, Mdcfg.defaultCharset);
        return new NamedObject(name, Mdcfg.decodeObjectWithId(a));
    }

    public final String name() {
        return name;
    }

    @Override
    public final MdcfgObject value() {
        return value;
    }

    @Override
    public final byte id() {
        return value.id();
    }

    @Override
    public final byte[] data() {
        byte[] a = name.getBytes(Mdcfg.defaultCharset);
        a = Array.merge(Data.toByteArray(a.length), a);
        byte[] b = Array.merge(new byte[]{value.id()}, value.data());
        return Array.merge(a, b);
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
    public final NamedObject clone() {
        try {
            return (NamedObject) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public final String getKey() {
        return name;
    }

    @Override
    public final MdcfgObject getValue() {
        return value;
    }

    @Override
    public final MdcfgObject setValue(MdcfgObject value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof NamedObject)
            return name.equals(((NamedObject) o).name) && value.equals(((NamedObject) o).value);
        else return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return "{name: \"" + name + "\", value: \"" + value + "\"}";
    }
}