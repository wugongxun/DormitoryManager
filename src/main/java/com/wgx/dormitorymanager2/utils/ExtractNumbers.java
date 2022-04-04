package com.wgx.dormitorymanager2.utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:wgx
 * version:1.0
 */
public class ExtractNumbers {
    public static Integer extractNumbers(String str) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(str);
        return Integer.valueOf(matcher.replaceAll("").trim());
    }
}
