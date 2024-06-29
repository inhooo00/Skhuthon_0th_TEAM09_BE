package com.skhuthon.skhuthon_0th_team9.app.controller.user4;

import com.skhuthon.skhuthon_0th_team9.app.dto.user.login.UserDto;
import com.skhuthon.skhuthon_0th_team9.app.service.info.UserInfoService;
import com.skhuthon.skhuthon_0th_team9.global.common.dto.ApiResponseTemplate;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user/info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<ApiResponseTemplate<UserDto.UserResponse>> getUserInfo(Principal principal){
        ApiResponseTemplate<UserDto.UserResponse> data = userInfoService.getUserInfo(principal);

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
