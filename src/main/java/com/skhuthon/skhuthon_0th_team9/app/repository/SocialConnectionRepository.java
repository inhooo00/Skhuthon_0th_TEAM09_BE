package com.skhuthon.skhuthon_0th_team9.app.repository;

import com.skhuthon.skhuthon_0th_team9.app.domain.user.User;
import com.skhuthon.skhuthon_0th_team9.app.domain.user.social.SocialConnection;
import com.skhuthon.skhuthon_0th_team9.app.domain.user.social.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialConnectionRepository extends JpaRepository<SocialConnection, Long> {
    Optional<SocialConnection> findByUserAndSocialType(User user, SocialType socialType);
}
