package com.mindrex.mdcfg;

import androidx.annotation.NonNull;
import com.mindrex.io.OutputStreamWrapper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public final class MdcfgOutputStream extends OutputStreamWrapper {
    public MdcfgOutputStream(@NonNull OutputStream base, boolean compress) {
        super(compress ? new DeflaterOutputStream(base, new Deflater(7, true), 512) : base);
    }

    private void writeNamespace() throws IOException {
        write(Mdcfg.namespace, 0, Mdcfg.namespace.length);
    }

    private void writeFormatVersion() throws IOException {
        write(Mdcfg.version, 0, 4);
    }

    private void writeSignature() {
    }

    public final void writeElement(@NonNull NamedObject obj) throws IOException {
        writeNamespace();
        writeFormatVersion();
        writeSignature();
        write(obj.data());
    }
}