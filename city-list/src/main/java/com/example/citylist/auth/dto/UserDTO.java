package com.example.citylist.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

    private Long id;
    protected String fullName;
    protected String username;
    protected List<String> roles;
    protected boolean enable;
}
