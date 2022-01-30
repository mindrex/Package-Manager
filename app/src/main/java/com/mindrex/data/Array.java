package com.mindrex.data;

import android.annotation.TargetApi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Array<E> implements List<E>, Set<E>, Cloneable, Serializable {
    private static final long serialVersionUID = -1303159027643608633L;

    public static <E> E[] newInstance(@NonNull Class<E> cls, int length) {
        //return Reflection.newArray(cls, length);
        //noinspection unchecked
        return (E[]) java.lang.reflect.Array.newInstance(cls, length);
    }

    public static <T> void fill(T[] t, @Nullable T val) {
        for (int i = 0; i < t.length; i++)
            t[i] = val;
    }

    public static <T> void fill(T[][] t, @Nullable T val) {
        for (T[] ts : t)
            fill(ts, val);
    }

    public static <T> void fill(T[][][] t, @Nullable T val) {
        for (T[][] ts : t)
            fill(ts, val);
    }

    public static <T> void fill(T[][][][] t, @Nullable T val) {
        for (T[][][] ts : t)
            fill(ts, val);
    }

    public static void fill(byte[] t, byte val) {
        for (int i = 0; i < t.length; i++)
            t[i] = val;
    }

    public static void fill(byte[][] t, byte val) {
        for (byte[] ts : t)
            fill(ts, val);
    }

    public static void fill(byte[][][] t, byte val) {
        for (byte[][] ts : t)
            fill(ts, val);
    }

    public static void fill(byte[][][][] t, byte val) {
        for (byte[][][] ts : t)
            fill(ts, val);
    }

    public static void fill(short[] t, short val) {
        for (int i = 0; i < t.length; i++)
            t[i] = val;
    }

    public static void fill(short[][] t, short val) {
        for (short[] ts : t)
            fill(ts, val);
    }

    public static void fill(short[][][] t, short val) {
        for (short[][] ts : t)
            fill(ts, val);
    }

    public static void fill(short[][][][] t, short val) {
        for (short[][][] ts : t)
            fill(ts, val);
    }

    public static void fill(int[] t, int val) {
        for (int i = 0; i < t.length; i++)
            t[i] = val;
    }

    public static void fill(int[][] t, int val) {
        for (int[] ts : t)
            fill(ts, val);
    }

    public static void fill(int[][][] t, int val) {
        for (int[][] ts : t)
            fill(ts, val);
    }

    public static void fill(int[][][][] t, int val) {
        for (int[][][] ts : t)
            fill(ts, val);
    }

    public static void fill(long[] t, long val) {
        for (int i = 0; i < t.length; i++)
            t[i] = val;
    }

    public static void fill(long[][] t, long val) {
        for (long[] ts : t)
            fill(ts, val);
    }

    public static void fill(long[][][] t, long val) {
        for (long[][] ts : t)
            fill(ts, val);
    }

    public static void fill(long[][][][] t, long val) {
        for (long[][][] ts : t)
            fill(ts, val);
    }

    public static void fill(float[] t, float val) {
        for (int i = 0; i < t.length; i++)
            t[i] = val;
    }

    public static void fill(float[][] t, float val) {
        for (float[] ts : t)
            fill(ts, val);
    }

    public static void fill(float[][][] t, float val) {
        for (float[][] ts : t)
            fill(ts, val);
    }

    public static void fill(float[][][][] t, float val) {
        for (float[][][] ts : t)
            fill(ts, val);
    }

    public static void fill(double[] t, double val) {
        for (int i = 0; i < t.length; i++)
            t[i] = val;
    }

    public static void fill(double[][] t, double val) {
        for (double[] ts : t)
            fill(ts, val);
    }

    public static void fill(double[][][] t, double val) {
        for (double[][] ts : t)
            fill(ts, val);
    }

    public static void fill(double[][][][] t, double val) {
        for (double[][][] ts : t)
            fill(ts, val);
    }

    public static void fill(boolean[] t, boolean val) {
        for (int i = 0; i < t.length; i++)
            t[i] = val;
    }

    public static void fill(boolean[][] t, boolean val) {
        for (boolean[] ts : t)
            fill(ts, val);
    }

    public static void fill(boolean[][][] t, boolean val) {
        for (boolean[][] ts : t)
            fill(ts, val);
    }

    public static void fill(boolean[][][][] t, boolean val) {
        for (boolean[][][] ts : t)
            fill(ts, val);
    }

    public static void fill(char[] t, char val) {
        for (int i = 0; i < t.length; i++)
            t[i] = val;
    }

    public static void fill(char[][] t, char val) {
        for (char[] ts : t)
            fill(ts, val);
    }

    public static void fill(char[][][] t, char val) {
        for (char[][] ts : t)
            fill(ts, val);
    }

    public static void fill(char[][][][] t, char val) {
        for (char[][][] ts : t)
            fill(ts, val);
    }

    public static <E> E[] grow(E[] es, int capacity) {
        if (capacity == es.length)
            return es;
        else {
            final E[] a = (E[]) newInstance(es.getClass().getComponentType(), capacity);
            if (capacity > es.length)
                System.arraycopy(es, 0, a, 0, es.length);
            else if (capacity >= 0)
                System.arraycopy(es, 0, a, 0, capacity);
            return a;
        }
    }

    public static <E> E[] grow(E[] es) {
        return grow(es, es.length + 1);
    }

    public static <E> E[] subArray(E[] es, int start, int end) {
        final E[] a = (E[]) newInstance(es.getClass().getComponentType(), end - start);
        System.arraycopy(es, start, a, 0, a.length);
        return a;
    }

    public static <E> E[] subArray(E[] es, int start) {
        return subArray(es, start, es.length);
    }

    public static byte[] subArray(byte[] es, int start, int end) {
        final byte[] a = new byte[end - start];
        System.arraycopy(es, start, a, 0, a.length);
        return a;
    }

    public static byte[] subArray(byte[] es, int start) {
        return subArray(es, start, es.length);
    }

    public static short[] subArray(short[] es, int start, int end) {
        final short[] a = new short[end - start];
        System.arraycopy(es, start, a, 0, a.length);
        return a;
    }

    public static short[] subArray(short[] es, int start) {
        return subArray(es, start, es.length);
    }

    public static int[] subArray(int[] es, int start, int end) {
        final int[] a = new int[end - start];
        System.arraycopy(es, start, a, 0, a.length);
        return a;
    }

    public static int[] subArray(int[] es, int start) {
        return subArray(es, start, es.length);
    }

    public static long[] subArray(long[] es, int start, int end) {
        final long[] a = new long[end - start];
        System.arraycopy(es, start, a, 0, a.length);
        return a;
    }

    public static long[] subArray(long[] es, int start) {
        return subArray(es, start, es.length);
    }

    public static float[] subArray(float[] es, int start, int end) {
        final float[] a = new float[end - start];
        System.arraycopy(es, start, a, 0, a.length);
        return a;
    }

    public static float[] subArray(float[] es, int start) {
        return subArray(es, start, es.length);
    }

    public static double[] subArray(double[] es, int start, int end) {
        final double[] a = new double[end - start];
        System.arraycopy(es, start, a, 0, a.length);
        return a;
    }

    public static double[] subArray(double[] es, int start) {
        return subArray(es, start, es.length);
    }

    public static boolean[] subArray(boolean[] es, int start, int end) {
        final boolean[] a = new boolean[end - start];
        System.arraycopy(es, start, a, 0, a.length);
        return a;
    }

    public static boolean[] subArray(boolean[] es, int start) {
        return subArray(es, start, es.length);
    }

    public static char[] subArray(char[] es, int start, int end) {
        final char[] a = new char[end - start];
        System.arraycopy(es, start, a, 0, a.length);
        return a;
    }

    public static char[] subArray(char[] es, int start) {
        return subArray(es, start, es.length);
    }

    public static byte[] toPrimitive(Byte[] a) {
        byte[] b = new byte[a.length];
        for (int i = 0; i < b.length; i++)
            b[i] = a[i];
        return b;
    }

    public static short[] toPrimitive(Short[] a) {
        short[] b = new short[a.length];
        for (int i = 0; i < b.length; i++)
            b[i] = a[i];
        return b;
    }

    public static int[] toPrimitive(Integer[] a) {
        int[] b = new int[a.length];
        for (int i = 0; i < b.length; i++)
            b[i] = a[i];
        return b;
    }

    public static long[] toPrimitive(Long[] a) {
        long[] b = new long[a.length];
        for (int i = 0; i < b.length; i++)
            b[i] = a[i];
        return b;
    }

    public static float[] toPrimitive(Float[] a) {
        float[] b = new float[a.length];
        for (int i = 0; i < b.length; i++)
            b[i] = a[i];
        return b;
    }

    public static double[] toPrimitive(Double[] a) {
        double[] b = new double[a.length];
        for (int i = 0; i < b.length; i++)
            b[i] = a[i];
        return b;
    }

    public static boolean[] toPrimitive(Boolean[] a) {
        boolean[] b = new boolean[a.length];
        for (int i = 0; i < b.length; i++)
            b[i] = a[i];
        return b;
    }

    public static char[] toPrimitive(Character[] a) {
        char[] b = new char[a.length];
        for (int i = 0; i < b.length; i++)
            b[i] = a[i];
        return b;
    }

    public static <E> E[] merge(E[] a, E[] b) {
        //noinspection unchecked
        final E[] c = (E[]) newInstance(a.getClass().getComponentType(), a.length + b.length);
        int i = 0;
        for (; i < a.length; i++)
            c[i] = a[i];
        for (; i < c.length; i++)
            c[i] = b[i - a.length];
        return c;
    }

    public static byte[] merge(byte[] a, byte[] b) {
        final byte[] c = new byte[a.length + b.length];
        int i = 0;
        for (; i < a.length; i++)
            c[i] = a[i];
        for (; i < c.length; i++)
            c[i] = b[i - a.length];
        return c;
    }

    public static short[] merge(short[] a, short[] b) {
        final short[] c = new short[a.length + b.length];
        int i = 0;
        for (; i < a.length; i++)
            c[i] = a[i];
        for (; i < c.length; i++)
            c[i] = b[i - a.length];
        return c;
    }

    public static int[] merge(int[] a, int[] b) {
        final int[] c = new int[a.length + b.length];
        int i = 0;
        for (; i < a.length; i++)
            c[i] = a[i];
        for (; i < c.length; i++)
            c[i] = b[i - a.length];
        return c;
    }

    public static long[] merge(long[] a, long[] b) {
        final long[] c = new long[a.length + b.length];
        int i = 0;
        for (; i < a.length; i++)
            c[i] = a[i];
        for (; i < c.length; i++)
            c[i] = b[i - a.length];
        return c;
    }

    public static float[] merge(float[] a, float[] b) {
        final float[] c = new float[a.length + b.length];
        int i = 0;
        for (; i < a.length; i++)
            c[i] = a[i];
        for (; i < c.length; i++)
            c[i] = b[i - a.length];
        return c;
    }

    public static double[] merge(double[] a, double[] b) {
        final double[] c = new double[a.length + b.length];
        int i = 0;
        for (; i < a.length; i++)
            c[i] = a[i];
        for (; i < c.length; i++)
            c[i] = b[i - a.length];
        return c;
    }

    public static boolean[] merge(boolean[] a, boolean[] b) {
        final boolean[] c = new boolean[a.length + b.length];
        int i = 0;
        for (; i < a.length; i++)
            c[i] = a[i];
        for (; i < c.length; i++)
            c[i] = b[i - a.length];
        return c;
    }

    public static char[] merge(char[] a, char[] b) {
        final char[] c = new char[a.length + b.length];
        int i = 0;
        for (; i < a.length; i++)
            c[i] = a[i];
        for (; i < c.length; i++)
            c[i] = b[i - a.length];
        return c;
    }

    public static <E> E[] append(E[] a, @Nullable E b) {
        final E[] c = grow(a);
        c[a.length] = b;
        return c;
    }

    public static byte[] append(byte[] a, byte b) {
        final byte[] c = new byte[a.length + 1];
        System.arraycopy(a, 0, c, 0, a.length);
        c[a.length] = b;
        return c;
    }

    public static short[] append(short[] a, short b) {
        final short[] c = new short[a.length + 1];
        System.arraycopy(a, 0, c, 0, a.length);
        c[a.length] = b;
        return c;
    }

    public static int[] append(int[] a, int b) {
        final int[] c = new int[a.length + 1];
        System.arraycopy(a, 0, c, 0, a.length);
        c[a.length] = b;
        return c;
    }

    public static long[] append(long[] a, long b) {
        final long[] c = new long[a.length + 1];
        System.arraycopy(a, 0, c, 0, a.length);
        c[a.length] = b;
        return c;
    }

    public static float[] append(float[] a, float b) {
        final float[] c = new float[a.length + 1];
        System.arraycopy(a, 0, c, 0, a.length);
        c[a.length] = b;
        return c;
    }

    public static double[] append(double[] a, double b) {
        final double[] c = new double[a.length + 1];
        System.arraycopy(a, 0, c, 0, a.length);
        c[a.length] = b;
        return c;
    }

    public static boolean[] append(boolean[] a, boolean b) {
        final boolean[] c = new boolean[a.length + 1];
        System.arraycopy(a, 0, c, 0, a.length);
        c[a.length] = b;
        return c;
    }

    public static char[] append(char[] a, char b) {
        final char[] c = new char[a.length + 1];
        System.arraycopy(a, 0, c, 0, a.length);
        c[a.length] = b;
        return c;
    }

    public static <E> E[] remove(E[] a, int i) {
        //noinspection unchecked
        final E[] b = (E[]) newInstance(a.getClass().getComponentType(), a.length - 1);
        for (int c = 0; c < a.length; c++) {
            if (c >= b.length)
                break;
            else if (c != i)
                b[c] = a[c];
        }
        return b;
    }

    public static byte[] remove(byte[] a, int i) {
        final byte[] b = new byte[a.length - 1];
        for (int c = 0; c < a.length; c++) {
            if (c >= b.length)
                break;
            else if (c != i)
                b[c] = a[c];
        }
        return b;
    }

    public static short[] remove(short[] a, int i) {
        final short[] b = new short[a.length - 1];
        for (int c = 0; c < a.length; c++) {
            if (c >= b.length)
                break;
            else if (c != i)
                b[c] = a[c];
        }
        return b;
    }

    public static int[] remove(int[] a, int i) {
        final int[] b = new int[a.length - 1];
        for (int c = 0; c < a.length; c++) {
            if (c >= b.length)
                break;
            else if (c != i)
                b[c] = a[c];
        }
        return b;
    }

    public static long[] remove(long[] a, int i) {
        final long[] b = new long[a.length - 1];
        for (int c = 0; c < a.length; c++) {
            if (c >= b.length)
                break;
            else if (c != i)
                b[c] = a[c];
        }
        return b;
    }

    public static float[] remove(float[] a, int i) {
        final float[] b = new float[a.length - 1];
        for (int c = 0; c < a.length; c++) {
            if (c >= b.length)
                break;
            else if (c != i)
                b[c] = a[c];
        }
        return b;
    }

    public static double[] remove(double[] a, int i) {
        final double[] b = new double[a.length - 1];
        for (int c = 0; c < a.length; c++) {
            if (c >= b.length)
                break;
            else if (c != i)
                b[c] = a[c];
        }
        return b;
    }

    public static boolean[] remove(boolean[] a, int i) {
        final boolean[] b = new boolean[a.length - 1];
        for (int c = 0; c < a.length; c++) {
            if (c >= b.length)
                break;
            else if (c != i)
                b[c] = a[c];
        }
        return b;
    }

    public static char[] remove(char[] a, int i) {
        final char[] b = new char[a.length - 1];
        for (int c = 0; c < a.length; c++) {
            if (c >= b.length)
                break;
            else if (c != i)
                b[c] = a[c];
        }
        return b;
    }

    public static <E> E[] clone(E[] a) {
        //noinspection unchecked
        final E[] b = (E[]) newInstance(a.getClass().getComponentType(), a.length);
        System.arraycopy(a, 0, b, 0, b.length);
        return b;
    }

    public static byte[] clone(byte[] a) {
        final byte[] b = new byte[a.length];
        System.arraycopy(a, 0, b, 0, b.length);
        return b;
    }

    public static short[] clone(short[] a) {
        final short[] b = new short[a.length];
        System.arraycopy(a, 0, b, 0, b.length);
        return b;
    }

    public static int[] clone(int[] a) {
        final int[] b = new int[a.length];
        System.arraycopy(a, 0, b, 0, b.length);
        return b;
    }

    public static long[] clone(long[] a) {
        final long[] b = new long[a.length];
        System.arraycopy(a, 0, b, 0, b.length);
        return b;
    }

    public static float[] clone(float[] a) {
        final float[] b = new float[a.length];
        System.arraycopy(a, 0, b, 0, b.length);
        return b;
    }

    public static double[] clone(double[] a) {
        final double[] b = new double[a.length];
        System.arraycopy(a, 0, b, 0, b.length);
        return b;
    }

    public static boolean[] clone(boolean[] a) {
        final boolean[] b = new boolean[a.length];
        System.arraycopy(a, 0, b, 0, b.length);
        return b;
    }

    public static char[] clone(char[] a) {
        final char[] b = new char[a.length];
        System.arraycopy(a, 0, b, 0, b.length);
        return b;
    }

    public static <E> void reverse(E[] a) {
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            final E b = a[l];
            a[l] = a[r];
            a[r] = b;
        }
    }

    public static void reverse(byte[] a) {
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            final byte b = a[l];
            a[l] = a[r];
            a[r] = b;
        }
    }

    public static void reverse(short[] a) {
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            final short b = a[l];
            a[l] = a[r];
            a[r] = b;
        }
    }

    public static void reverse(int[] a) {
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            final int b = a[l];
            a[l] = a[r];
            a[r] = b;
        }
    }

    public static void reverse(long[] a) {
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            final long b = a[l];
            a[l] = a[r];
            a[r] = b;
        }
    }

    public static void reverse(float[] a) {
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            final float b = a[l];
            a[l] = a[r];
            a[r] = b;
        }
    }

    public static void reverse(double[] a) {
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            final double b = a[l];
            a[l] = a[r];
            a[r] = b;
        }
    }

    public static void reverse(boolean[] a) {
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            final boolean b = a[l];
            a[l] = a[r];
            a[r] = b;
        }
    }

    public static void reverse(char[] a) {
        for (int l = 0, r = a.length - 1; l < r; l++, r--) {
            final char b = a[l];
            a[l] = a[r];
            a[r] = b;
        }
    }

    public static <T extends Comparable<? super T>> int compare(T[] a, T[] b) {
        for (int i = 0; i < a.length; i++) {
            if (i >= b.length)
                break;
            else {
                final int c = a[i].compareTo(b[i]);
                if (c != 0)
                    return c;
            }
        }
        return a.length - b.length;
    }

    public static int compare(byte[] a, byte[] b) {
        for (int i = 0; i < a.length; i++) {
            if (i >= b.length)
                break;
            else {
                final int c = Byte.compare(a[i], b[i]);
                if (c != 0)
                    break;
            }
        }
        return a.length - b.length;
    }

    public static int compare(short[] a, short[] b) {
        for (int i = 0; i < a.length; i++) {
            if (i >= b.length)
                break;
            else {
                final int c = Short.compare(a[i], b[i]);
                if (c != 0)
                    break;
            }
        }
        return a.length - b.length;
    }

    public static int compare(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (i >= b.length)
                break;
            else {
                final int c = Integer.compare(a[i], b[i]);
                if (c != 0)
                    break;
            }
        }
        return a.length - b.length;
    }

    public static int compare(long[] a, long[] b) {
        for (int i = 0; i < a.length; i++) {
            if (i >= b.length)
                break;
            else {
                final int c = Long.compare(a[i], b[i]);
                if (c != 0)
                    break;
            }
        }
        return a.length - b.length;
    }

    public static int compare(float[] a, float[] b) {
        for (int i = 0; i < a.length; i++) {
            if (i >= b.length)
                break;
            else {
                final int c = Float.compare(a[i], b[i]);
                if (c != 0)
                    break;
            }
        }
        return a.length - b.length;
    }

    public static int compare(double[] a, double[] b) {
        for (int i = 0; i < a.length; i++) {
            if (i >= b.length)
                break;
            else {
                final int c = Double.compare(a[i], b[i]);
                if (c != 0)
                    break;
            }
        }
        return a.length - b.length;
    }

    public static int compare(boolean[] a, boolean[] b) {
        for (int i = 0; i < a.length; i++) {
            if (i >= b.length)
                break;
            else {
                final int c = Boolean.compare(a[i], b[i]);
                if (c != 0)
                    break;
            }
        }
        return a.length - b.length;
    }

    public static int compare(char[] a, char[] b) {
        for (int i = 0; i < a.length; i++) {
            if (i >= b.length)
                break;
            else {
                final int c = Character.compare(a[i], b[i]);
                if (c != 0)
                    break;
            }
        }
        return a.length - b.length;
    }

    public static <E> boolean equals(E[] a, E[] b) {
        if (a.length != b.length)
            return false;
        else for (int i = 0; i < a.length; i++)
            if (!equals(a[i], b[i]))
                return false;
        return true;
    }

    public static boolean equals(byte[] a, byte[] b) {
        return compare(a, b) == 0;
    }

    public static boolean equals(short[] a, short[] b) {
        return compare(a, b) == 0;
    }

    public static boolean equals(int[] a, int[] b) {
        return compare(a, b) == 0;
    }

    public static boolean equals(long[] a, long[] b) {
        return compare(a, b) == 0;
    }

    public static boolean equals(float[] a, float[] b) {
        return compare(a, b) == 0;
    }

    public static boolean equals(double[] a, double[] b) {
        return compare(a, b) == 0;
    }

    public static boolean equals(boolean[] a, boolean[] b) {
        return compare(a, b) == 0;
    }

    public static boolean equals(char[] a, char[] b) {
        return compare(a, b) == 0;
    }

    public static Object[] from(Iterable<?> collection) {
        return from(collection.iterator());
    }

    public static Object[] from(Iterator<?> iterator) {
        Object[] a = new Object[0];
        while (iterator.hasNext())
            a = append(a, iterator.next());
        return a;
    }

    public static <E> E[] from(Iterable<? extends E> collection, Class<E> cls) {
        return from(collection.iterator(), cls);
    }

    public static <E> E[] from(Iterator<? extends E> iterator, Class<E> cls) {
        E[] a = newInstance(cls, 0);
        while (iterator.hasNext())
            a = Array.append(a, iterator.next());
        return a;
    }

    @SafeVarargs
    public static <E> List<E> list(E... items) {
        return new Array<>(items);
    }

    public static List<Byte> list(byte... v) {
        final Byte[] a = new Byte[v.length];
        for (int i = 0; i < v.length; i++)
            a[i] = v[i];
        return new Array<>(a);
    }

    public static List<Short> list(short... v) {
        final Short[] a = new Short[v.length];
        for (int i = 0; i < v.length; i++)
            a[i] = v[i];
        return new Array<>(a);
    }

    public static List<Integer> list(int... v) {
        final Integer[] a = new Integer[v.length];
        for (int i = 0; i < v.length; i++)
            a[i] = v[i];
        return new Array<>(a);
    }

    public static List<Long> list(long... v) {
        final Long[] a = new Long[v.length];
        for (int i = 0; i < v.length; i++)
            a[i] = v[i];
        return new Array<>(a);
    }

    public static List<Float> list(float... v) {
        final Float[] a = new Float[v.length];
        for (int i = 0; i < v.length; i++)
            a[i] = v[i];
        return new Array<>(a);
    }

    public static List<Double> list(double... v) {
        final Double[] a = new Double[v.length];
        for (int i = 0; i < v.length; i++)
            a[i] = v[i];
        return new Array<>(a);
    }

    public static List<Boolean> list(boolean... v) {
        final Boolean[] a = new Boolean[v.length];
        for (int i = 0; i < v.length; i++)
            a[i] = v[i];
        return new Array<>(a);
    }

    public static List<Character> list(char... v) {
        final Character[] a = new Character[v.length];
        for (int i = 0; i < v.length; i++)
            a[i] = v[i];
        return new Array<>(a);
    }

    private static boolean equals(Object a, Object b) {
        if (a == null)
            return b == null;
        else if (b == null)
            return false;
        return a.equals(b);
    }

    public static native int length(Object array);

    public static native <T> T get(Object array, int index);

    @SuppressWarnings("rawtypes")
    public static Class elementType(Object array) {
        return array.getClass().getComponentType();
    }

    protected E[] data;
    protected final Class<E> ref;

    public Array(@NonNull E[] data) {
        this.data = data;
        //noinspection unchecked
        this.ref = (Class<E>) data.getClass().getComponentType();
    }

    public Array(@NonNull Collection<E> data, @NonNull Class<E> reference) {
        this(data.toArray(newInstance(reference, 0)));
    }

    public Array(@NonNull Class<E> reference) {
        this(reference, 0);
    }

    public Array(@NonNull Class<E> reference, int length) {
        this.data = newInstance(reference, length);
        this.ref = reference;
    }

    @Override
    public int size() {
        return data.length;
    }

    @Override
    public boolean isEmpty() {
        return data.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (E e : data)
            if (e == o)
                return true;
        return false;
    }

    @Override
    public ArrayIterator<E> iterator() {
        return new ArrayIterator<>(this);
    }

    @Override
    public E[] toArray() {
        return data.clone();
    }

    public E[] arrayData() {
        return data;
    }

    @Deprecated
    public <T> T[] toArray(@NonNull T[] a) {
        //noinspection unchecked
        return (T[]) data.clone();
    }

    @Override
    public boolean add(@Nullable E e) {
        data = append(data, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        data = remove(data, indexOf(o));
        return true;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        for (Object a : c)
            if (!contains(a))
                return false;
        return true;
    }

    public boolean containsAll(@NonNull E[] a) {
        for (E e : a)
            if (!contains(e))
                return false;
        return true;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends E> c) {
        for (E a : c)
            data = append(data, a);
        return true;
    }

    public boolean addAll(@NonNull E[] a) {
        data = merge(data, a);
        return true;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends E> c) {
        final Object[] a = c.toArray();
        E[] b = newInstance(ref, data.length + a.length);
        final E[] before = subArray(data, 0, index);
        final E[] after = subArray(data, index + a.length);
        final int e = before.length + a.length;
        for (int i = 0; i < b.length; i++) {
            if (i < index)
                b[i] = before[i];
            else {
                final int d = i - before.length;
                if (d < a.length)
                    //noinspection unchecked
                    b[i] = (E) a[d];
                else
                    b[i] = after[i - e];
            }
        }
        data = b;
        return true;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        for (Object a : c) {
            if (contains(a)) {
                remove(indexOf(a));
            }
        }
        return true;
    }

    public boolean removeAll(@NonNull E[] a) {
        for (Object b : a) {
            if (contains(b)) {
                remove(indexOf(b));
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        for (E a : data) {
            if (!c.contains(a))
                remove(a);
        }
        return true;
    }

    public boolean retainAll(@NonNull E[] a) {
        final Array<E> c = new Array<>(a);
        for (E b : data) {
            if (!c.contains(b))
                remove(b);
        }
        return true;
    }

    @Override
    public void clear() {
        data = newInstance(ref, 0);
    }

    @Override
    public E get(int index) {
        return data[index];
    }

    @Override
    public E set(int index, E element) {
        final E a = data[index];
        data[index] = element;
        return a;
    }

    @Override
    public void add(int index, E element) {
        System.arraycopy(data, index, data, index + 1, size() - index);
        data[index] = element;
    }

    @Override
    public E remove(int index) {
        final E a = data[index];
        data = remove(data, index);
        return a;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < data.length; i++) {
            if (equals(data[i], o))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = data.length - 1; i >= 0; i--) {
            if (equals(data[i], o))
                return i;
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ArrayIterator<>(this);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ArrayIterator<>(this, index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new Array<>(subArray(data, fromIndex, toIndex));
    }

    @TargetApi(24)
    @Override
    public void forEach(@NonNull Consumer<? super E> action) {
        for (E a : data)
            action.accept(a);
    }

    @Override
    public void sort(@Nullable Comparator<? super E> c) {
        if (c == null)
            sort();
        else Arrays.sort(data, c);
    }

    public void sort() {
        if (ref.isAssignableFrom(Comparable.class))
            Arrays.sort(data);
        else throw new UnsupportedOperationException("Not a comparable data type");
    }

    @TargetApi(24)
    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(data, Spliterator.ORDERED);
    }

    @Override
    public Array<E> clone() {
        try {
            //noinspection unchecked
            return (Array<E>) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(ref, Arrays.hashCode(data));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Array)
            return ref.equals(((Array<?>) o).ref) && Arrays.equals(data, ((Array<?>) o).data);
        else return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}