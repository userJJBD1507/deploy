package com.project.id.project.application.services;

public class ApiResponse<T> {
    private T data;
    private String photoBase64;
    public ApiResponse(T data) {
        this.data = data;
    }
    public ApiResponse(T data, String photoBase64) {
        this.data = data;
        this.photoBase64 = photoBase64;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }
}
