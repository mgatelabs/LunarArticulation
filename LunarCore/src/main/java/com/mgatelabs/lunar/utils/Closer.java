package com.mgatelabs.lunar.utils;

import com.sun.istack.internal.Nullable;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
public class Closer {

    public static void close(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void rollback(@Nullable org.hibernate.Transaction closeable) {
        if (closeable != null) {
            try {
                closeable.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
