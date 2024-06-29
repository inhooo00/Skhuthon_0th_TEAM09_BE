package com.skhuthon.skhuthon_0th_team9.app.service.login;

import com.skhuthon.skhuthon_0th_team9.app.domain.user.User;
import com.skhuthon.skhuthon_0th_team9.app.dto.user.login.UserDto;
import com.skhuthon.skhuthon_0th_team9.app.repository.UserRepository;
import com.skhuthon.skhuthon_0th_team9.app.service.login.certification.EmailCertificationService;
import com.skhuthon.skhuthon_0th_team9.app.service.login.encryption.EncryptionService;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.ErrorCode;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.model.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final EmailCertificationService emailCertificationService;

    @Transactional
    public void save(UserDto.SignUpRequest request) {

        if (checkEmailDuplicate(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_EMAIL_EXCEPTION,
                    ErrorCode.DUPLICATE_USER_EMAIL_EXCEPTION.getMessage());
        }

        if (checkNicknameDuplicate(request.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_NICKNAME_EXCEPTION,
                    ErrorCode.DUPLICATE_USER_NICKNAME_EXCEPTION.getMessage());
        }

        request.passwordEncryption(encryptionService);

        userRepository.save(User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickName(request.getNickname())
                .build());
    }

    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickName(nickname);
    }

    @Transactional(readOnly = true)
    public UserDto.UserResponse findByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                        ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));

        return UserDto.UserResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickName())
                .userLevel(user.getUserAccessLevel().toString())
                .socialTypes(user.getSocialTypes())
                .build();
    }

    public String sendCertificationEmail(String email) {
        return emailCertificationService.sendCertificationEmail(email);
    }

    public String verifyCertificationCode(String email, String code) {
        if (emailCertificationService.verifyCertificationCode(email, code)) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                            ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));

            user.certify();

            emailCertificationService.deleteCertificationCode(email);

            return "인증에 성공했습니다.";
        }

        throw new CustomException(ErrorCode.FAIL_TO_EMAIL_CERTIFICATION,
                ErrorCode.FAIL_TO_EMAIL_CERTIFICATION.getMessage());
    }
}
