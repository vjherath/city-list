package com.example.citylist.controller;

import com.example.citylist.auth.dto.requestDTO.CityListRequest;
import com.example.citylist.auth.dto.requestDTO.CityUpdateRequest;
import com.example.citylist.constant.ApiPathConstants;
import com.example.citylist.service.CityService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiPathConstants.API_CITY)
@CrossOrigin(origins = "*")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @PostMapping("/list")
    public ResponseEntity<?> getAllCities(
            @RequestBody CityListRequest cityListRequest,
            @PageableDefault( size = 50, sort = "name", direction = Sort.Direction.ASC) Pageable pageable)  {

        return cityService.getAllCities( cityListRequest, pageable);
    }

    @PutMapping(value="/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE} )
    public ResponseEntity<?> updateCity(@PathVariable(value = "id") long id,
                                        @RequestPart(value = "name", required = false) String name,
                                        @RequestPart(value = "image", required = false) MultipartFile image)  {

        CityUpdateRequest cityUpdateRequest = new CityUpdateRequest();
        cityUpdateRequest.setName(name);
        cityUpdateRequest.setImage(image);

        return cityService.updateCity(id, cityUpdateRequest);
    }

    @PostMapping(value="/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    public ResponseEntity<?> uploadCity(@RequestBody MultipartFile file)  {

        return cityService.uploadCity(file);
    }

}
