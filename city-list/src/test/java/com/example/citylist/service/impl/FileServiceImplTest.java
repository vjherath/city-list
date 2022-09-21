package com.example.citylist.service.impl;

import com.example.citylist.auth.dto.requestDTO.CityListRequest;
import com.example.citylist.dto.CityDTO;
import com.example.citylist.dto.PaginatedResponse;
import com.example.citylist.fileService.service.impl.FileServiceImpl;
import com.example.citylist.model.City;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
public class FileServiceImplTest {

    private FileServiceImpl fileService;

    @Value("${image-upload.dir}")
    private String uploadDirectory;
    private String imageName;


    @Before
    public void setup(){
        this.fileService = new FileServiceImpl();
        this.imageName = "test_image.png";
    }

    @Test
    public void saveFile() throws IOException {

        // setup required mocks
        MockMultipartFile cityImage = new MockMultipartFile(imageName,  imageName,null, new byte[1]);

        // test
        String location = fileService.saveFile(cityImage);

        // asserts
        assertThat(location).isNotNull();

    }

    @Test
    public void downloadFiles() {

        // setup required mocks
        MockHttpServletRequest request = new MockHttpServletRequest();

        // test
        ResponseEntity<Resource> response = fileService.downloadFiles(imageName, request);

        // asserts
        assertEquals(200, response.getStatusCodeValue());
        assertThat(response).isNotNull();

        Resource resource = response.getBody();
        assertThat(resource).isNotNull();

        HttpHeaders headers = response.getHeaders();
        assertThat(headers).isNotNull();
        List<String> headerList = headers.get(HttpHeaders.CONTENT_DISPOSITION);
        assertEquals(headerList.get(0),"attachment; filename=\"" + imageName + "\"");

    }
}
