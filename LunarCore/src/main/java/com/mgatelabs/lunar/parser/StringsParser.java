package com.mgatelabs.lunar.parser;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.utils.Closer;
import com.mgatelabs.lunar.utils.KeyValueCommentItem;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
public class StringsParser {

    public static List<KeyValueCommentItem> parseFile(@NotNull File file) throws IOException {
        return parseStream(new FileInputStream(file));
    }

    public static List<KeyValueCommentItem> parseStream(@NotNull final InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = null;
        final StringBuilder sb = new StringBuilder();
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-16");
            char[] buffer = new char[1024];
            while (inputStreamReader.ready()) {
                int length = inputStreamReader.read(buffer);
                sb.append(buffer, 0, length);
                if (length <= 0) {
                    break;
                }
            }
        } finally {
            Closer.close(inputStreamReader);
        }
        return parseString(sb.toString());
    }



    public static List<KeyValueCommentItem> parseString(@NotNull final String contents) {
        List<KeyValueCommentItem> items = Lists.newArrayList();
        try {

            String lastComment = null;
            List<String> keyText = Lists.newArrayList();
            boolean foundEquals = false;

            final StringBuilder buffer = new StringBuilder();
            int i = 0;
            while (i < contents.length()) {
                String temp = buffer.toString();
                if (temp.endsWith("/*")) {
                    // We found the start of a comment, find the end
                    int endPosition = findEndOfComment(contents, i);
                    lastComment = contents.substring(i, endPosition + 1).trim();
                    i = endPosition + 3;
                    buffer.setLength(0);
                } else if (temp.endsWith("\"") || temp.endsWith("“") || temp.endsWith("”")) {
                    // We found a string
                    int endPosition = findEndOfString(contents, i);
                    String text = contents.substring(i, endPosition + 1).trim();
                    keyText.add(text);
                    if (foundEquals) {
                        if (keyText.size() != 2) {
                            throw new Exception("Found Keys and Values does not equal 2");
                        }
                        foundEquals = false;
                        items.add(new KeyValueCommentItem(lastComment, keyText.get(0), keyText.get(1)));
                        keyText.clear();
                        lastComment = null;
                    }
                    i = endPosition + 2;
                    buffer.setLength(0);
                } else if (temp.endsWith("=")) {
                    if (keyText.size() != 1) {
                        throw new Exception("Found '=' symbol without key string");
                    }
                    foundEquals = true;
                    buffer.setLength(0);
                    i++;
                } else {
                    buffer.append(contents.charAt(i));
                    i++;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            // Nothing
            return Lists.newArrayList();
        }
        return items;
    }

    public static int findEndOfComment(@NotNull final String sample, final int startFrom) throws Exception {
        char lastChar = ' ';
        for (int i = startFrom; i < sample.length(); i++) {
            char current = sample.charAt(i);
            if (current == '/' && lastChar == '*') {
                return i - 2;
            }
            lastChar = current;
        }
        throw new Exception("Unable to find comment end-line");
    }

    public static int findEndOfString(@NotNull final String sample, final int startFrom) throws Exception {
        char lastChar = ' ';
        for (int i = startFrom; i < sample.length(); i++) {
            char current = sample.charAt(i);
            if ((current == '"' || current == '“' || current == '”') && lastChar != '\\') {
                return i - 1;
            }
            lastChar = current;
        }
        throw new Exception("Unable to find end of string");
    }
}
