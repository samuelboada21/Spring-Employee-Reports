package com.gestion.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author SamuelBoada
 */
public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin21";
        String encodedPassword = encoder.encode(rawPassword);
        
        System.out.println(encodedPassword);
    }
    
}
