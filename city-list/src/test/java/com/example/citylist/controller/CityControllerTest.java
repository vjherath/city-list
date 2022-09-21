package com.example.citylist.controller;

import com.example.citylist.auth.dto.requestDTO.CityListRequest;
import com.example.citylist.auth.repo.AppUserRepo;
import com.example.citylist.auth.service.UserService;
import com.example.citylist.auth.service.impl.CustomUserDetailsService;
import com.example.citylist.constant.ApiPathConstants;
import com.example.citylist.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.example.citylist.constant.AuthorizationRoles.ROLE_ADMIN;
import static com.example.citylist.constant.AuthorizationRoles.ROLE_USER;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CityControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CityService cityService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private AppUserRepo appUserRepo;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = {ROLE_ADMIN,ROLE_USER})
    public void getCitiesWithoutSearchText() throws Exception {

        CityListRequest cityListRequest = CityListRequest.builder()
                .size(20).page(0).build();

        String url = ApiPathConstants.PATH_SEPARATOR + ApiPathConstants.API_CITY + "/list";
        MvcResult result = this.mockMvc.perform( post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cityListRequest)))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
    }


    @Test
    @WithMockUser(roles = {ROLE_ADMIN,ROLE_USER})
    public void getCitiesWithSearchText() throws Exception {

        CityListRequest cityListRequest = CityListRequest.builder()
                .size(20).page(0).searchText("test").build();

        String url = ApiPathConstants.PATH_SEPARATOR + ApiPathConstants.API_CITY + "/list";
        MvcResult result = mockMvc.perform( post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cityListRequest)))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = {ROLE_ADMIN})
    public void updateCity() throws Exception {

        MockMultipartFile cityName = new MockMultipartFile("name", "{\"name\": \"cityName\"}".getBytes());
        MockMultipartFile cityImage = new MockMultipartFile("image",  new byte[1]);


        String url = ApiPathConstants.PATH_SEPARATOR + ApiPathConstants.API_CITY + "/1" ;
        MvcResult result = mockMvc.perform( multipart(HttpMethod.PUT, url)
                        .file(cityName)
                        .file(cityImage))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
    }
}
