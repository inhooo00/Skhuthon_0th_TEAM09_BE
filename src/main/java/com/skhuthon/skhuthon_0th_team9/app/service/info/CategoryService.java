package com.skhuthon.skhuthon_0th_team9.app.service.info;

import com.skhuthon.skhuthon_0th_team9.app.domain.category.DepartmentCategory;
import com.skhuthon.skhuthon_0th_team9.app.dto.category.DepartmentCategoryDto;
import com.skhuthon.skhuthon_0th_team9.app.dto.category.DetailCategoryDto;
import com.skhuthon.skhuthon_0th_team9.app.dto.category.OnlyDepartmentCategoryDto;
import com.skhuthon.skhuthon_0th_team9.app.repository.DepartmentCategoryRepository;
import com.skhuthon.skhuthon_0th_team9.app.repository.DetailCategoryRepository;
import com.skhuthon.skhuthon_0th_team9.global.common.dto.ApiResponseTemplate;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.ErrorCode;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.model.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final DepartmentCategoryRepository departmentCategoryRepository;
    private final DetailCategoryRepository detailCategoryRepository;

    @Transactional
    public ApiResponseTemplate<DepartmentCategoryDto> getReviewsUsingDepartmentCategory(Long departmentCategoryId){
        DepartmentCategory departmentCategory = departmentCategoryRepository.findById(departmentCategoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "카테고리 : " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        DepartmentCategoryDto departmentCategoryDto = DepartmentCategoryDto.builder()
                .id(departmentCategory.getId())
                .departmentCategory(departmentCategory.getDepartmentCategory())
                .detailCategoryDtos(departmentCategory.getDetailCategories().stream()
                        .map(detailCategory -> DetailCategoryDto.builder()
                                .id(detailCategory.getId())
                                .detailCategory(detailCategory.getDetailCategory())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        return ApiResponseTemplate.<DepartmentCategoryDto>builder()
                .status(200)
                .success(true)
                .message("카테고리 조회 성공")
                .data(departmentCategoryDto)
                .build();
    }
    @Transactional
    public ApiResponseTemplate<List<OnlyDepartmentCategoryDto>> getAllDepartmentCategory1(){
        List<DepartmentCategory> departmentCategories = departmentCategoryRepository.findAll();

        List<OnlyDepartmentCategoryDto> onlyDepartmentCategoryDtos = departmentCategories.stream()
                .map(departmentCategory -> OnlyDepartmentCategoryDto.builder()
                        .id(departmentCategory.getId())
                        .departmentCategory(departmentCategory.getDepartmentCategory())
                        .build())
                .collect(Collectors.toList());

        return ApiResponseTemplate.<List<OnlyDepartmentCategoryDto>>builder()
                .status(200)
                .success(true)
                .message("카테고리 조회 성공")
                .data(onlyDepartmentCategoryDtos)
                .build();
    }
}
