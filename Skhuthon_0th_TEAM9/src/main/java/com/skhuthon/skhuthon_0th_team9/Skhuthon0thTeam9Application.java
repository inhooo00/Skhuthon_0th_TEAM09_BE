package com.skhuthon.skhuthon_0th_team9;

import com.skhuthon.skhuthon_0th_team9.app.domain.category.DepartmentCategory;
import com.skhuthon.skhuthon_0th_team9.app.domain.category.DetailCategory;
import com.skhuthon.skhuthon_0th_team9.app.repository.DepartmentCategoryRepository;
import com.skhuthon.skhuthon_0th_team9.app.repository.DetailCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;
import java.util.TimeZone;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class Skhuthon0thTeam9Application {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        SpringApplication.run(Skhuthon0thTeam9Application.class, args);
    }

    // 프로젝트 데모 데이터
    @Bean
    public CommandLineRunner initDatabase(DepartmentCategoryRepository departmentCategoryRepository, DetailCategoryRepository detailCategoryRepository) {
        return args -> {
            // Department Categories
            DepartmentCategory socialConvergenceAutonomous = new DepartmentCategory("사회융합자율학부");
            DepartmentCategory humanitiesConvergenceAutonomous = new DepartmentCategory("인문융합자율학부");
            DepartmentCategory digitalContentConvergenceAutonomous = new DepartmentCategory("디지털콘텐츠융합자율학부");
            DepartmentCategory softwareConvergenceAutonomous = new DepartmentCategory("소프트웨어융합자율학부");

            departmentCategoryRepository.saveAll(Arrays.asList(
                    socialConvergenceAutonomous,
                    humanitiesConvergenceAutonomous,
                    digitalContentConvergenceAutonomous,
                    softwareConvergenceAutonomous
            ));

            // Detail Categories
            DetailCategory electronicDevice = new DetailCategory("전자기기", socialConvergenceAutonomous);
            DetailCategory books = new DetailCategory("도서", socialConvergenceAutonomous);
            DetailCategory extracurricularActivities = new DetailCategory("대외활동", socialConvergenceAutonomous);
            DetailCategory etc1 = new DetailCategory("etc", socialConvergenceAutonomous);

            DetailCategory electronicDevice2 = new DetailCategory("전자기기", humanitiesConvergenceAutonomous);
            DetailCategory languageLearningMaterials = new DetailCategory("어학 학습 자료", humanitiesConvergenceAutonomous);
            DetailCategory culturalContents = new DetailCategory("문화 콘텐츠", humanitiesConvergenceAutonomous);
            DetailCategory etc2 = new DetailCategory("etc", humanitiesConvergenceAutonomous);

            DetailCategory electronicDevice3 = new DetailCategory("전자기기", digitalContentConvergenceAutonomous);
            DetailCategory website = new DetailCategory("사이트", digitalContentConvergenceAutonomous);
            DetailCategory extracurricularActivities2 = new DetailCategory("대외활동", digitalContentConvergenceAutonomous);
            DetailCategory culturalContents2 = new DetailCategory("문화 콘텐츠", digitalContentConvergenceAutonomous);
            DetailCategory etc3 = new DetailCategory("etc", digitalContentConvergenceAutonomous);

            DetailCategory laptop = new DetailCategory("노트북", softwareConvergenceAutonomous);
            DetailCategory onlineCourses = new DetailCategory("온라인 강의", softwareConvergenceAutonomous);
            DetailCategory competitions = new DetailCategory("대회활동", softwareConvergenceAutonomous);
            DetailCategory etc4 = new DetailCategory("etc", softwareConvergenceAutonomous);

            detailCategoryRepository.saveAll(Arrays.asList(
                    electronicDevice, books, extracurricularActivities, etc1,
                    electronicDevice2, languageLearningMaterials, culturalContents, etc2,
                    electronicDevice3, website, extracurricularActivities2, culturalContents2, etc3,
                    laptop, onlineCourses, competitions, etc4
            ));
        };
    }

}
