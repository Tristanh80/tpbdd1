package com.example.backendtporacle.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.util.UUID;

public class Utils {

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase().replace("-", "");
    }

    public static String obtenirCookieRegion(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String region = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("region")) {
                    region = cookie.getValue();
                }
            }
        }
        return region;
    }

    public static String transformDate(Date str) {
        return "TO_DATE('" + str.toString() + "', 'yyyy-mm-dd')";
    }
}