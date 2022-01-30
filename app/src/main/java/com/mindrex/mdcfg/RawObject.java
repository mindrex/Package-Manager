package com.mindrex.mdcfg;

import androidx.annotation.NonNull;

import com.mindrex.data.Array;
import com.mindrex.data.Data;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Objects;

public class RawObject implements MdcfgObject {
    private static final long serialVersionUID = 5835417211917916698L;

    protected final byte id;
    protected final byte[] data;

    public RawObject(byte id, @NonNull byte[] data) {
        this.id = id;
        this.data = data;
    }

    static RawObject decode(byte id, @NonNull MdcfgInputStream a) throws IOException {
        final int i = a.readInt();
        final byte[] b = new byte[i];
        if (a.read(b, 0, i) < i)
            throw new EOFException();
        return new RawObject(id, b);
    }

    @Override
    public Object value() {
        return data;
    }

    @Override
    public byte id() {
        return id;
    }

    @Override
    public byte[] data() {
        return Array.merge(Data.toByteArray(data.length), data);
    }

    @SuppressWarnings("unused")
    public byte[] rawData() {
        return data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Arrays.hashCode(data));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RawObject)
            return id == ((RawObject) o).id && Arrays.equals(data, ((RawObject) o).data);
        else return false;
    }

    @Override
    public void readObject(@NonNull ObjectInputStream a) throws Throwable {
        a.defaultReadObject();
    }

    @Override
    public void writeObject(@NonNull ObjectOutputStream a) throws Throwable {
        a.defaultWriteObject();
    }

    @Override
    public RawObject clone() {
        try {
            return (RawObject) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return new String(data, Mdcfg.defaultCharset);
    }
}