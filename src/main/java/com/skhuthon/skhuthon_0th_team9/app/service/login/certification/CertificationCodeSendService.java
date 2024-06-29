package com.skhuthon.skhuthon_0th_team9.app.service.login.certification;


public interface CertificationCodeSendService {
    String getCertificationCode(String email);
    void deleteCertificationCode(String email);
}
