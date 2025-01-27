package com.project.id.project.application.services.enc;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.project.id.project.application.services.s3storage.StorageService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


// @Service
// public class EncryptionUtil {
//     @Autowired
//     private HashesRepository hashesRepository;
//     private static final String ALGO = "AES/CBC/PKCS5PADDING";
//     private static final int AES_KEY_SIZE = 16;
//     public String encrypt(String value) {
//         try {
//             Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//             String preferredUsername = jwt.getClaimAsString("preferred_username");
//             String secondLevelhash = hashesRepository.getHashWithUname(preferredUsername).getHash();
//             SecretKeySpec skeySpec = createKey(secondLevelhash);
//             IvParameterSpec iv = generateIv();
//             Cipher cipher = Cipher.getInstance(ALGO);
//             cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//             byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
//             return Base64.getEncoder().encodeToString(iv.getIV()) + ":" + Base64.getEncoder().encodeToString(encrypted);
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//         return null;
//     }
//     public String decrypt(String encrypted) {
//         try {
//             Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//             String preferredUsername = jwt.getClaimAsString("preferred_username");
//             String secondLevelhash = hashesRepository.getHashWithUname(preferredUsername).getHash();
//             SecretKeySpec skeySpec = createKey(secondLevelhash);
//             String[] parts = encrypted.split(":");
//             IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[0]));
//             byte[] encryptedData = Base64.getDecoder().decode(parts[1]);
//             Cipher cipher = Cipher.getInstance(ALGO);
//             cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//             byte[] original = cipher.doFinal(encryptedData);
//             return new String(original, StandardCharsets.UTF_8);
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//         return null;
//     }
//     private SecretKeySpec createKey(String keyData) {
//         try {
//             MessageDigest digest = MessageDigest.getInstance("SHA-256");
//             byte[] hash = digest.digest(keyData.getBytes(StandardCharsets.UTF_8));
//             return new SecretKeySpec(hash, 0, AES_KEY_SIZE, "AES");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
//     private IvParameterSpec generateIv() {
//         try {
//             byte[] iv = new byte[16];
//             SecureRandom random = new SecureRandom();
//             random.nextBytes(iv);
//             return new IvParameterSpec(iv);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
// }


// @Component
// public class EncryptionUtil {
//     @Autowired
//     private HashesRepository hashesRepository;
//     private static final String ALGO = "AES/CBC/PKCS5PADDING";
//     private static final int AES_KEY_SIZE = 16;
//     public String encrypt(String value) {
//         try {
//             Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//             String sub = jwt.getClaimAsString("sub");
//             String preferredUsername = jwt.getClaimAsString("preferred_username");

//             String secondLevelhash = hashesRepository.getHashWithUname(preferredUsername).getHash();

//             String keyData = secondLevelhash;
//             SecretKeySpec skeySpec = createKey(keyData);
//             IvParameterSpec iv = generateIv();
//             Cipher cipher = Cipher.getInstance(ALGO);
//             cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//             byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
//             return Base64.getEncoder().encodeToString(iv.getIV()) + ":" + Base64.getEncoder().encodeToString(encrypted);
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//         return null;
//     }
//     public String decrypt(String encrypted) {
//         try {
//             Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//             String sub = jwt.getClaimAsString("sub");
//             String preferredUsername = jwt.getClaimAsString("preferred_username");
//             String keyData = sub + preferredUsername;
//             SecretKeySpec skeySpec = createKey(keyData);
//             String[] parts = encrypted.split(":");
//             IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[0]));
//             byte[] encryptedData = Base64.getDecoder().decode(parts[1]);
//             Cipher cipher = Cipher.getInstance(ALGO);
//             cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//             byte[] original = cipher.doFinal(encryptedData);
//             return new String(original, StandardCharsets.UTF_8);
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//         return null;
//     }
//     private SecretKeySpec createKey(String keyData) {
//         try {
//             MessageDigest digest = MessageDigest.getInstance("SHA-256");
//             byte[] hash = digest.digest(keyData.getBytes(StandardCharsets.UTF_8));
//             return new SecretKeySpec(hash, 0, AES_KEY_SIZE, "AES");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
//     private IvParameterSpec generateIv() {
//         try {
//             byte[] iv = new byte[16];
//             SecureRandom random = new SecureRandom();
//             random.nextBytes(iv);
//             return new IvParameterSpec(iv);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
// }



// @Component
// public class EncryptionUtil {
//     @Autowired
//     private HashesRepository hashesRepository;
//     private static final String ALGO = "AES/CBC/PKCS5PADDING";
//     private static final int AES_KEY_SIZE = 16;
//     public String encrypt(String value) {
//         try {
//             Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//             String sub = jwt.getClaimAsString("sub");
//             String preferredUsername = jwt.getClaimAsString("preferred_username");

//             String secondLevelhash = hashesRepository.getHashWithUname(preferredUsername).getHash();

//             String keyData = secondLevelhash;
//             SecretKeySpec skeySpec = createKey(keyData);
//             IvParameterSpec iv = generateIv();
//             Cipher cipher = Cipher.getInstance(ALGO);
//             cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//             byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
//             return Base64.getEncoder().encodeToString(iv.getIV()) + ":" + Base64.getEncoder().encodeToString(encrypted);
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//         return null;
//     }
//     public String decrypt(String encrypted) {
//         try {
//             Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//             String sub = jwt.getClaimAsString("sub");
//             String preferredUsername = jwt.getClaimAsString("preferred_username");

