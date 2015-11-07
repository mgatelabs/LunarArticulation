package com.mgatelabs.lunar.parser;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
public class StringsParserTest {

    @Test
    public void testParseString() throws Exception {
        //Get file from resources folder
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource("samples/Localizable.strings").getFile());
        assertTrue("Should return results", StringsParser.parseFile(file).size() > 0);
    }

    @Test
    public void testFindEndOfComment() throws Exception {
        assertEquals(9, StringsParser.findEndOfComment("/* ASDF12 */", 3));
    }

    @Test
    public void testFindEndOfString() throws Exception {
        assertEquals(21, StringsParser.findEndOfString("\"Hello \\\"Mr Tinsdale\\\"\"", 3));
    }
}