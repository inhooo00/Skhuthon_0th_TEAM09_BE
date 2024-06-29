package com.skhuthon.skhuthon_0th_team9.app.dto.review.responseDto;

import com.skhuthon.skhuthon_0th_team9.app.domain.category.DepartmentCategory;
import com.skhuthon.skhuthon_0th_team9.app.domain.category.DetailCategory;
import com.skhuthon.skhuthon_0th_team9.app.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReviewCreateResponseDto {

    private Long id;
    private String title;
    private String description;
    private List<String> imageUrls;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long userId;
    private DepartmentCategory departmentCategory;
    private DetailCategory detailCategory;

}
