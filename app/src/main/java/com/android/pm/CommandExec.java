package com.android.pm;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

@SuppressWarnings("unused")
public final class CommandExec {
    public static final int EXEC_FAILED = 0x7f000001;
    public static final int EXEC_INTERRUPTED = 0x7f000002;
    private static final Object LOCK = new Object();

    private final String cmd;

    public CommandExec(String cmd) {
        this.cmd = cmd;
    }

    private static native int system(String s);

    public final int exec() {
        try {
            final Process b = new ProcessBuilder(cmd.split("\\\\s+")).start();
            try {
                return b.waitFor();
            } catch (InterruptedException e) {
                return EXEC_INTERRUPTED;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return EXEC_FAILED;
        }
        //return system(cmd);
    }

    private boolean exec0(Interface i) {
        try {
            final Process a = new ProcessBuilder(cmd.split("\\\\s+")).start();
            i.in = a.getInputStream();
            i.out = new PrintStream(a.getOutputStream());
            i.err = a.getErrorStream();
            i.destroyHook = a::destroy;
            synchronized (LOCK) {
                LOCK.notifyAll();
            }
            try {
                i.code = a.waitFor();
            } catch (InterruptedException e) {
                i.code = EXEC_INTERRUPTED;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            synchronized (LOCK) {
                LOCK.notifyAll();
            }
            return false;
        }
        //return (i.code = system(cmd)) == 0;
    }

    public final boolean exec(Interface i) {
        return exec(i, false);
    }

    public final boolean exec(Interface i, boolean newThread) {
        if (newThread) {
            new Thread(() -> {
                exec0(i);
                synchronized (i.lock) {
                    i.lock.notifyAll();
                }
            }).start();
            synchronized (LOCK) {
                try {
                    LOCK.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else return exec0(i);
    }

    public static final class Interface extends Process implements Closeable {
        private final Object lock = new Object();
        private final int id = (int) System.currentTimeMillis();
        private boolean closed = false;
        private Runnable destroyHook;
        private Integer code;
        private InputStream in;
        private PrintStream out;
        private InputStream err;

        public Interface() {
        }

        private void checkClosed() {
            if (closed)
                throw new IllegalStateException("Interface closed");
        }

        private void sleep() {
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final int waitFor() {
            checkClosed();
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                    return EXEC_INTERRUPTED;
                }
            }
            return code;
        }

        public final int exitCode() {
            checkClosed();
            if (code == null)
                throw new IllegalStateException("Process is running");
            return code;
        }


        public final InputStream in() {
            checkClosed();
            if (in == null)
                throw new IllegalStateException("Process has not started");
            return in;
        }


        public final PrintStream out() {
            checkClosed();
            if (out == null)
                throw new IllegalStateException("Process has not started");
            return out;
        }


        public final InputStream err() {
            checkClosed();
            if (err == null)
                throw new IllegalStateException("Process has not started");
            return err;
        }

        @Override
        public final InputStream getInputStream() {
            return in();
        }

        @Override
        public final OutputStream getOutputStream() {
            return out();
        }

        @Override
        public final InputStream getErrorStream() {
            return err();
        }

        @Override
        public final int exitValue() {
            return exitCode();
        }

        @Override
        public final boolean isAlive() {
            return !closed && code == null;
        }

        @Override
        public final void destroy() {
            checkClosed();
            if (destroyHook == null)
                throw new IllegalStateException("Process has not started");
            destroyHook.run();
        }

        @Override
        public final void close() {
            destroy();
            closed = true;
        }

        protected final Void clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("Function not implemented");
        }

        @Override
        public final boolean equals(Object o) {
            return false;
        }

        @Override
        public final int hashCode() {
            return id;
        }
    }
}