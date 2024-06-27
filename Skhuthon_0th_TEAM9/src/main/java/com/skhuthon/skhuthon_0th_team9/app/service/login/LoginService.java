package com.skhuthon.skhuthon_0th_team9.app.service.login;

import com.skhuthon.skhuthon_0th_team9.app.domain.user.User;
import com.skhuthon.skhuthon_0th_team9.app.domain.user.UserAccessLevel;
import com.skhuthon.skhuthon_0th_team9.app.domain.user.social.SocialConnection;
import com.skhuthon.skhuthon_0th_team9.app.domain.user.social.SocialType;
import com.skhuthon.skhuthon_0th_team9.app.dto.user.login.OAuthDto;
import com.skhuthon.skhuthon_0th_team9.app.dto.user.login.UserDto;
import com.skhuthon.skhuthon_0th_team9.app.repository.SocialConnectionRepository;
import com.skhuthon.skhuthon_0th_team9.app.repository.UserRepository;
import com.skhuthon.skhuthon_0th_team9.app.service.login.encryption.EncryptionService;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.ErrorCode;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.model.CustomException;
import com.skhuthon.skhuthon_0th_team9.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final SocialConnectionRepository socialConnectionRepository;
    private final TokenProvider tokenProvider;

    protected User findUserByValidatingCredentials(UserDto.LoginRequest request) {
        String email = request.getEmail();
        String rawPassword = request.getPassword();

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null || !encryptionService.match(rawPassword, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_PASSWORD_MATCH_EXCEPTION,
                    ErrorCode.INVALID_EMAIL_PASSWORD_MATCH_EXCEPTION.getMessage());
        }

        return user;
    }

    private UserDto.LoginResponse createTokenForUser(User user) {
        String email = user.getEmail();
        UserAccessLevel level = user.getUserAccessLevel();

        String accessToken = tokenProvider.createAccessToken(email, level);
        String refreshToken = tokenProvider.createRefreshToken(email, level);

        return UserDto.LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public UserDto.LoginResponse login(UserDto.LoginRequest request) {
        User user = findUserByValidatingCredentials(request);

        return createTokenForUser(user);
    }

    private boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickName(nickname);
    }

    @Transactional
    public UserDto.LoginResponse socialLogin(OAuthDto.LoginRequest request) {
        SocialType socialType = SocialType.valueOf(request.getSocialType());
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<SocialConnection> socialConnectionOptional = socialConnectionRepository.findByUserAndSocialType(user, socialType);

            if (socialConnectionOptional.isEmpty()) {
                SocialConnection newSocialConnection = SocialConnection.builder()
                        .socialType(socialType)
                        .socialId(request.getSocialId())
                        .user(user)
                        .build();
                user.addSocialConnection(newSocialConnection);
                user.certify();
                userRepository.save(user);
            }

            return createTokenForUser(user);
        }

        if (checkNicknameDuplicate(request.getNickName())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_NICKNAME_EXCEPTION,
                    ErrorCode.DUPLICATE_USER_NICKNAME_EXCEPTION.getMessage());
        }

        User user = User.builder()
                .email(request.getEmail())
                .nickName(request.getNickName())
                .build();

        user.certify();

        SocialConnection socialConnection = socialConnectionRepository.save(SocialConnection.builder()
                .socialType(socialType)
                .socialId(request.getSocialId())
                .user(user)
                .build());

        user.addSocialConnection(socialConnection);

        userRepository.save(user);

        return createTokenForUser(user);
    }
}