//             String secondLevelhash = hashesRepository.getHashWithUname(preferredUsername).getHash();

//             String keyData = secondLevelhash;
//             SecretKeySpec skeySpec = createKey(keyData);
//             String[] parts = encrypted.split(":");
//             IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[0]));
//             byte[] encryptedData = Base64.getDecoder().decode(parts[1]);
//             Cipher cipher = Cipher.getInstance(ALGO);
//             cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//             byte[] original = cipher.doFinal(encryptedData);
//             return new String(original, StandardCharsets.UTF_8);
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//         return null;
//     }
//     private SecretKeySpec createKey(String keyData) {
//         try {
//             MessageDigest digest = MessageDigest.getInstance("SHA-256");
//             byte[] hash = digest.digest(keyData.getBytes(StandardCharsets.UTF_8));
//             return new SecretKeySpec(hash, 0, AES_KEY_SIZE, "AES");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
//     private IvParameterSpec generateIv() {
//         try {
//             byte[] iv = new byte[16];
//             SecureRandom random = new SecureRandom();
//             random.nextBytes(iv);
//             return new IvParameterSpec(iv);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
// }




// @Component
// public class EncryptionUtil {
//     @Autowired
//     private HashesRepository hashesRepository;
//     @Autowired
//     private StorageService storageService;
//     private static final String ALGO = "AES/CBC/PKCS5PADDING";
//     private static final int AES_KEY_SIZE = 16;
//     public synchronized String encrypt(String value) {
//         try {
//             Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//             String sub = jwt.getClaimAsString("sub");
//             String preferredUsername = jwt.getClaimAsString("preferred_username");

//             String secondLevelhash = hashesRepository.getHashWithUname(preferredUsername).getHash();

//             String keyData = secondLevelhash;
//             SecretKeySpec skeySpec = createKey(keyData);
//             IvParameterSpec iv = generateIv();
//             Cipher cipher = Cipher.getInstance(ALGO);
//             cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//             byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
//             return Base64.getEncoder().encodeToString(iv.getIV()) + ":" + Base64.getEncoder().encodeToString(encrypted);
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//         return null;
//     }
//     public synchronized String decrypt(String encrypted) {
//         try {
//             Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//             String sub = jwt.getClaimAsString("sub");
//             String preferredUsername = jwt.getClaimAsString("preferred_username");

//             String secondLevelhash = new String(storageService.get(preferredUsername), StandardCharsets.UTF_8);
//             String keyData = secondLevelhash;
//             SecretKeySpec skeySpec = createKey(keyData);
//             String[] parts = encrypted.split(":");
//             IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[0]));
//             byte[] encryptedData = Base64.getDecoder().decode(parts[1]);
//             Cipher cipher = Cipher.getInstance(ALGO);
//             cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//             byte[] original = cipher.doFinal(encryptedData);
//             return new String(original, StandardCharsets.UTF_8);
//         } catch (Exception ex) {
//             System.out.println("error");
//         }
//         return null;
//     }
//     private SecretKeySpec createKey(String keyData) {
//         try {
//             MessageDigest digest = MessageDigest.getInstance("SHA-256");
//             byte[] hash = digest.digest(keyData.getBytes(StandardCharsets.UTF_8));
//             return new SecretKeySpec(hash, 0, AES_KEY_SIZE, "AES");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
//     private IvParameterSpec generateIv() {
//         try {
//             byte[] iv = new byte[16];
//             SecureRandom random = new SecureRandom();
//             random.nextBytes(iv);
//             return new IvParameterSpec(iv);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
// }



@Component
public class EncryptionUtil {
    @Autowired
    private HttpServletRequest request;
    private static final String ALGO = "AES/CBC/PKCS5PADDING";
    private static final int AES_KEY_SIZE = 16;

    public synchronized String encrypt(String value) {
        try {
            String keyData = getKeyFromCookie("flh");
            if (keyData == null) {
                throw new IllegalStateException("Encryption key not found in cookies");
            }

            SecretKeySpec skeySpec = createKey(keyData);
            IvParameterSpec iv = generateIv();
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(iv.getIV()) + ":" + Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public synchronized String decrypt(String encrypted) {
        try {
            String keyData = getKeyFromCookie("flh");
            if (keyData == null) {
                throw new IllegalStateException("Decryption key not found in cookies");
            }

            SecretKeySpec skeySpec = createKey(keyData);
            String[] parts = encrypted.split(":");
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[0]));
            byte[] encryptedData = Base64.getDecoder().decode(parts[1]);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(encryptedData);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            System.out.println("error");
        }
        return null;
    }

    private String getKeyFromCookie(String cookieName) {
        if (request == null) {
            System.out.println("request is null");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private SecretKeySpec createKey(String keyData) {
        try {
            keyData = DigestUtils.sha256Hex(keyData);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(keyData.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(hash, 0, AES_KEY_SIZE, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private IvParameterSpec generateIv() {
        try {
            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            return new IvParameterSpec(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}