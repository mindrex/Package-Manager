package com.android.pm;

import android.os.Build;

import androidx.annotation.NonNull;

import com.topjohnwu.superuser.Shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;

public final class CommandUtils {
    private static final Shell sh = Shell.Builder.create()
            .setTimeout(10)
            .setFlags(Shell.NON_ROOT_SHELL)
            .build("sh");

    private static String getMsg(InputStream str) throws Exception {
        final StringBuilder builder = new StringBuilder();
        final byte[] buf = new byte[8192];
        final Charset ch = Charset.forName("UTF-8");
        int d;
        while ((d = str.read(buf, 0, 8192)) >= 0)
            builder.append(new String(buf, 0, d, ch));
        return builder.toString();
    }

    private static Result legacyExec(String cmd) throws Exception {
        final CommandExec.Interface i = new CommandExec.Interface();
        if (!new CommandExec(cmd).exec(i))
            throw new IOException("Command execution failure, command: " + cmd);
        final String msg = getMsg(i.getInputStream());
        final String err = getMsg(i.getErrorStream());
        final int code = i.exitCode();
        final Result r = new Result(cmd, msg, err, code);
        if (code != 0)
            throw new CommandExecException(r);
        else return r;
    }

    private static String parseOut(List<String> s) {
        final StringBuilder b = new StringBuilder();
        for (String str : s)
            b.append(str).append("\n");
        return b.toString();
    }

    private static Result exec0(String cmd) throws Exception {
        final Shell.Result r = sh.newJob().add(cmd).exec();
        final String msg = parseOut(r.getOut());
        final String err = parseOut(r.getErr());
        final int code = r.getCode();
        final Result result = new Result(cmd, msg, err, code);
        if (code != 0)
            throw new CommandExecException(result);
        else return result;
    }

    public static Result exec(String cmd) throws Exception {
        if (Build.VERSION.SDK_INT > 14)
            return exec0(cmd);
        else return legacyExec(cmd);
    }

    public static final class Result implements Cloneable, Serializable {
        private static final long serialVersionUID = -1942296814182070066L;

        public final String cmd;
        public final String msg;
        public final String err;
        public final int code;

        private Result(String cmd, String msg, String err, int code) {
            this.cmd = cmd;
            this.msg = msg;
            this.err = err;
            this.code = code;
        }

        public String[] getOutput() {
            return msg.split("\\r?\\n");
        }

        @Override
        public final Result clone() {
            try {
                return (Result) super.clone();
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }

        @Override
        public final String toString() {
            return "Result{" +
                    "cmd='" + cmd + '\'' +
                    ", msg='" + msg + '\'' +
                    ", err='" + err + '\'' +
                    ", code=" + code +
                    '}';
        }
    }

    public static final class CommandExecException extends IOException {
        private static final long serialVersionUID = -2031973501877910993L;

        public final Result result;

        private CommandExecException(Result r) {
            this.result = r;
        }

        @Override
        public final String getMessage() {
            return "Failed to execute command " + result.cmd +
                    "\n\tError Message: " + result.err +
                    "\n\tError Code: " + result.code;
        }

        @Override
        public final void printStackTrace(@NonNull PrintStream s) {
            super.printStackTrace(s);
            s.print("Addition trace: ");
            s.print("\n\tError message: " + result.err);
            s.print("\n\tError code: " + result.code);
            s.print("\n\tProgram output: " + result.msg);
        }

        @Override
        public void printStackTrace(@NonNull PrintWriter s) {
            super.printStackTrace(s);
            s.print("Addition trace: ");
            s.print("\n\tError message: " + result.err);
            s.print("\n\tError code: " + result.code);
            s.print("\n\tProgram output: " + result.msg);
        }

        @Override
        public String toString() {
            return "java.io.IOException: " + getMessage();
        }
    }
}