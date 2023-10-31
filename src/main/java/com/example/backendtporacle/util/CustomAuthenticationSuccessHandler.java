package com.example.backendtporacle.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        // Add a cookie here
        Cookie cookie = new Cookie("region", authentication.getName());
        cookie.setMaxAge(24 * 60 * 60); // Set the cookie's maximum age to 1 day (in seconds)
        response.addCookie(cookie);

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
