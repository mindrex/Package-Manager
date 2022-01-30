package com.mindrex.mdcfg;

import androidx.annotation.NonNull;

import com.mindrex.data.Array;
import com.mindrex.data.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class MapObject implements MdcfgObject, Iterable<NamedObject> {
    private static final long serialVersionUID = -1808567353970640366L;
    static final byte ID = -1;

    private NamedObject[] elements;

    public MapObject(@NonNull NamedObject[] elements) {
        this.elements = elements;
    }

    public MapObject(int length) {
        this(new NamedObject[length]);
    }

    public MapObject() {
        this(0);
    }

    static MapObject decode(@NonNull MdcfgInputStream a) throws IOException {
        final int b = a.readInt();
        final NamedObject[] c = new NamedObject[b];
        for (int d = 0; d < b; d++)
            c[d] = NamedObject.decode(a);
        return new MapObject(c);
    }

    @Override
    public final byte id() {
        return ID;
    }

    @Override
    public final NamedObject[] value() {
        return elements;
    }

    @Override
    public final void readObject(@NonNull ObjectInputStream a) throws Throwable {
        a.defaultReadObject();
    }

    @Override
    public final void writeObject(@NonNull ObjectOutputStream a) throws Throwable {
        a.defaultWriteObject();
    }

    public final void put(@NonNull String key, @NonNull MdcfgObject value) {
        put(new NamedObject(key, value));
    }

    public final void put(@NonNull NamedObject value) {
        final int i = indexOf(value.name());
        if (i < 0)
            elements = Array.append(elements, value);
        else elements[i] = value;
    }

    public final void putAll(@NonNull NamedObject[] values) {
        elements = Array.merge(elements, values);
    }

    public final boolean add(@NonNull NamedObject value) {
        elements = Array.append(elements, value);
        return true;
    }

    public final boolean addAll(@NonNull NamedObject[] values) {
        elements = Array.merge(elements, values);
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
    public final Iterator<NamedObject> iterator() {
        return new Iterator<NamedObject>() {
            private int index = 0;

            @Override
            public final boolean hasNext() {
                return index < elements.length;
            }

            @Override
            public final NamedObject next() {
                return elements[index++];
            }
        };
    }

    public NamedObject[] toArray() {
        return elements.clone();
    }

    public final void clear() {
        elements = new NamedObject[0];
    }

    public final boolean contains(@NonNull NamedObject obj) {
        for (NamedObject a : elements)
            if (obj.equals(a))
                return true;
        return false;
    }

    public final boolean contains(@NonNull String key) {
        for (NamedObject a : elements)
            if (a != null && a.name().equals(key))
                return true;
        return false;
    }

    public final int indexOf(@NonNull NamedObject obj) {
        for (int i = 0; i < elements.length; i++)
            if (obj.equals(elements[i]))
                return i;
        return -1;
    }

    public final int indexOf(@NonNull String key) {
        for (int i = 0; i < elements.length; i++)
            if (elements[i] != null && elements[i].name().equals(key))
                return i;
        return -1;
    }

    public final NamedObject get(int index) {
        return elements[index];
    }

    public final NamedObject get(@NonNull String key) {
        return elements[indexOf(key)];
    }

    public final NamedObject set(int index, NamedObject value) {
        final NamedObject a = elements[index];
        elements[index] = value;
        return a;
    }

    public final NamedObject remove(int index) {
        final NamedObject a = elements[index];
        elements = Array.remove(elements, index);
        return a;
    }

    public final NamedObject remove(@NonNull String key) {
        return remove(indexOf(key));
    }

    public final Map<String, MdcfgObject> toMap() {
        final Map<String, MdcfgObject> a = new HashMap<>();
        for (NamedObject b : elements)
            a.put(b.name(), b.value());
        return a;
    }

    @Override
    public final byte[] data() {
        byte[] a = Data.toByteArray(elements.length);
        for (NamedObject b : elements)
            a = Array.merge(a, b.data());
        return a;
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof MapObject)
            return Arrays.equals(elements, ((MapObject) o).elements);
        else return false;
    }

    @Override
    public final int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public final MapObject clone() {
        try {
            return (MapObject) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public final String toString() {
        return Arrays.toString(elements);
    }
}