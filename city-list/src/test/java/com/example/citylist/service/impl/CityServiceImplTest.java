package com.example.citylist.service.impl;

import com.example.citylist.auth.dto.requestDTO.CityUpdateRequest;
import com.example.citylist.dto.PaginatedResponse;
import com.example.citylist.auth.dto.requestDTO.CityListRequest;
import com.example.citylist.dto.CityDTO;
import com.example.citylist.fileService.service.FileService;
import com.example.citylist.model.City;
import com.example.citylist.repo.CityRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class CityServiceImplTest {

    @Mock
    private CityRepo cityRepo;
    @Mock
    private FileService fileService;
    private CityServiceImpl cityService;


    @Before
    public void setup(){
        this.cityService = new CityServiceImpl(this.cityRepo, this.fileService);
    }

    @Test
    public void getCityList(){

        // setup required mocks
        int size = 20;
        String sortBy = "name";
        Pageable pageable = PageRequest.of(0, size, Sort.by(sortBy));
        Page<City> pagedResponse = new PageImpl<>(mockCityList(size), pageable, 0);
        CityListRequest cityListRequest = CityListRequest.builder().size(20).page(0).build();

        // pre-conditions
        given(cityRepo.findByNameLikeIgnoreCase(any(String.class), any(Pageable.class))).willReturn(pagedResponse);

        // test
        ResponseEntity<?> response = cityService.getAllCities(cityListRequest, pageable);

        // asserts
        assertEquals(200, response.getStatusCodeValue());
        assertThat(response).isNotNull();

        PaginatedResponse<List<CityDTO>> paginatedResponse = (PaginatedResponse<List<CityDTO>>) response.getBody();
        assertThat(paginatedResponse).isNotNull();
        assertFalse(paginatedResponse.getContent().isEmpty());

        assertEquals(paginatedResponse.getContent().size(), size);

    }

    private List<City> mockCityList (int size){

        List<City> cities = new ArrayList<>();
        for(int i=0; i < size; i++){
            City city = new City();
            city.setName(String.valueOf(i));

            cities.add(city);
        }

        return cities;
    }

    @Test
    public void updateCity () throws IOException {

        // setup required mocks
        City db_city = new City();
        db_city.setId(1L);
        Optional<City> cityOptional = Optional.of(db_city);
        MockMultipartFile cityImage = new MockMultipartFile("image",  new byte[1]);
        CityUpdateRequest cityUpdateRequest = new CityUpdateRequest();
        cityUpdateRequest.setName("updated city name");
        cityUpdateRequest.setImage(cityImage);

        // pre-conditions
        given(cityRepo.findById(1L)).willReturn(cityOptional);
        given(fileService.saveFile(cityImage)).willReturn("Image Location");

        // test
        ResponseEntity<?> response = cityService.updateCity(1L, cityUpdateRequest);

        // asserts
        assertEquals(200, response.getStatusCodeValue());
        assertThat(response).isNotNull();

        CityDTO cityDTO = (CityDTO) response.getBody();
        assertThat(cityDTO).isNotNull();
        assertEquals(cityDTO.getId(), db_city.getId());
        assertEquals(cityDTO.getName(), cityUpdateRequest.getName());
        assertThat(cityDTO.getPhotoUrl()).isNotNull();

    }
}
