package com.example.citylist.auth.mapper;


import com.example.citylist.auth.dto.requestDTO.CityUpdateRequest;
import com.example.citylist.dto.CityDTO;
import com.example.citylist.model.City;

public final class CityDTOMapper {

    public static CityDTO mapCityDTO(City city) {
        if(city == null){
            return null;
        }

        CityDTO cityDTO = new CityDTO();
        if(city.getId() != null){
            cityDTO.setId(city.getId());
        }
        if(city.getName() != null){
            cityDTO.setName(city.getName());
        }
        if(city.getPhotoUrl() != null){
            cityDTO.setPhotoUrl(city.getPhotoUrl());
        }

        return cityDTO;
    }

    public static City mapCityUpdateRequest(CityUpdateRequest request, City city) {
        if(request == null){
            return city;
        }

        if(request.getName() != null){
            city.setName(request.getName());
        }

        return city;
    }
}
