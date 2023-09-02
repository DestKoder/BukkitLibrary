package ru.dest.library.utils;

import org.jetbrains.annotations.Nullable;

public class Parser {

    public static @Nullable Integer parseInt(String s){
        try {
            return Integer.parseInt(s);
        }catch (NumberFormatException e){
            return null;
        }
    }

    public static @Nullable Short parseShort(String s){
        try {
            return Short.parseShort(s);
        }catch (Exception e){
            return null;
        }
    }

    public static @Nullable Double parseDouble(String s){
        try {
            return Double.parseDouble(s);
        }catch (Exception e){
            return null;
        }
    }

    public static @Nullable Float parseFloat(String s){
        try {
            return Float.parseFloat(s);
        }catch (Exception e){
            return null;
        }
    }
}
