package com.skhuthon.skhuthon_0th_team9.app.controller.category;

import com.skhuthon.skhuthon_0th_team9.app.dto.category.DepartmentCategoryDto;
import com.skhuthon.skhuthon_0th_team9.app.dto.category.OnlyDepartmentCategoryDto;
import com.skhuthon.skhuthon_0th_team9.app.service.info.CategoryService;
import com.skhuthon.skhuthon_0th_team9.global.common.dto.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@Tag(name = "카테고리", description = "카테고리 분류를 담당하는 api")
public class CategoryController {

    private final CategoryService categoryService;

    // 스웨거도 같이 해주자.
    @GetMapping("/get/{category_ID}")
    public ResponseEntity<ApiResponseTemplate<DepartmentCategoryDto>> getCategoryList(@PathVariable Long category_ID){
        ApiResponseTemplate<DepartmentCategoryDto> data = categoryService.getReviewsUsingDepartmentCategory(category_ID);

        return ResponseEntity.status(data.getStatus()).body(data);
    }
    @GetMapping("/get/departments_category")
    public ResponseEntity<ApiResponseTemplate<List<OnlyDepartmentCategoryDto>>> getAllDepartmentCategory(){
        ApiResponseTemplate<List<OnlyDepartmentCategoryDto>> data = categoryService.getAllDepartmentCategory1();

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
