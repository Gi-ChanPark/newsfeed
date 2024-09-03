package com.sparta.springnewsfeed.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordEncoderUtil(PasswordEncoder passwordEncoder) {

        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encode(String rawPassword) {

        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
