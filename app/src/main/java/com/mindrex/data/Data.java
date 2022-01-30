package com.mindrex.data;

import androidx.annotation.NonNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public enum Data implements Serializable {
    Void((byte) -128, (byte) 0, "void"),
    Byte((byte) 0, (byte) 1, "byte"),
    Char((byte) 1, (byte) 2, "char"),
    Short((byte) 2, (byte) 2, "short"),
    Int((byte) 3, (byte) 4, "int"),
    Long((byte) 4, (byte) 8, "long"),
    Float((byte) 5, (byte) 4, "float"),
    Double((byte) 6, (byte) 8, "double"),
    Boolean((byte) 7, (byte) 1, "boolean");

    public final byte id;
    public final byte bytes;
    public final String name;

    Data(byte id, byte bytes, String name) {
        this.id = id;
        this.bytes = bytes;
        this.name = name;
    }

    private void readObject(@NonNull ObjectInputStream a) throws Exception {
        a.defaultReadObject();
    }

    private void writeObject(@NonNull ObjectOutputStream a) throws Exception {
        a.defaultWriteObject();
    }

    @Override
    public final String toString() {
        return name;
    }

    public static byte[] toByteArray(char a) {
        return new byte[]{
                (byte) (a & 0xff),
                (byte) ((a >> 8) & 0xff)
        };
    }

    public static byte[] toByteArray(short a) {
        return new byte[]{
                (byte) (a & 0xff),
                (byte) ((a >> 8) & 0xff)
        };
    }

    public static byte[] toByteArray(int a) {
        return new byte[]{
                (byte) (a & 0xff),
                (byte) ((a >> 8) & 0xff),
                (byte) ((a >> 16) & 0xff),
                (byte) ((a >> 24) & 0xff)
        };
    }

    public static byte[] toByteArray(long a) {
        return new byte[]{
                (byte) (a & 0xff),
                (byte) ((a >> 8) & 0xff),
                (byte) ((a >> 16) & 0xff),
                (byte) ((a >> 24) & 0xff),
                (byte) ((a >> 32) & 0xff),
                (byte) ((a >> 40) & 0xff),
                (byte) ((a >> 48) & 0xff),
                (byte) ((a >> 56) & 0xff)
        };
    }

    public static byte[] toByteArray(float a) {
        return toByteArray(java.lang.Float.floatToRawIntBits(a));
    }

    public static byte[] toByteArray(double a) {
        return toByteArray(java.lang.Double.doubleToRawLongBits(a));
    }

    public static byte[] toByteArray(boolean a) {
        return new byte[]{(byte) (a ? 1 : 0)};
    }

    public static char toChar(byte[] a) {
        return (char) ((a[0] & 0xff) |
                (a[1] & 0xff) << 8);
    }

    public static short toShort(byte[] a) {
        return (short) ((a[0] & 0xff) |
                (a[1] & 0xff) << 8);
    }

    public static int toInt(byte[] a) {
        return (a[0] & 0xff) |
                (a[1] & 0xff) << 8 |
                (a[2] & 0xff) << 16 |
                (a[3] & 0xff) << 24;
    }

    public static long toLong(byte[] a) {
        return (a[0] & 0xffL) |
                (a[1] & 0xffL) << 8 |
                (a[2] & 0xffL) << 16 |
                (a[3] & 0xffL) << 24 |
                (a[4] & 0xffL) << 32 |
                (a[5] & 0xffL) << 40 |
                (a[6] & 0xffL) << 48 |
                (a[7] & 0xffL) << 56;
    }

    public static float toFloat(byte[] a) {
        return java.lang.Float.intBitsToFloat(toInt(a));
    }

    public static double toDouble(byte[] a) {
        return java.lang.Double.longBitsToDouble(toLong(a));
    }
}