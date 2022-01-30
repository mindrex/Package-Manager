package com.mindrex.mdcfg;

public final class IllegalFormatException extends RuntimeException {
    private static final long serialVersionUID = 3261942740315980741L;

    IllegalFormatException(String msg) {
        super(msg);
    }

    IllegalFormatException() {
        super();
    }
}