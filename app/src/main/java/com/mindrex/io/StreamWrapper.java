package com.mindrex.io;

import java.io.IOException;

public interface StreamWrapper extends Stream {
    @Override
    default int read() throws IOException {
        throw new InvalidOperationException("Not a readable stream");
    }

    @Override
    default void write(int b) throws IOException {
        throw new InvalidOperationException("Not a writeable stream");
    }

    @Override
    default void flush() throws IOException {
        throw new InvalidOperationException();
    }

    @Override
    default void close() throws IOException {
        throw new InvalidOperationException();
    }
}