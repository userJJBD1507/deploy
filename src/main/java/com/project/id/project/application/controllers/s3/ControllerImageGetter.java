package com.project.id.project.application.controllers.s3;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.id.project.application.services.s3storage.StorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/getImage")
@Tag(name = "Image Receiver Controller (from s3 storage) ")
public class ControllerImageGetter {
    @Autowired
    private StorageService storageService;
    @Operation(
	summary = "Получение фото от s3 хранилища",
	description = "Позволяет получить фото от s3 хранилища"
    )
    @GetMapping("/get")
        public ResponseEntity<byte[]> getImage(@RequestParam("filename") String filename) {
            try {
                byte[] imageBytes = storageService.get(filename);

                if (imageBytes == null || imageBytes.length == 0) {
                    return ResponseEntity.notFound().build();
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                headers.setContentLength(imageBytes.length);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(imageBytes);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
}
