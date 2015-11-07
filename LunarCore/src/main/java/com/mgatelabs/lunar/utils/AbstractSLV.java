package com.mgatelabs.lunar.utils;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public abstract class AbstractSLV implements ShellInterface {
    private final String title;
    private final String shortName;
    private ShellImpl shell;

    public AbstractSLV(String title, String shortName, ShellImpl shell) {
        this.title = title;
        this.shortName = shortName;
        this.shell = shell;
    }

    public String getTitle() {
        return title;
    }

    public String getShortName() {
        return shortName;
    }

    public ShellImpl getShell() {
        return shell;
    }

    public void run() {
        shell.shellEntered(title, shortName);
    }

    public void shellExited(@NotNull String reason) {
        shell.shellExited(reason, getShortName());
    }

    public String promptForString(@Nullable String description) {
        return shell.promptForString(description, getShortName());
    }

    public int promptForInt(@Nullable String description, int failureValue) {
        return shell.promptForInt(description, getShortName(), failureValue);
    }

    @Nullable
    public AbstractSLV promptForShell(@NotNull List<AbstractSLV> levels) {
        return shell.promptForShell(levels, getShortName());
    }

    public int promptForOptions(String... options) {
        return shell.promptForOptions(getShortName(), options);
    }

    public void info(@NotNull final String infoMessage) {
        shell.info(infoMessage);
    }

    public void error(@NotNull final String infoMessage) {
        shell.error(infoMessage);
    }
}
