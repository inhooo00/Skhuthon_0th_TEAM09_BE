package com.skhuthon.skhuthon_0th_team9.global.config;

import com.skhuthon.skhuthon_0th_team9.global.annotation.CurrentUser;
import com.skhuthon.skhuthon_0th_team9.global.exception.ErrorCode;
import com.skhuthon.skhuthon_0th_team9.global.exception.model.CustomException;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                   NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        Principal principal = nativeWebRequest.getUserPrincipal();
        if (principal == null) {
            throw new CustomException(ErrorCode.FORBIDDEN_AUTH_EXCEPTION,
                    ErrorCode.FORBIDDEN_AUTH_EXCEPTION.getMessage());
        }

        return principal.getName();
    }

}
