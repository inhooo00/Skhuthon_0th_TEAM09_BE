package com.skhuthon.skhuthon_0th_team9.app.service.login.encryption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class EncryptionServiceImpl implements EncryptionService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EncryptionServiceImpl() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encrypt(String input) {
        return bCryptPasswordEncoder.encode(input);
    }

    @Override
    public boolean match(String input, String encrypted) {
        return bCryptPasswordEncoder.matches(input, encrypted);
    }
}
