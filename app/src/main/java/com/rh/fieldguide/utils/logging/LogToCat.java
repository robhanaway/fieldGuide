package com.rh.fieldguide.utils.logging;

import android.util.Log;

/**
 * Created by rhanaway on 26/02/2018.
 */

public class LogToCat extends Logging {


    public LogToCat() {
        this(true);
    }

    public LogToCat(boolean enabled) {
        super(enabled);
    }


    @Override
    public void v(String tag, String format, Object... objects) {
        if (enabled) {
            Log.v(tag, formatString(format, objects));
        }
    }

    @Override
    public void d(String tag, String format, Object... objects) {
        if (enabled) {
            Log.d(tag, formatString(format, objects));
        }
    }

    @Override
    public void i(String tag, String format, Object... objects) {
        if (enabled) {
            Log.i(tag, formatString(format, objects));
        }
    }

    @Override
    public void w(String tag, String format, Object... objects) {
        if (enabled) {
            Log.w(tag, formatString(format, objects));
        }
    }

    @Override
    public void e(String tag, String format, Object... objects) {
        if (enabled) {
            Log.e(tag, formatString(format, objects));
        }
    }

    @Override
    public void e(String tag, Throwable t, String format, Object... objects) {
        if (enabled) {
            Log.e(tag, formatString(format, objects), t);
        }
    }

    @Override
    public void e(String tag, Throwable t) {
        if (enabled) {
            Log.e(tag, "exception", t);
        }
    }

    @Override
    public void r(String tag, String text) {
        if (enabled) {
            Log.d(tag, text);
        }
    }


}
