package com.rh.fieldguide.utils.logging;

import java.util.Locale;

/**
 * Created by rhanaway on 21/06/2018.
 */

public abstract class Logging {
    private final static int MAX_EVENTS = 1000;

    private int maxEvents = MAX_EVENTS;
    protected boolean enabled;

    protected Logging(boolean enabled) {
        this.enabled = enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean isEnabled() {
        return enabled;
    }

    public abstract void v(String tag, String format, Object... objects);
    public abstract void d(String tag, String format, Object... objects);
    public abstract void i(String tag, String format, Object... objects);
    public abstract void w(String tag, String format, Object... objects);
    public abstract void e(String tag, String format, Object... objects);
    public abstract void e(String tag, Throwable t, String format, Object... objects);
    public abstract void e(String tag, Throwable t);
    public abstract void r(String tag, String text);

    protected String formatString(String format, Object... objects) {
        return String.format(Locale.getDefault(), format, objects);
    }



}
