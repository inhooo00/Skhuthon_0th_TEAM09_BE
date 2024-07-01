package com.skhuthon.skhuthon_0th_team9.app.service.review;

import com.skhuthon.skhuthon_0th_team9.app.domain.review.Review;
import com.skhuthon.skhuthon_0th_team9.app.dto.review.responseDto.ReviewCreateResponseDto;
import com.skhuthon.skhuthon_0th_team9.app.repository.ReviewRepository;
import com.skhuthon.skhuthon_0th_team9.global.common.dto.ApiResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewSearchService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<ReviewCreateResponseDto>> getAllReviews(Principal principal) {
        List<Review> reviews = reviewRepository.findAll();

        List<ReviewCreateResponseDto> reviewCreateResponseDtos = reviews.stream()
                .map(review -> ReviewCreateResponseDto.builder()
                        .id(review.getId())
                        .title(review.getTitle())
                        .description(review.getDescription())
                        .imageUrls(review.getImageUrls())
                        .createAt(review.getCreateAt())
                        .modifiedAt(review.getModifiedAt())
                        .userId(review.getUser().getId())
                        .departmentCategory(review.getDepartmentCategory())
                        .detailCategory(review.getDetailCategory())
                        .build())
                .collect(Collectors.toList());

        return ApiResponseTemplate.<List<ReviewCreateResponseDto>>builder()
                .status(200)
                .success(true)
                .message("리뷰 조회 성공")
                .data(reviewCreateResponseDtos)
                .build();
    }

    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<ReviewCreateResponseDto>> getReviewsByDepartmentCategory(Long departmentCategoryId){
        List<Review> reviews = reviewRepository.findByDepartmentCategoryId(departmentCategoryId);

        List<ReviewCreateResponseDto> reviewCreateResponseDtos = reviews.stream()
                .map(review -> ReviewCreateResponseDto.builder()
                        .id(review.getId())
                        .title(review.getTitle())
                        .description(review.getDescription())
                        .imageUrls(review.getImageUrls())
                        .createAt(review.getCreateAt())
                        .modifiedAt(review.getModifiedAt())
                        .userId(review.getUser().getId())
                        .departmentCategory(review.getDepartmentCategory())
                        .detailCategory(review.getDetailCategory())
                        .build())
                .collect(Collectors.toList());

        return ApiResponseTemplate.<List<ReviewCreateResponseDto>>builder()
                .status(200)
                .success(true)
                .message("리뷰 조회 성공")
                .data(reviewCreateResponseDtos)
                .build();
    }
}
