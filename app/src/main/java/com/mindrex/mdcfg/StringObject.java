package com.mindrex.mdcfg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mindrex.data.Array;
import com.mindrex.data.Data;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("unused")
public final class StringObject extends RawObject {
    private static final long serialVersionUID = 5782115161462022238L;

    public static final byte ID = -3;

    public StringObject(@Nullable CharSequence b) {
        this(b == null ? "" : b.toString());
    }

    public StringObject(@Nullable String s) {
        this(s == null ? new byte[0] : s.getBytes(Mdcfg.defaultCharset));
    }

    public StringObject(@NonNull char[] b) {
        this(new String(b));
    }

    public StringObject(@NonNull byte[] b) {
        super(ID, b);
    }

    static StringObject decode(@NonNull MdcfgInputStream a) throws IOException {
        final int i = a.readInt();
        final byte[] b = new byte[i];
        if (a.read(b, 0, i) < i)
            throw new EOFException();
        return new StringObject(b);
    }

    @Override
    public final byte[] data() {
        return Array.merge(Data.toByteArray(data.length), data);
    }

    @Override
    public final String value() {
        return new String(data, Mdcfg.defaultCharset);
    }

    @Override
    public final byte id() {
        return ID;
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof StringObject)
            return Arrays.equals(data, ((StringObject) o).data);
        else return false;
    }

    @Override
    public final int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public final StringObject clone() {
        try {
            return (StringObject) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public final String toString() {
        return value();
    }
}