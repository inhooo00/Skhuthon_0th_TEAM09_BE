package com.skhuthon.skhuthon_0th_team9.app.service.review;

import com.skhuthon.skhuthon_0th_team9.app.domain.review.Review;
import com.skhuthon.skhuthon_0th_team9.app.domain.user.User;
import com.skhuthon.skhuthon_0th_team9.app.dto.review.requestDto.ReviewCreateRequestDto;
import com.skhuthon.skhuthon_0th_team9.app.dto.review.responseDto.ReviewCreateResponseDto;
import com.skhuthon.skhuthon_0th_team9.app.repository.ReviewRepository;
import com.skhuthon.skhuthon_0th_team9.app.repository.UserRepository;
import com.skhuthon.skhuthon_0th_team9.global.common.dto.ApiResponseTemplate;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.ErrorCode;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.model.CustomException;
import com.skhuthon.skhuthon_0th_team9.global.util.GetS3Resource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCreateService {

    private final ReviewRepository reviewRepository;
    private final S3ImageFileService s3ImageFileService;
    private final UserRepository userRepository;

    @Transactional
    public ApiResponseTemplate<ReviewCreateResponseDto> createReview(ReviewCreateRequestDto requestDto,
                                                                     List<MultipartFile> images, Principal principal) {

        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                        ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));

        List<GetS3Resource> imagesUrl = uploadImages(images);


        Review review = createReviewEntity(requestDto, user, imagesUrl.stream()
                .map(GetS3Resource::getImageUrl)
                .collect(Collectors.toList()));

        reviewRepository.save(review);

        ReviewCreateResponseDto responseDto = createReviewCreateResponseDto(review);

        return ApiResponseTemplate.<ReviewCreateResponseDto>builder()
                .status(201)
                .success(true)
                .message("review 등록 성공")
                .data(responseDto)
                .build();
    }

    private Review createReviewEntity(ReviewCreateRequestDto requestDto, User user,
                                      List<String> imageUrls) {
        return Review.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .createAt(requestDto.getCreateAt())
                .modifiedAt(requestDto.getModifiedAt())
                .imageUrls(imageUrls)
                .departmentCategory(requestDto.getDepartmentCategory())
                .detailCategory(requestDto.getDetailCategory())
                .user(user)
                .build();
    }

    private List<GetS3Resource> uploadImages(List<MultipartFile> imageFiles) {
        return s3ImageFileService.uploadImageFiles(imageFiles, "Review");
    }

    private ReviewCreateResponseDto createReviewCreateResponseDto(Review review) {
        return ReviewCreateResponseDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .description(review.getDescription())
                .createAt(review.getCreateAt())
                .modifiedAt(review.getCreateAt())
                .imageUrls(review.getImageUrls())
                .departmentCategory(review.getDepartmentCategory())
                .detailCategory(review.getDetailCategory())
                .build();
    }
    public List<ReviewCreateResponseDto> getAllReviews(){
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

        return reviewCreateResponseDtos;
    }
    public List<ReviewCreateResponseDto> getReviewsByDepartmentCategory(Long departmentCategoryId){
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

        return reviewCreateResponseDtos;
    }
}
