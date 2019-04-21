package com.kamilkorzeniewski.stockcontrol.utils;

import javax.servlet.http.HttpServletRequest;

import static com.kamilkorzeniewski.stockcontrol.utils.ServletRequestUtils.getClientIp;

public class LoggingUtils {
    public static String withIP(HttpServletRequest request, String text) {
        return "[" + getClientIp(request) + "] - " + text;
    }
}
