package com.skhuthon.skhuthon_0th_team9.app.service.login.certification;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CertificationCodeSendServiceImpl implements CertificationCodeSendService{

    @Cacheable(cacheNames = "emailCertificationCode", value = "emailCertificationCode", key = "#email")
    public String getCertificationCode(String email) {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    @Cacheable(cacheNames = "emailCertificationCode", value = "emailCertificationCode", key = "#email")
    public void deleteCertificationCode(String email) {}
}
