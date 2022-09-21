package com.example.citylist.service.impl;

import com.example.citylist.dto.PaginatedResponse;
import com.example.citylist.auth.dto.requestDTO.CityListRequest;
import com.example.citylist.auth.dto.requestDTO.CityUpdateRequest;
import com.example.citylist.auth.mapper.CityDTOMapper;
import com.example.citylist.dto.CityDTO;
import com.example.citylist.model.City;
import com.example.citylist.repo.CityRepo;
import com.example.citylist.service.CityService;
import com.example.citylist.fileService.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CityServiceImpl implements CityService {

    private final CityRepo cityRepo;
    private final FileService fileService;

    @Value("${app.base-url}")
    private String imageFileDownloadUrl;

    public CityServiceImpl(CityRepo cityRepo, FileService fileService) {
        this.cityRepo = cityRepo;
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity<?> getAllCities(CityListRequest cityListRequest, Pageable pageable) {

        String searchText = "";
        if (cityListRequest != null) {
            if(StringUtils.hasLength(cityListRequest.getSearchText())){
                searchText = cityListRequest.getSearchText();
            }
            pageable = PageRequest.of(cityListRequest.getPage(), cityListRequest.getSize(), Sort.by("name"));
        }

        ResponseEntity<PaginatedResponse<List<CityDTO>>> response;

        try{
            PaginatedResponse<List<CityDTO>> paginatedResponse = new PaginatedResponse<>();

            Page<City> cityPage = cityRepo.findByNameLikeIgnoreCase("%"+searchText+"%", pageable);

            if(cityPage != null && !CollectionUtils.isEmpty(cityPage.getContent())){
                paginatedResponse.setContent(
                        cityPage.getContent().stream().map(CityDTOMapper::mapCityDTO).collect(Collectors.toList()));
                paginatedResponse.setTotalElementCount(cityPage.getTotalElements());
                paginatedResponse.setTotalPageCount(cityPage.getTotalPages());
            }

            response = new ResponseEntity<>( paginatedResponse, HttpStatus.OK );

        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateCity(long id, CityUpdateRequest cityUpdateRequest) {

        ResponseEntity<CityDTO> response;

        try{

            City city = cityRepo.findById(id).orElseThrow();
            city = CityDTOMapper.mapCityUpdateRequest(cityUpdateRequest, city);

            if(cityUpdateRequest.getImage() != null){
                fileService.saveFile(cityUpdateRequest.getImage());
                city.setPhotoUrl(imageFileDownloadUrl + cityUpdateRequest.getImage().getOriginalFilename());
            }
            cityRepo.save(city);

            response = new ResponseEntity<>(CityDTOMapper.mapCityDTO(city), HttpStatus.OK);

        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            response = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        return response;
    }

    @Override
    public ResponseEntity<?> uploadCity(MultipartFile file) {

        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);

            List<City> cities = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                String name = csvRecord.get(1);
                String photo = csvRecord.get(2);
                if(name != null && photo != null){
                    City city = new City();
                    city.setName(name);
                    city.setPhotoUrl(photo);

                    cities.add(city);

                    try{
                        cityRepo.save(city);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }

            if(!CollectionUtils.isEmpty(cities)){
               // cityRepo.saveAll(cities);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
