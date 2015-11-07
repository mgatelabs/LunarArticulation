package com.mgatelabs.lunar.utils;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/26/2015.
 */
public interface ShellInterface {
    void shellExited(@NotNull String reason);

    String promptForString(@Nullable String description);

    int promptForInt(@Nullable String description, int failureValue);

    @Nullable
    AbstractSLV promptForShell(@NotNull List<AbstractSLV> levels);

    int promptForOptions(String ... options);
}
