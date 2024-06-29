package com.skhuthon.skhuthon_0th_team9.app.dto.user.login;

import com.skhuthon.skhuthon_0th_team9.app.domain.user.UserAccessLevel;
import com.skhuthon.skhuthon_0th_team9.app.service.login.encryption.EncryptionService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpRequest {

        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "정확한 이메일 주소를 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        private String password;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
        private String nickname;

        @Builder
        public SignUpRequest(String email, String password, String nickname) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }

        public void passwordEncryption(EncryptionService encryptionService) {
            this.password = encryptionService.encrypt(password);
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginRequest {

        @NotBlank(message = "이메일 주소를 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;

        public void passwordEncryption(EncryptionService encryptionService) {
            this.password = encryptionService.encrypt(password);
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginResponse {

        private String accessToken;
        private String refreshToken;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserResponse {

        private Long id;

        private String email;

        private String nickname;

        private String userLevel;

        private String[] socialTypes;

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserSearchRequest {

        private Long id;

        private String email;

        private String nickname;

        private UserAccessLevel userAccessLevel;

        @Builder
        public UserSearchRequest(Long id, String email, String nickname, UserAccessLevel userAccessLevel) {
            this.id = id;
            this.email = email;
            this.nickname = nickname;
            this.userAccessLevel = userAccessLevel;
        }

    }
}
