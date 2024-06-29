package com.skhuthon.skhuthon_0th_team9.app.domain.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skhuthon.skhuthon_0th_team9.app.domain.category.DepartmentCategory;
import com.skhuthon.skhuthon_0th_team9.app.domain.category.DetailCategory;
import com.skhuthon.skhuthon_0th_team9.app.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @Column(name = "REVIEW_TITLE")
    private String title;

    @Column(name = "REVIEW_DESCRIPTION")
    private String description;

    @Column(name = "REVIEW_IMAGE_URLS")
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> imageUrls;

    @JsonFormat(pattern = "yyyy-MM-dd - HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createAt;

    @JsonFormat(pattern = "yyyy-MM-dd - HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_category_id")
    private DepartmentCategory departmentCategory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_category_id")
    private DetailCategory detailCategory;

}
