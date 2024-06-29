package com.skhuthon.skhuthon_0th_team9.app.domain.category.base;

import com.skhuthon.skhuthon_0th_team9.app.domain.category.DepartmentCategory;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DetailCategoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    protected String detailCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_category_id")
    protected DepartmentCategory departmentCategory;

}
