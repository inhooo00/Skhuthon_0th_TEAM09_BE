package com.skhuthon.skhuthon_0th_team9.app.dto.review.requestDto;

import com.skhuthon.skhuthon_0th_team9.app.domain.category.DepartmentCategory;
import com.skhuthon.skhuthon_0th_team9.app.domain.category.DetailCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewCreateRequestDto {
    private String title;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private DepartmentCategory departmentCategory;
    private DetailCategory detailCategory;
}
