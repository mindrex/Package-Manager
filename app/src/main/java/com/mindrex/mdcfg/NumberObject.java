package com.mindrex.mdcfg;

import androidx.annotation.NonNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class NumberObject extends Number implements MdcfgObject {
    private static final long serialVersionUID = -5445317188764871374L;

    NumberObject() {
    }

    @Override
    public abstract Number value();

    @Override
    public byte byteValue() {
        return value().byteValue();
    }

    @Override
    public short shortValue() {
        return value().shortValue();
    }

    @Override
    public int intValue() {
        return value().intValue();
    }

    @Override
    public long longValue() {
        return value().longValue();
    }

    @Override
    public float floatValue() {
        return value().floatValue();
    }

    @Override
    public double doubleValue() {
        return value().doubleValue();
    }

    public NumberObject clone() {
        try {
            return (NumberObject) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void readObject(@NonNull ObjectInputStream a) throws Exception {
        a.defaultReadObject();
    }

    @Override
    public void writeObject(@NonNull ObjectOutputStream a) throws Exception {
        a.defaultWriteObject();
    }

    @Override
    public int hashCode() {
        return value().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NumberObject)
            return value().equals(((NumberObject) obj).value());
        else return false;
    }

    @Override
    public String toString() {
        return value().toString();
    }
}