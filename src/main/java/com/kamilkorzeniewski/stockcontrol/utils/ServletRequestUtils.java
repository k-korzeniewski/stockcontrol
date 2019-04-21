package com.kamilkorzeniewski.stockcontrol.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

 class ServletRequestUtils {

     static String getClientIp(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
    }
}
