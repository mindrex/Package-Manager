package com.mindrex.mdcfg;

import androidx.annotation.NonNull;

import com.mindrex.data.Data;
import com.mindrex.io.InputStreamWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public final class MdcfgInputStream extends InputStreamWrapper {
    public MdcfgInputStream(@NonNull InputStream base, boolean compress) {
        super(compress ? new InflaterInputStream(base, new Inflater(true), 512) : base);
    }

    private void verifyNamespace() throws IOException {
        final byte[] a = new byte[Mdcfg.namespace.length];
        final int b = read(a, 0, a.length);
        if (b != a.length)
            throw new IllegalFormatException("Invalid namespace length: " + b);
        for (int i = 0; i < a.length; i++)
            if (a[i] != Mdcfg.namespace[i])
                throw new IllegalFormatException("Invalid namespace: " + new String(a, Mdcfg.defaultCharset));
    }

    private void verifyFormatVersion() throws IOException {
        final byte[] a = new byte[Mdcfg.version.length];
        final int b = read(a, 0, a.length);
        if (b != a.length)
            throw new IllegalFormatException("Invalid length: " + b);
        final int d = Data.toInt(a);
        final int c = Data.toInt(Mdcfg.version);
        if (d > c)
            throw new IllegalFormatException("Unsupported format version: " + b + " (Allowed: equals or less than " + c + ")");
    }

    private void verifySignature() {
    }

    @NonNull
    public final NamedObject readObject() throws IOException {
        verifyNamespace();
        verifyFormatVersion();
        verifySignature();
        return NamedObject.decode(this);
    }
}