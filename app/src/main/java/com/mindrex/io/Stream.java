package com.mindrex.io;

import com.mindrex.data.Array;
import com.mindrex.data.Data;

import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.Flushable;
import java.io.IOException;
import java.nio.charset.Charset;

@SuppressWarnings("unused")
public interface Stream extends Flushable, Closeable, DataInput, DataOutput {
    Charset defaultCharset = Charset.forName("UTF-8");

    int read() throws IOException;

    void write(int b) throws IOException;

    @Override
    void flush() throws IOException;

    @Override
    void close() throws IOException;

    default int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    default int read(byte[] b, int off, int len) throws IOException {
        int c = read();
        if (c == -1)
            return -1;
        b[off] = (byte) c;
        int i = 1;
        try {
            for (; i < len; i++) {
                if ((c = read()) == -1)
                    break;
                b[off + i] = (byte) c;
            }
        } catch (Exception e) { //ignored
        }
        return i;
    }

    default byte[] readAllBytes() throws IOException {
        byte[] a = new byte[0];
        final byte[] b = new byte[8192];
        int c;
        while ((c = read(b, 0, b.length)) >= 0)
            a = Array.merge(a, c == b.length ? b : Array.subArray(b, 0, c));
        return a;
    }

    default byte[] readNBytes(int len) throws IOException {
        byte[] a = new byte[0];
        final byte[] b = new byte[8192];
        int c;
        int d = 0;
        while ((c = read(b, 0, b.length)) >= 0) {
            if (d >= len)
                break;
            else {
                a = Array.merge(a, b);
                d += c;
            }
        }
        return a;
    }

    default void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    default void write(byte[] b, int off, int len) throws IOException {
        for (int i = 0; i < len; i++)
            write(b[off + i]);
    }

    default int vread() throws IOException {
        final int a = read();
        if (a < 0)
            throw new EOFException();
        return a;
    }

    default void readFully(byte[] b) throws IOException {
        readFully(b, 0, b.length);
    }

    default void readFully(byte[] b, int off, int len) throws IOException {
        for (int i = 0; i < len; ) {
            int p = read(b, off + i, len - i);
            if (p < 0)
                throw new EOFException();
            i += p;
        }
    }

    @Override
    default int skipBytes(int n) throws IOException {
        return 0;
    }

    @Override
    default boolean readBoolean() throws IOException {
        return vread() != 0;
    }

    @Override
    default byte readByte() throws IOException {
        return (byte) vread();
    }

    @Override
    default int readUnsignedByte() throws IOException {
        return vread();
    }

    @Override
    default char readChar() throws IOException {
        final byte[] a = new byte[2];
        a[0] = (byte) vread();
        a[1] = (byte) vread();
        return Data.toChar(a);
    }

    @Override
    default short readShort() throws IOException {
        final byte[] a = new byte[2];
        a[0] = (byte) vread();
        a[1] = (byte) vread();
        return Data.toShort(a);
    }

    @Override
    default int readUnsignedShort() throws IOException {
        final int a = vread();
        final int b = vread();
        return (a << 8) + b;
    }

    @Override
    default int readInt() throws IOException {
        final byte[] a = new byte[4];
        a[0] = (byte) vread();
        a[1] = (byte) vread();
        a[2] = (byte) vread();
        a[3] = (byte) vread();
        return Data.toInt(a);
    }

    @Override
    default long readLong() throws IOException {
        final byte[] a = new byte[8];
        a[0] = (byte) vread();
        a[1] = (byte) vread();
        a[2] = (byte) vread();
        a[3] = (byte) vread();
        a[4] = (byte) vread();
        a[5] = (byte) vread();
        a[6] = (byte) vread();
        a[7] = (byte) vread();
        return Data.toLong(a);
    }

    @Override
    default float readFloat() throws IOException {
        final byte[] a = new byte[4];
        a[0] = (byte) vread();
        a[1] = (byte) vread();
        a[2] = (byte) vread();
        a[3] = (byte) vread();
        return Data.toFloat(a);
    }

    @Override
    default double readDouble() throws IOException {
        final byte[] a = new byte[8];
        a[0] = (byte) vread();
        a[1] = (byte) vread();
        a[2] = (byte) vread();
        a[3] = (byte) vread();
        a[4] = (byte) vread();
        a[5] = (byte) vread();
        a[6] = (byte) vread();
        a[7] = (byte) vread();
        return Data.toDouble(a);
    }

    @Override
    default String readLine() throws IOException {
        byte[] a = new byte[0];
        while (true) {
            final byte b = (byte) read();
            if (b == '\n' || b < 0)
                break;
            else a = Array.append(a, b);
        }
        return new String(a, 0, a.length, defaultCharset);
    }

    @Override
    default String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    @Override
    default void writeBoolean(boolean v) throws IOException {
        write(v ? 1 : 0);
    }

    @Override
    default void writeByte(int v) throws IOException {
        write(v);
    }

    @Override
    default void writeChar(int v) throws IOException {
        write(Data.toByteArray(v), 0, 2);
    }

    @Override
    default void writeShort(int v) throws IOException {
        write(Data.toByteArray(v), 0, 2);
    }

    @Override
    default void writeInt(int v) throws IOException {
        write(Data.toByteArray(v), 0, 4);
    }

    @Override
    default void writeLong(long v) throws IOException {
        write(Data.toByteArray(v), 0, 8);
    }

    @Override
    default void writeFloat(float v) throws IOException {
        write(Data.toByteArray(v), 0, 4);
    }

    @Override
    default void writeDouble(double v) throws IOException {
        write(Data.toByteArray(v), 0, 8);
    }

    @Override
    default void writeBytes(String s) throws IOException {
        for (int i = 0; i < s.length(); i++)
            write((byte) s.charAt(i));
    }

    @Override
    default void writeChars(String s) throws IOException {
        for (int i = 0; i < s.length(); i++)
            writeChar(s.charAt(i));
    }

    @Override
    default void writeUTF(String s) throws IOException {
        final int a = s.length();
        int b = a;
        for (int i = 0; i < a; i++) {
            final int c = s.charAt(i);
            if (c >= 0x80 || c == 0)
                b += (c >= 0x800) ? 2 : 1;
        }
        final byte[] d;
        d = new byte[b + 2];
        int e = 0;
        d[e++] = (byte) ((b >>> 8) & 0xFF);
        d[e++] = (byte) ((b) & 0xFF);
        int i;
        for (i = 0; i < a; i++) {
            final int c = s.charAt(i);
            if (c >= 0x80 || c == 0)
                break;
            d[e++] = (byte) c;
        }
        for (; i < a; i++) {
            int c = s.charAt(i);
            if (c < 0x80 && c != 0)
                d[e++] = (byte) c;
            else if (c >= 0x800) {
                d[e++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                d[e++] = (byte) (0x80 | ((c >> 6) & 0x3F));
                d[e++] = (byte) (0x80 | ((c) & 0x3F));
            } else {
                d[e++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
                d[e++] = (byte) (0x80 | ((c) & 0x3F));
            }
        }
        write(d, 0, b + 2);
    }

    class InvalidOperationException extends IOException {
        private static final long serialVersionUID = 3331330849425679939L;

        InvalidOperationException() {
        }

        InvalidOperationException(String s) {
            super(s);
        }
    }
}