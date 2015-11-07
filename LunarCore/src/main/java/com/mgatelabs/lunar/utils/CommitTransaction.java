package com.mgatelabs.lunar.utils;

import com.mgatelabs.lunar.Application;
import com.sun.istack.internal.NotNull;

/**
 * Created by Michael Glen Fuller Jr on 10/26/2015.
 */
public abstract class CommitTransaction {
    public abstract boolean commit(@NotNull final Application app) throws Exception;
}
