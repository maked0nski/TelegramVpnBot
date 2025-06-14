package com.freedomua.vpn.freedomuavpnbot.util;

public class MarkdownV2Util {
    public static String escape(String text) {
        if (text == null || text.isEmpty()) return text;
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            switch (c) {
                case '_': case '*': case '[': case ']':
                case '(': case ')': case '~': case '`':
                case '>': case '#': case '+': case '-':
                case '=': case '|': case '{': case '}':
                case '.': case '!':
                    sb.append('\\').append(c);
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
}