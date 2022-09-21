package com.example.citylist.auth.controller;

import com.example.citylist.auth.dto.UserDTO;
import com.example.citylist.auth.service.UserService;
import com.example.citylist.constant.ApiPathConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( ApiPathConstants.API_V1_USER )
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser() {

        return new ResponseEntity<>( userService.getCurrentUserDTO() , HttpStatus.OK );
    }
}
