package com.example.citylist.fileService.service.impl;

import com.example.citylist.fileService.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${image-upload.dir}")
    private String uploadDirectory;

    @Override
    public String saveFile(MultipartFile file) throws IOException {

        Path location = Paths.get( uploadDirectory)
                .toAbsolutePath().normalize();
        String orgName = file.getOriginalFilename();

        String locationString ="";
        if(orgName != null){
            Path targetLocation = location.resolve( orgName );
            Files.copy( file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING );

            locationString = targetLocation.toString();

        }

        return locationString;
    }

    @Override
    public ResponseEntity<Resource> downloadFiles( String fileName, HttpServletRequest request )
    {
        try   {

            Resource resource = loadFileAsResource( fileName );
            String contentType = null;
            try   {
                contentType = request.getServletContext().getMimeType( resource.getFile().getAbsolutePath() );
            }
            catch( IOException ex ) {
                throw new IOException( "Could not determine file type." );
            }

            if( contentType == null )  {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType( MediaType.parseMediaType( contentType ) )
                    .header( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"" )
                    .body( resource );

        }
        catch( Exception e )    {
            try {
                throw new IOException ( "File not found" );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private Resource loadFileAsResource( String fileName ) throws IOException {

        try {

            String filePath = uploadDirectory +  fileName ;
            Resource resource = new UrlResource( Paths.get(filePath).toUri() );
            if( resource.exists() )  {
                return resource;
            }
            else  {
                throw new IOException( "File not found " + fileName );
            }
        }
        catch( IOException ex )   {
            throw new IOException( "File not found " + fileName );
        }
    }
}
