package com.freedomua.vpn.freedomuavpnbot.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MarkdownV2UtilTest {

    @Test
    public void testEscapeSimpleText() {
        String input = "Hello world!";
        String expected = "Hello world\\!";
        assertEquals(expected, MarkdownV2Util.escape(input));
    }

    @Test
    public void testEscapeAllSpecialCharacters() {
        String input = "*_[]()~`>#+-=|{}.!\\";
        String expected = "\\*\\_\\[\\]\\(\\)\\~\\`\\>\\#\\+\\-\\=\\|\\{\\}\\.\\!\\\\";

        assertEquals(expected, MarkdownV2Util.escape(input));
    }

    @Test
    public void testEscapeNullAndEmpty() {
        assertNull(MarkdownV2Util.escape(null));
        assertEquals("", MarkdownV2Util.escape(""));
    }
}