package com.example.citylist.auth.mapper;

import com.example.citylist.auth.config.UserDetail;
import com.example.citylist.auth.dto.UserDTO;
import com.example.citylist.auth.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class UserMapper {

    public static UserDTO mapUser(UserDetail userDetail) {
        if(userDetail == null){
            return null;
        }

        UserDTO userDTO = new UserDTO();
        if(userDetail.getUsername() != null){
            userDTO.setUsername(userDetail.getUsername());
        }
        if(!CollectionUtils.isEmpty(userDetail.getAuthorities())){
            userDTO.setRoles(userDetail.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        }

        userDTO.setEnable(userDetail.isEnabled());

        return userDTO;
    }
}
