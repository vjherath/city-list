package com.example.citylist.auth.service.impl;

import com.example.citylist.auth.config.UserDetail;
import com.example.citylist.auth.model.AppUser;
import com.example.citylist.auth.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements  UserDetailsService {

    @Autowired
    private AppUserRepo appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = appUserRepo.findByUsernameAndEnabledTrue( username ).orElseThrow(() -> {
            throw new UsernameNotFoundException( "User not found with username: " + username );});

        return new UserDetail(appUser);

    }


}
