package com.skhuthon.skhuthon_0th_team9.app.controller.review;

import com.skhuthon.skhuthon_0th_team9.app.dto.review.requestDto.ReviewCreateRequestDto;
import com.skhuthon.skhuthon_0th_team9.app.dto.review.responseDto.ReviewCreateResponseDto;
import com.skhuthon.skhuthon_0th_team9.app.service.review.ReviewCreateService;
import com.skhuthon.skhuthon_0th_team9.global.common.dto.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "api/v1/review")
@Tag(name = "리뷰", description = "리뷰를 담당하는 api그룹")
public class ReviewController {

    private final ReviewCreateService reviewCreateService;

    @PostMapping("/create")
    @Operation(
            summary = "리뷰 생성",
            description = "리뷰를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "리뷰 생성 성공"),
                    @ApiResponse(responseCode = "403", description = "url문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate<ReviewCreateResponseDto>> createReview(
            @RequestPart ReviewCreateRequestDto requestDto,
            @RequestPart List<MultipartFile> images,
            Principal principal) {

        ApiResponseTemplate<ReviewCreateResponseDto> data = reviewCreateService.createReview(requestDto, images, principal);

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
