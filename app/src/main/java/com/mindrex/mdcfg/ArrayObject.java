package com.mindrex.mdcfg;

import androidx.annotation.NonNull;

import com.mindrex.data.Array;
import com.mindrex.data.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class ArrayObject implements MdcfgObject, Iterable<MdcfgObject> {
    private static final long serialVersionUID = 6967286953790607648L;
    static final byte ID = -2;

    private MdcfgObject[] elements;

    public ArrayObject(@NonNull MdcfgObject[] elements) {
        this.elements = elements;
    }

    public ArrayObject(int length) {
        this(new MdcfgObject[length]);
    }

    public ArrayObject() {
        this(0);
    }

    static ArrayObject decode(@NonNull MdcfgInputStream a) throws IOException {
        final int b = a.readInt();
        final MdcfgObject[] c = new MdcfgObject[b];
        for (int i = 0; i < b; i++)
            c[i] = Mdcfg.decodeObjectWithId(a);
        return new ArrayObject(c);
    }

    @Override
    public final byte id() {
        return ID;
    }

    @Override
    public final MdcfgObject[] value() {
        return elements;
    }

    public final boolean add(@NonNull MdcfgObject obj) {
        elements = Array.append(elements, obj);
        return true;
    }

    public final boolean addAll(@NonNull MdcfgObject[] obj) {
        elements = Array.merge(elements, obj);
        return true;
    }

    public final int size() {
        return elements.length;
    }

    public final boolean isEmpty() {
        return elements.length == 0;
    }

    @NonNull
    @Override
    public final Iterator<MdcfgObject> iterator() {
        return new Iterator<MdcfgObject>() {
            private int index = 0;

            @Override
            public final boolean hasNext() {
                return index < elements.length;
            }

            @Override
            public final MdcfgObject next() {
                return elements[index++];
            }
        };
    }

    public final MdcfgObject[] toArray() {
        return elements.clone();
    }

    public final void clear() {
        elements = new MdcfgObject[0];
    }

    public final boolean contains(@NonNull MdcfgObject obj) {
        for (MdcfgObject a : elements)
            if (obj.equals(a))
                return true;
        return false;
    }

    public final int indexOf(@NonNull MdcfgObject obj) {
        for (int i = 0; i < elements.length; i++)
            if (obj.equals(elements[i]))
                return i;
        return -1;
    }

    public final MdcfgObject get(int index) {
        return elements[index];
    }

    public final MdcfgObject set(int index, MdcfgObject obj) {
        final MdcfgObject a = elements[index];
        elements[index] = obj;
        return a;
    }

    public final MdcfgObject remove(int index) {
        final MdcfgObject a = elements[index];
        elements = Array.remove(elements, index);
        return a;
    }

    public final List<MdcfgObject> toList() {
        return new Array<>(elements);
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
    public final byte[] data() {
        byte[] a = Data.toByteArray(elements.length);
        for (MdcfgObject b : elements)
            a = Array.merge(Array.merge(a, new byte[]{b.id()}), b.data());
        return a;
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof ArrayObject)
            return Arrays.equals(elements, ((ArrayObject) o).elements);
        else return false;
    }

    @Override
    public final int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public final ArrayObject clone() {
        try {
            return (ArrayObject) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public final String toString() {
        return Arrays.toString(elements);
    }
}