package com.example.citylist.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
public class CityDTO implements Serializable {

    private Long id;
    private String name;
    private String photoUrl;
}
