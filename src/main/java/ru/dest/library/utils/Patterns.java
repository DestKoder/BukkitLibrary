package ru.dest.library.utils;

import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern INTEGER = Pattern.compile("^[0-9]+$");
    public static final Pattern DOUBLE = Pattern.compile("^[0-9]+\\.[0-9]+$");
    public static final Pattern BOOLEAN = Pattern.compile("^(true|false)$");
    public static final Pattern TIME_UNIT = Pattern.compile("^[0-9]+[smhdwMy]$");
    public static final Pattern IP_V4 = Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");
}
