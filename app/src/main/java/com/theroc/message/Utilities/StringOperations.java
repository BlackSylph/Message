package com.theroc.message.Utilities;

public class StringOperations {

    public static String milliToTimeDelay(Long millis, Integer delay) {
        long minute = (millis / (1000 * 60)) % 60;
        long hour = ((millis / (1000 * 60 * 60)) % 24) + 2;
        String time = "";
        if (minute < delay) {
            hour--;
            minute = 60 + (minute - delay);
        } else {
            minute -= delay;
        }
        if (hour == 24) {
            time += "00:";
        } else {
            time += hour + ":";
        }
        if (minute < 10) {
            time += 0;
        }
        time += minute;
        return time;
    }

    public static String sanitize(String string) {
        string = string.toUpperCase();
        string = string.replaceAll("\\n", "");
        string = string.replaceAll("\\r", "");
        return string;
    }
}
