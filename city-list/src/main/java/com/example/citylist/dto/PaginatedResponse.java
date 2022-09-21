package com.example.citylist.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedResponse<T> implements Serializable {

    protected int totalPageCount;
    protected long totalElementCount;
    protected T content;
}
