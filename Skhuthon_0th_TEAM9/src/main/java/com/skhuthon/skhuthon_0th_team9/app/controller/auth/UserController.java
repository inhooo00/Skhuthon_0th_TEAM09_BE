package com.skhuthon.skhuthon_0th_team9.app.controller.auth;

import com.skhuthon.skhuthon_0th_team9.app.dto.user.login.OAuthDto;
import com.skhuthon.skhuthon_0th_team9.app.dto.user.login.UserDto;
import com.skhuthon.skhuthon_0th_team9.app.service.login.LoginService;
import com.skhuthon.skhuthon_0th_team9.app.service.login.UserService;
import com.skhuthon.skhuthon_0th_team9.global.annotation.CurrentUser;
import com.skhuthon.skhuthon_0th_team9.global.annotation.UserLevelCheck;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserDto.SignUpRequest request) {
        userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponse> login(@Valid @RequestBody UserDto.LoginRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }

    @PostMapping("/login/oauth")
    public ResponseEntity<UserDto.LoginResponse> loginOAuth(@Valid @RequestBody OAuthDto.LoginRequest request) {
        return ResponseEntity.ok(loginService.socialLogin(request));
    }

    @GetMapping
    @UserLevelCheck
    public ResponseEntity<UserDto.UserResponse> myPage(@CurrentUser String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/certification/email")
    @UserLevelCheck
    public ResponseEntity<String> emailCertification(@CurrentUser String email) {
        return ResponseEntity.ok(userService.sendCertificationEmail(email));
    }

    @PostMapping("/certification/email/{code}")
    @UserLevelCheck
    public ResponseEntity<String> emailCertification(@CurrentUser String email, @PathVariable String code) {
        return ResponseEntity.ok(userService.verifyCertificationCode(email, code));
    }

    @GetMapping("/duplicate/email")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestBody String email) {
        return ResponseEntity.ok(userService.checkEmailDuplicate(email));
    }

    @GetMapping("/duplicate/nickname")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestBody String nickname) {
        return ResponseEntity.ok(userService.checkNicknameDuplicate(nickname));
    }
}
