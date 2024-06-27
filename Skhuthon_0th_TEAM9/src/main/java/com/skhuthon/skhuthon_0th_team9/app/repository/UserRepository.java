package com.skhuthon.skhuthon_0th_team9.app.repository;

import com.skhuthon.skhuthon_0th_team9.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
