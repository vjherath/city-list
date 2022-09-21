package com.example.citylist.auth.repo;

import com.example.citylist.auth.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    @Query("SELECT COUNT(us) FROM AppUser us WHERE us.enable = true")
    int getEnableUserCount();

    @Query("SELECT us FROM AppUser us WHERE us.username = :username AND us.enable = true")
    Optional<AppUser> findByUsernameAndEnabledTrue(String username);
}
