package com.project.id.project.application.services.s3storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

//@RestController
//public class Control {
//    @Autowired
//    private StorageService storageService;
//    @PostMapping("/c")
//    public void create(@RequestParam("sth") String sth) {
//        storageService.upload(sth.getBytes());
//    }
//    @GetMapping("/r")
//    public String read(@RequestParam("link") String link) {
//        byte[] bytes = storageService.get(link);
//        String result = new String(bytes, StandardCharsets.UTF_8);
//        return result;
//    }
//    @PutMapping("/u")
//    public void update(@RequestParam("link") String link,
//                       @RequestParam("sth") String sth) {
//        storageService.update(link, sth.getBytes());
//    }
//    @DeleteMapping("/d")
//    public void delete(@RequestParam("link") String link) {
//        storageService.delete(link);
//    }
//}
