package org.lwstudio.springtodo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppPasswordEncoder {
    private static BCryptPasswordEncoder bCryptEncoder;

    private AppPasswordEncoder() {
    }

    public static synchronized BCryptPasswordEncoder getBcryptEncoder() {
        if (bCryptEncoder == null) {
            bCryptEncoder = new BCryptPasswordEncoder();
        }

        return bCryptEncoder;
    }
}
