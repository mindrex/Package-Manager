package com.mindrex.io;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.OutputStream;

@SuppressWarnings("unused")
public class OutputStreamWrapper extends OutputStream implements StreamWrapper {
    protected OutputStream base;

    public OutputStreamWrapper(@Nullable OutputStream base) {
        this.base = base;
    }

    public OutputStreamWrapper(@Nullable Stream stream) {
        this(stream == null ? null : (stream instanceof OutputStream ? (OutputStream) stream : new OutputStream() {
            @Override
            public final void write(int b) throws IOException {
                stream.write(b);
            }

            @Override
            public final void write(@NonNull byte[] b) throws IOException {
                stream.write(b);
            }

            @Override
            public final void write(@NonNull byte[] b, int off, int len) throws IOException {
                stream.write(b, off, len);
            }

            @Override
            public final void flush() throws IOException {
                stream.flush();
            }

            @Override
            public final void close() throws IOException {
                stream.close();
            }
        }));
    }

    @Override
    public void write(int i) throws IOException {
        base.write(i);
    }

    @Override
    public void write(@NonNull byte[] b) throws IOException {
        base.write(b);
    }

    @Override
    public void write(@NonNull byte[] b, int off, int len) throws IOException {
        base.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        base.flush();
    }

    @Override
    public void close() throws IOException {
        base.close();
    }
}