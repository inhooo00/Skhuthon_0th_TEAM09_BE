package com.skhuthon.skhuthon_0th_team9.app.dto.category;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DepartmentCategoryDto {
    private Long id;
    private String departmentCategory;
    private List<DetailCategoryDto> detailCategoryDtos;
}
