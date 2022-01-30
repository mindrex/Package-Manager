package com.mindrex.io;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("unused")
public class InputStreamWrapper extends InputStream implements StreamWrapper {
    protected InputStream base;
    protected final int bufferSize;

    public InputStreamWrapper(@Nullable InputStream base) {
        this.bufferSize = 8192;
        this.base = base;
    }

    public InputStreamWrapper(@Nullable Stream stream) {
        this(stream == null ? null : (stream instanceof InputStream ? (InputStream) stream : new InputStream() {
            @Override
            public final int read() throws IOException {
                return stream.read();
            }

            @Override
            public final int read(@NonNull byte[] b) throws IOException {
                return stream.read(b);
            }

            @Override
            public final int read(@NonNull byte[] b, int off, int len) throws IOException {
                return stream.read(b, off, len);
            }

            @Override
            public final void close() throws IOException {
                stream.close();
            }
        }));
    }

    @Override
    public int read() throws IOException {
        return base.read();
    }

    @Override
    public int read(@NonNull byte[] b) throws IOException {
        return base.read(b);
    }

    @Override
    public int read(@NonNull byte[] b, int off, int len) throws IOException {
        return base.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return base.skip(n);
    }

    @Override
    public boolean markSupported() {
        return base.markSupported();
    }

    @Override
    public void mark(int i) {
        base.mark(i);
    }

    @Override
    public void reset() throws IOException {
        base.reset();
    }

    @Override
    public int available() throws IOException {
        return base.available();
    }

    @Override
    public void close() throws IOException {
        base.close();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public int skipBytes(int i) throws IOException {
        int total = 0;
        int cur;
        for (; total < i && (cur = (int) base.skip(i - total)) > 0; total += cur)
            ;
        return total;
    }
}