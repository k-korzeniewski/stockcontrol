package com.kamilkorzeniewski.stockcontrol.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ServletRequestUtils {

     public static String getClientIp(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
    }
}
