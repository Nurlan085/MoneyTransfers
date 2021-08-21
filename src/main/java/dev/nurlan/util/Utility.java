package dev.nurlan.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    public static long getCountBetweenDays(Date fromDate, Date toDate) {

        long commonDayCount = TimeUnit.DAYS.convert(toDate.getTime() - fromDate.getTime(), TimeUnit.MILLISECONDS);

        return commonDayCount;
    }

}
