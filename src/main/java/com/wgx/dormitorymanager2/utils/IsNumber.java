package com.wgx.dormitorymanager2.utils;


import java.util.regex.Pattern;

/**
 * author:wgx
 * version:1.0
 */
public class IsNumber {
    public static boolean StringIsNumber(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
