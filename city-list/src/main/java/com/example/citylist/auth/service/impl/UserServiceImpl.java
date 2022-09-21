package com.example.citylist.auth.service.impl;

import com.example.citylist.auth.config.UserDetail;
import com.example.citylist.auth.dto.UserDTO;
import com.example.citylist.auth.mapper.UserMapper;
import com.example.citylist.auth.model.AppUser;
import com.example.citylist.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public UserDTO getCurrentUserDTO()
    {
        return UserMapper.mapUser( getCurrentUser() );
    }

    private UserDetail getCurrentUser() {
        try
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if( authentication != null && authentication.getPrincipal() != null &&
                    authentication.getPrincipal() instanceof UserDetail userDetail )
            {
                return userDetail;
            }
        }
        catch( Exception e )
        {
            log.error( "exception in obtaining current user: ", e );
        }
        return null;

    }
}
