package com.skhuthon.skhuthon_0th_team9.app.repository;

import com.skhuthon.skhuthon_0th_team9.app.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
