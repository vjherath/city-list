package com.example.citylist.auth.service.impl;

import com.example.citylist.auth.model.AppUser;
import com.example.citylist.auth.repo.AppUserRepo;
import com.example.citylist.constant.AuthorizationRoles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SystemStateValidatorService implements ApplicationRunner {

    private final AppUserRepo appUserRepo;

    public SystemStateValidatorService(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        validateAtLeastOneActiveAdminAccount();
    }

    public void validateAtLeastOneActiveAdminAccount() {
        log.info( "validating whether at least one active user account is present" );
        int numberOfUsers = appUserRepo.getEnableUserCount();


        if( numberOfUsers > 0) {
            log.info( "Active user account found" );
            return;
        }

        log.info( "No active account found -> initializing the default user account..." );
        createInitialUserAccount();
    }

    public void createInitialUserAccount() {

        try {
            AppUser appUser = new AppUser();
            appUser.setFullName( "Admin Account" );
            appUser.setUsername( "admin" );
            appUser.setPassword(  new BCryptPasswordEncoder().encode( "admin123" ));
            appUser.setEnable( true );
            appUser.setUserRoles(AuthorizationRoles.ROLE_ADMIN + "," + AuthorizationRoles.ROLE_USER);

            AppUser user = new AppUser();
            user.setFullName( "User Account" );
            user.setUsername( "user" );
            user.setPassword(  new BCryptPasswordEncoder().encode( "user123" ));
            user.setEnable( true );
            user.setUserRoles(AuthorizationRoles.ROLE_USER);

            appUserRepo.saveAll( List.of(appUser,user) );

            log.info( "Admin : Username: " + appUser.getUsername() );
            log.info( "Pw: " + "admin123" );

            log.info( "User : Username: " + user.getUsername() );
            log.info( "Pw: " + "user123" );
        } catch( Exception e )  {
            log.error( e.getMessage(), e );
        }

    }
}
