package com.skhuthon.skhuthon_0th_team9.global.common;

import com.skhuthon.skhuthon_0th_team9.app.domain.user.User;
import com.skhuthon.skhuthon_0th_team9.app.repository.UserRepository;
import com.skhuthon.skhuthon_0th_team9.global.annotation.UserLevelCheck;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.ErrorCode;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.model.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class UserLevelCheckInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        UserLevelCheck userLevelCheck = handlerMethod.getMethodAnnotation(UserLevelCheck.class);

        if (userLevelCheck == null) {
            return true;
        }

        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_EXCEPTION,
                    ErrorCode.UNAUTHORIZED_USER_EXCEPTION.getMessage());
        }

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EMAIL_EXCEPTION,
                        ErrorCode.NOT_FOUND_EMAIL_EXCEPTION.getMessage() + "이메일: " + principal.getName()));

        if (user.getUserAccessLevel().ordinal() < userLevelCheck.level().ordinal()) {
            throw new CustomException(ErrorCode.ACCESS_DENIED_EXCEPTION,
                    ErrorCode.ACCESS_DENIED_EXCEPTION.getMessage());
        }

        return true;
    }
}
