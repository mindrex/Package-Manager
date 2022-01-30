package com.mindrex.mdcfg;

import androidx.annotation.NonNull;

import com.mindrex.io.Stream;

import java.io.IOException;
import java.nio.charset.Charset;

@SuppressWarnings("unused")
final class Mdcfg {
    private Mdcfg() {
    }

    public static final byte[] namespace = {0x7f, 0x6d, 0x64, 0x63, 0x66, 0x67};

    public static final byte[] version = {0, 0, 0, 1};

    public static final byte[] signature = {};

    public static final MdcfgInputStream defIn = null;

    public static final MdcfgOutputStream defOut = null;

    public static final Charset defaultCharset = Stream.defaultCharset;  // avoid format error

    static MdcfgObject decodeObjectWithId(@NonNull MdcfgInputStream a) throws IOException {
        final byte id = a.readByte();
        if (id == ByteObject.ID)
            return ByteObject.decode(a);
        else if (id == ShortObject.ID)
            return ShortObject.decode(a);
        else if (id == IntObject.ID)
            return IntObject.decode(a);
        else if (id == LongObject.ID)
            return LongObject.decode(a);
        else if (id == FloatObject.ID)
            return FloatObject.decode(a);
        else if (id == DoubleObject.ID)
            return DoubleObject.decode(a);
        else if (id == BooleanObject.ID)
            return BooleanObject.decode(a);
        else if (id == StringObject.ID)
            return StringObject.decode(a);
        else if (id == MapObject.ID)
            return MapObject.decode(a);
        else if (id == ArrayObject.ID)
            return ArrayObject.decode(a);
        else if (id == VoidObject.ID)
            return VoidObject.INSTANCE;
        else return RawObject.decode(id, a);
    }
}