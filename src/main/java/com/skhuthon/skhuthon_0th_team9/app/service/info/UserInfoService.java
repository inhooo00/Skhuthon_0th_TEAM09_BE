package com.skhuthon.skhuthon_0th_team9.app.service.info;

import com.skhuthon.skhuthon_0th_team9.app.domain.user.User;
import com.skhuthon.skhuthon_0th_team9.app.dto.user.login.UserDto;
import com.skhuthon.skhuthon_0th_team9.app.repository.UserRepository;
import com.skhuthon.skhuthon_0th_team9.global.common.dto.ApiResponseTemplate;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.ErrorCode;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.model.CustomException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoService {
    private final UserRepository userRepository;

    public ApiResponseTemplate<UserDto.UserResponse> getUserInfo(Principal principal){
        // principal 객체의 name을 Long으로 Parse하면 ID가 나오는 것.
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자 : " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        UserDto.UserResponse userResponse = UserDto.UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickName())
                .userLevel(user.getUserAccessLevel().toString())
                .socialTypes(user.getSocialTypes())
                .build();

        return ApiResponseTemplate.<UserDto.UserResponse>builder()
                .status(200)
                .success(true)
                .message("마이페이지 정보 조회 성공")
                .data(userResponse)
                .build();
    }
}
