package com.project.id.project.application.services.s3storage;

import org.springframework.web.multipart.MultipartFile;

public interface S3StorageCrud {
    public String upload(MultipartFile multipartFile);
    public void update(String linkToObject, byte[] data);
    public void delete(String linkToObject);
    public byte[] get(String linkToObject);
}
