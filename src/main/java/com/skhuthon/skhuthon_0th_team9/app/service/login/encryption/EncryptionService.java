package com.skhuthon.skhuthon_0th_team9.app.service.login.encryption;

public interface EncryptionService {

    String encrypt(String input);
    boolean match(String input, String encrypted);
}
