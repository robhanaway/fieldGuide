package com.rh.fieldguide.utils.logging;

import java.util.Locale;

public class SystemOutLogging extends Logging {
    public SystemOutLogging() {
        super(false);
    }

    public SystemOutLogging(boolean enabled) {
        super(enabled);
    }

    @Override
    public void v(String tag, String format, Object... objects) {
        print("V", tag, formatString(format,objects));
    }

    @Override
    public void d(String tag, String format, Object... objects) {
        print("D", tag, formatString(format,objects));
    }

    @Override
    public void i(String tag, String format, Object... objects) {
        print("I", tag, formatString(format,objects));
    }

    @Override
    public void w(String tag, String format, Object... objects) {
        print("W", tag, formatString(format,objects));
    }

    @Override
    public void e(String tag, String format, Object... objects) {
        print("E", tag, formatString(format,objects));
    }

    @Override
    public void e(String tag, Throwable t, String format, Object... objects) {
        if (enabled) {
            System.out.println(String.format(Locale.getDefault(), "%s [%s] %s %s",
                    "E", tag,formatString(format,objects), t.getClass()));
            if (t.getStackTrace() != null) {
                for (StackTraceElement stackTrace : t.getStackTrace()) {
                    System.out.println("* " + stackTrace.toString());
                }
            }
        }
    }

    @Override
    public void e(String tag, Throwable t) {
        if (enabled) {
            if (t.getStackTrace() != null) {
                for (StackTraceElement stackTrace : t.getStackTrace()) {
                    System.out.println("* " + stackTrace.toString());
                }
            }
        }
    }

    @Override
    public void r(String tag, String text) {
        print("R", tag, text);
    }

    void print(String level, String tag, String text) {
        if (enabled) {
            System.out.println(String.format(Locale.getDefault(), "%s [%s] %s",
                    level, tag,text));
        }
    }
}
