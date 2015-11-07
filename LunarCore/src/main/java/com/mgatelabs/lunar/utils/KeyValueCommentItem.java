package com.mgatelabs.lunar.utils;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
public class KeyValueCommentItem {
    private @NotNull final String comment;
    private @NotNull final String key;
    private @NotNull final String text;

    public KeyValueCommentItem(@Nullable String comment, @NotNull String key, @NotNull String text) {
        this.comment = comment == null ? "No comment provided by engineer." : comment;
        this.key = key;
        this.text = text;
    }

    @NotNull
    public String getComment() {
        return comment;
    }

    @NotNull
    public String getKey() {
        return key;
    }

    @NotNull
    public String getText() {
        return text;
    }
}
