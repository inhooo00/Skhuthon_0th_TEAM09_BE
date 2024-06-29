package com.skhuthon.skhuthon_0th_team9.app.domain.category;

import com.skhuthon.skhuthon_0th_team9.app.domain.category.base.DetailCategoryEntity;
import com.skhuthon.skhuthon_0th_team9.app.domain.review.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailCategory extends DetailCategoryEntity {

    @OneToOne(mappedBy = "detailCategory", fetch = FetchType.LAZY)
    private Review review;

    public DetailCategory(String detailCategory, DepartmentCategory departmentCategory) {
        this.detailCategory = detailCategory;
        this.departmentCategory = departmentCategory;
    }
}
