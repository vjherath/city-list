package com.example.citylist.auth.dto.requestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityListRequest implements Serializable {

    protected int page;
    protected int size;
    protected String searchText;
}
