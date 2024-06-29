package com.skhuthon.skhuthon_0th_team9.app.controller.auth;

import com.skhuthon.skhuthon_0th_team9.app.dto.user.login.OAuthDto;
import com.skhuthon.skhuthon_0th_team9.app.dto.user.login.UserDto;
import com.skhuthon.skhuthon_0th_team9.app.service.login.LoginService;
import com.skhuthon.skhuthon_0th_team9.app.service.login.UserService;
import com.skhuthon.skhuthon_0th_team9.global.annotation.CurrentUser;
import com.skhuthon.skhuthon_0th_team9.global.annotation.UserLevelCheck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "로그인/회원가입", description = "로그인/회원가입을 담당하는 api그룹")
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("/signup")
    @Operation(
            summary = "회원가입",
            description = "사용자 정보를 받아와서 DB에 저장하고 회원가입을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "회원가입 성공"),
                    @ApiResponse(responseCode = "403", description = "URL문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의")
            }
    )
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserDto.SignUpRequest request) {
        userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "사용자의 이메일, 비밀번호를 받아와서 암호화되어서 저장된 Password와 매칭되는지를 검사하고 로그인을 통해서 갱신된 refreshToken, accessToken을 반환합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 암호 입력"),
                    @ApiResponse(responseCode = "404", description = "이메일을 찾을수 없거나, 잘못된 값을 입력"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의")
            }
    )
    public ResponseEntity<UserDto.LoginResponse> login(@Valid @RequestBody UserDto.LoginRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }

    @PostMapping("/login/oauth")
    @Operation(
            summary = "소셜 로그인",
            description = "소셜 로그인을 통해서 로그인을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 암호 입력"),
                    @ApiResponse(responseCode = "404", description = "이메일을 찾을수 없거나, 잘못된 값을 입력"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의")
            }
    )
    public ResponseEntity<UserDto.LoginResponse> loginOAuth(@Valid @RequestBody OAuthDto.LoginRequest request) {
        return ResponseEntity.ok(loginService.socialLogin(request));
    }

    @GetMapping
    @UserLevelCheck
    @Operation(
            summary = "마이페이지",
            description = "자신의 정보를 조회하는 API입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "URL문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<UserDto.UserResponse> myPage(@CurrentUser String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/certification/email")
    @UserLevelCheck
    @Operation(
            summary = "이메일 인증",
            description = "이메일 인증을 진행하는 API입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이메일 인증 성공"),
                    @ApiResponse(responseCode = "403", description = "URL문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<String> emailCertification(@CurrentUser String email) {
        return ResponseEntity.ok(userService.sendCertificationEmail(email));
    }

    @PostMapping("/certification/email/{code}")
    @UserLevelCheck
    @Operation(
            summary = "이메일 인증 코드 확인",
            description = "이메일 인증 코드를 확인하는 API입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이메일 인증 코드 확인 성공"),
                    @ApiResponse(responseCode = "403", description = "URL문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<String> emailCertification(@CurrentUser String email, @PathVariable String code) {
        return ResponseEntity.ok(userService.verifyCertificationCode(email, code));
    }

    @GetMapping("/duplicate/email")
    @Operation(
            summary = "이메일 중복검사",
            description = "사용자의 이메일을 중복인지 검사합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "중복검사 성공"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의 or json에 값이 없는 경우")
            }
    )
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestBody String email) {
        return ResponseEntity.ok(userService.checkEmailDuplicate(email));
    }

    @GetMapping("/duplicate/nickname")
    @Operation(
            summary = "닉네임 중복검사",
            description = "사용자의 닉네임을 중복인지 검사합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "중복검사 성공"),
                    @ApiResponse(responseCode = "500", description = "관리자 문의 or json에 값이 없는 경우")
            }
    )
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestBody String nickname) {
        return ResponseEntity.ok(userService.checkNicknameDuplicate(nickname));
    }
}
