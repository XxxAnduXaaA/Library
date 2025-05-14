package com.example.Library.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
public class FileServeController {

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            // Декодируем имя файла из URL
            String decodedFilename = URLDecoder.decode(filename, StandardCharsets.UTF_8.toString());
            
            // Используем относительный путь
            Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
            Path file = uploadPath.resolve(decodedFilename).normalize();
            
            // Проверяем, что файл находится внутри uploads директории
            if (!file.startsWith(uploadPath)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file path");
            }

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new FileNotFoundException("File not found: " + decodedFilename);
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error serving file:  " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error serving file: " + e.getMessage());
        }
    }
}
