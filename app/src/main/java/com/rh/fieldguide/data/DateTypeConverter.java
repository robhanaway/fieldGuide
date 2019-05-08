package com.rh.fieldguide.data;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by rhanaway on 20/02/2018.
 */

public class DateTypeConverter {
    private DateTypeConverter() {
        //Unused
    }

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
