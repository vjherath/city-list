package com.example.citylist.fileService.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileService {

    String saveFile( MultipartFile file ) throws IOException;

    ResponseEntity<Resource> downloadFiles (String fileName, HttpServletRequest request);
}
