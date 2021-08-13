package dev.nurlan.util;

import javax.servlet.http.HttpServletRequest;

public class Utility {

    public static String getClientIp(HttpServletRequest request) {

        if (request != null) {
            String remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr))
                remoteAddr = request.getRemoteAddr();
            return remoteAddr;
        } else {
            return null;
        }
    }
}
