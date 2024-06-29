package com.skhuthon.skhuthon_0th_team9.app.domain.category.base;

import com.skhuthon.skhuthon_0th_team9.app.domain.category.DetailCategory;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.List;

@MappedSuperclass
@Getter
public abstract class DepartmentCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    protected String departmentCategory;

    @OneToMany(mappedBy = "departmentCategory", fetch = FetchType.LAZY)
    protected List<DetailCategory> detailCategories;

}

