package com.project.id.project.application.services.enc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Service
// public class HashService {

//     @Autowired
//     private HashesRepository hashesRepository;

//     public void saveHash(String username, String hash) {
//         Hashes hashes = new Hashes();
//         hashes.setUsername(username);
//         hashes.setHash(hash);
//         hashesRepository.save(hashes);
//     }

//     public String getHashByUsername(String username) {
//         Hashes hashes = hashesRepository.getHashWithUname(username);
//         return (hashes != null) ? hashes.getHash() : null;
//     }
// }