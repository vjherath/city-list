package com.example.citylist.fileService.controller;

import com.example.citylist.constant.ApiPathConstants;
import com.example.citylist.fileService.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping( ApiPathConstants.FILE_DOWNLOAD )
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping( "/{fileName}" )
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
    {
        return fileService.downloadFiles( fileName, request );
    }
}
