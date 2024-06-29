package com.skhuthon.skhuthon_0th_team9.app.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailCategoryDto {
    private Long id;
    private String detailCategory;
}
