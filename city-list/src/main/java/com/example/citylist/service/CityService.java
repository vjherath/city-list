package com.example.citylist.service;

import com.example.citylist.auth.dto.requestDTO.CityListRequest;
import com.example.citylist.auth.dto.requestDTO.CityUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import static com.example.citylist.constant.AuthorizationRoles.ROLE_ADMIN;
import static com.example.citylist.constant.AuthorizationRoles.ROLE_USER;

public interface CityService {

    @Secured({ROLE_ADMIN,ROLE_USER})
    ResponseEntity<?> getAllCities(CityListRequest cityListRequest, Pageable pageable);

    @Secured(ROLE_ADMIN)
    ResponseEntity<?> updateCity(long id, CityUpdateRequest cityUpdateRequest);

    ResponseEntity<?> uploadCity(MultipartFile file);
}
