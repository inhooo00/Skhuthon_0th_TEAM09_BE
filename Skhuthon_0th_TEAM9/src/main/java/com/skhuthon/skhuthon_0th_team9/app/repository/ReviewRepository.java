package com.skhuthon.skhuthon_0th_team9.app.repository;

import com.skhuthon.skhuthon_0th_team9.app.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByDepartmentCategoryId(Long departmentCategoryId);
}
