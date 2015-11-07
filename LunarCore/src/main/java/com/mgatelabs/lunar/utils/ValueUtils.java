package com.mgatelabs.lunar.utils;

import com.sun.istack.internal.NotNull;

/**
 * Created by Michael Glen Fuller Jr on 10/31/2015.
 */
public class ValueUtils {

    public static boolean isValidKey(@NotNull final String sample, final int length) {
        if (sample.length() > 1 && sample.length() <= length) {
            return sample.matches("(_a-zA-Z0-9)+]");
        }
        return false;
    }

}
