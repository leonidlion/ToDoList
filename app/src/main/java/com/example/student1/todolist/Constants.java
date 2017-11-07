package com.example.student1.todolist;

import java.text.SimpleDateFormat;
import java.util.Locale;


public final class Constants {
    private Constants(){}

    private static final String DATE_FORMAT_STRING = "dd MMMM yyyy";
    public static final SimpleDateFormat DATE_FORMAT;

    static {
        DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING, Locale.getDefault());
    }
}
