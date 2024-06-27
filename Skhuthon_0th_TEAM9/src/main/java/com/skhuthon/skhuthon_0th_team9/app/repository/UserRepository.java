package com.skhuthon.skhuthon_0th_team9.app.repository;

import com.skhuthon.skhuthon_0th_team9.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickName(String nickname);
    Optional<User> findByEmail(String email);
}
