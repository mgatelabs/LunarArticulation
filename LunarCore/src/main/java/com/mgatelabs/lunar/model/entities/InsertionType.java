package com.mgatelabs.lunar.model.entities;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public enum InsertionType {
    SOURCE, // From s standard source code scan, ignores text
    IMPORT, // Bringing in a *.strings file to use as is
    XML // Imported from a language xml file
}
