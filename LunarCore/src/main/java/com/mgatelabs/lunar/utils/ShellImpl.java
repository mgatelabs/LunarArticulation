package com.mgatelabs.lunar.utils;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public class ShellImpl {

    private
    @NotNull
    //final Console console;
    final Scanner scanner;

    public ShellImpl() {
        scanner = new Scanner(System.in);
        //this.console = System.console();
    }

    public static String defaultTo(@Nullable String inputValue, @NotNull String defaultValue) {
        if (inputValue == null) {
            return defaultValue;
        }
        inputValue = inputValue.trim();
        if (inputValue.length() == 0) {
            return defaultValue;
        }
        return inputValue;
    }

    public void shellEntered(@NotNull final String title, @NotNull final String shortName) {
        System.out.printf("\n----------------------------------------\nEntering Shell \"%s\" (%s)\n", title, shortName);
        System.out.printf("\n");
    }

    public void shellExited(@NotNull final String reason, @NotNull final String shortName) {
        System.out.printf("\n\nLeaving Shell (%s) - \"%s\" \n", shortName, reason);
        System.out.printf("\n----------------------------------------\n\n");
    }

    public String promptForString(@Nullable String description, @NotNull final String prompt) {
        if (description != null && description.length() > 0) {
            System.out.printf("INFO: %s\n", description);
        }
        System.out.print(prompt + "> ");
        return scanner.nextLine();
    }

    public int promptForInt(@Nullable String description, @NotNull final String prompt, int failureValue) {
        final String input = promptForString(description, prompt);
        if (input.length() == 0) {
            return failureValue;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return failureValue;
        }
    }

    @Nullable
    public AbstractSLV promptForShell(@NotNull final List<AbstractSLV> levels, @NotNull final String prompt) {

        int i = 1;
        for (AbstractSLV level: levels) {
            System.out.printf("SHELL: (%d - %s) %s\n", i, level.getShortName(), level.getTitle());
            i++;
        }
        String option = promptForString("Please enter a position or key, blank to return", prompt);
        if (option == null || option.trim().length() == 0) {
            return null;
        }
        option = option.trim();
        for (AbstractSLV level: levels) {
            if (level.getShortName().equalsIgnoreCase(option)) {
                return level;
            }
        }
        try {
            i = Integer.parseInt(option);
            if (i >= 1 && i <= levels.size()) {
                return levels.get(i-1);
            }
        } catch (NumberFormatException ex) {
            return null;
        }
        return null;
    }

    public int promptForOptions(@NotNull final String prompt, String ... options) {

        int i = 1;
        for (String level: options) {
            System.out.printf("OPT: (%s) %s\n", i, level);
            i++;
        }
        String option = promptForString("Please enter a position, blank to return", prompt);
        if (option == null || option.trim().length() == 0) {
            return -1;
        }
        option = option.trim();
        try {
            i = Integer.parseInt(option);
            if (i >= 1 && i <= options.length) {
                return i - 1;
            }
        } catch (NumberFormatException ex) {
            return -1;
        }
        return -1;
    }

    public void info(@NotNull final String infoMessage) {
        System.out.printf("INFO: %s\n", infoMessage);
    }

    public void error(@NotNull final String infoMessage) {
        System.out.printf("ERROR: %s\n", infoMessage);
    }

}
