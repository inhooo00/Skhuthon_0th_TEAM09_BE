package com.skhuthon.skhuthon_0th_team9.app.service.login.certification;

import com.skhuthon.skhuthon_0th_team9.global.common.exception.ErrorCode;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.model.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailCertificationService {

    private final JavaMailSender mailSender;
    private final CertificationCodeSendService certificationCodeSendService;

    public String sendCertificationEmail(String email) {
        String certificationCode = certificationCodeSendService.getCertificationCode(email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("SKHUthon 이메일 인증 코드입니다.");
        message.setText("인증번호: " + certificationCode);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.EMAIL_SEND_FAIL, ErrorCode.EMAIL_SEND_FAIL.getMessage());
        }

        return message.getText();
    }

    public boolean verifyCertificationCode(String email, String inputCode) {
        String code = certificationCodeSendService.getCertificationCode(email);
        return code.equals(inputCode);
    }

    public void deleteCertificationCode(String email) {
        certificationCodeSendService.deleteCertificationCode(email);
    }
}
