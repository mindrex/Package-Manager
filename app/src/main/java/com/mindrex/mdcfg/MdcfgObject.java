package com.mindrex.mdcfg;

import androidx.annotation.NonNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@SuppressWarnings("unused")
public interface MdcfgObject extends Serializable, Cloneable {
    Object value();

    byte id();

    default int length() {
        return data().length;
    }

    byte[] data();

    void readObject(@NonNull ObjectInputStream a) throws Throwable;

    void writeObject(@NonNull ObjectOutputStream a) throws Throwable;

    MdcfgObject clone();

    @Override
    String toString();
}