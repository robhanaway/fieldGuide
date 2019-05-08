package com.rh.fieldguide.utils.logging;


public class DudLogging extends Logging {

    public DudLogging() {
        super(false);
    }

    public DudLogging(boolean enabled) {
        super(enabled);
    }

    @Override
    public void v(String tag, String format, Object... objects) {
        //Unused
    }

    @Override
    public void d(String tag, String format, Object... objects) {
        //Unused
    }

    @Override
    public void i(String tag, String format, Object... objects) {
        //Unused
    }

    @Override
    public void w(String tag, String format, Object... objects) {
        //Unused
    }

    @Override
    public void e(String tag, String format, Object... objects) {
        //Unused
    }

    @Override
    public void e(String tag, Throwable t, String format, Object... objects) {
        //Unused
    }

    @Override
    public void e(String tag, Throwable t) {
        //Unused
    }

    @Override
    public void r(String tag, String text) {
        //Unused
    }
}