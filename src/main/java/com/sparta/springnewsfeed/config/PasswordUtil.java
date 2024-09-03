package com.sparta.springnewsfeed.config;

public class PasswordUtil {

    public String encode(String rawPassword) {
        return rawPassword;
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
