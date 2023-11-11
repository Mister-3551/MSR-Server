package eu.msr.server.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

@Service
public class FileService {

    public enum Types {
        PROFILE,
        MISSION,
        MAP
    }

    private final Path profileImages, otherImages, newsImages, weaponImages, missionImages, missionsMap, video;

    public FileService() throws IOException {
        this.profileImages = Paths.get("./files/images/profile").toAbsolutePath().normalize();
        this.otherImages = Paths.get("./files/images/other").toAbsolutePath().normalize();
        this.newsImages = Paths.get("./files/images/news").toAbsolutePath().normalize();
        this.weaponImages = Paths.get("./files/images/weapons").toAbsolutePath().normalize();
        this.missionImages = Paths.get("./files/missions/images").toAbsolutePath().normalize();
        this.missionsMap = Paths.get("./files/missions/maps").toAbsolutePath().normalize();
        this.video = Paths.get("./files/video/intro").toAbsolutePath().normalize();

        Files.createDirectories(profileImages);
        Files.createDirectories(otherImages);
        Files.createDirectories(newsImages);
        Files.createDirectories(weaponImages);
        Files.createDirectories(missionImages);
        Files.createDirectories(missionsMap);
        Files.createDirectories(video);
    }

    public String storeFile(String name, MultipartFile multipartFile, Types type) throws IOException {
        String fileName = null;
        Path targetLocation = null;

        switch (type) {
            case PROFILE -> {
                String extension = getImageExtension(multipartFile.getOriginalFilename());
                if (Objects.equals(extension, "jpg") || Objects.equals(extension, "png") || Objects.equals(extension, "gif")) {
                    fileName = name + "-image." + extension;
                    targetLocation = this.profileImages.resolve(fileName);
                }
            }
            case MISSION -> {
                String extension = getImageExtension(multipartFile.getOriginalFilename());
                if (Objects.equals(extension, "jpg") || Objects.equals(extension, "png") || Objects.equals(extension, "gif")) {
                    fileName = name + "-image." + extension;
                    targetLocation = this.missionImages.resolve(fileName);
                }
            }
            case MAP -> {
                String extension = getImageExtension(multipartFile.getOriginalFilename());
                if (Objects.equals(extension, "tmx")) {
                    fileName = name + "-map." + extension;
                    targetLocation = this.missionsMap.resolve(fileName);
                }
            }
        }

        if (fileName != null && targetLocation != null) {
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }
        return fileName;
    }

    public ResponseEntity<byte[]> profileImage(String name) throws IOException {
        try {
            byte[] image = Files.readAllBytes(Paths.get(profileImages + "/" + name));
            return ResponseEntity.ok().headers(getHeader(name)).contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (NoSuchFileException e) {
            return null;
        }
    }

    public ResponseEntity<byte[]> otherImage(String name) throws IOException {
        try {
            byte[] image = Files.readAllBytes(Paths.get(otherImages + "/" + name));
            return ResponseEntity.ok().headers(getHeader(name)).contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (NoSuchFileException e) {
            return null;
        }
    }

    public ResponseEntity<byte[]> newsImage(String name) throws IOException {
        try {
            byte[] image = Files.readAllBytes(Paths.get(newsImages + "/" + name));
            return ResponseEntity.ok().headers(getHeader(name)).contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (NoSuchFileException e) {
            return null;
        }
    }

    public ResponseEntity<byte[]> getWeaponImage(String name) throws IOException {
        try {
            byte[] image = Files.readAllBytes(Paths.get(weaponImages + "/" + name));
            return ResponseEntity.ok().headers(getHeader(name)).contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (NoSuchFileException e) {
            return null;
        }
    }

    public ResponseEntity<byte[]> getMissionImage(String name) throws IOException {
        try {
            byte[] image = Files.readAllBytes(Paths.get(missionImages + "/" + name));
            return ResponseEntity.ok().headers(getHeader(name)).contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (NoSuchFileException e) {
            return null;
        }
    }

    public ResponseEntity<byte[]> getMissionMap(String filename) throws Exception {
        byte[] map = Files.readAllBytes(Paths.get(missionsMap + "/" + filename));
        return ResponseEntity.ok().headers(getHeader(filename)).contentType(MediaType.APPLICATION_OCTET_STREAM).body(map);
    }

    public ResponseEntity<byte[]> getIntroVideo(String filename) throws Exception {
        byte[] video = Files.readAllBytes(Paths.get(this.video + "/" + filename));
        return ResponseEntity.ok().headers(getHeader(filename)).contentType(MediaType.APPLICATION_OCTET_STREAM).body(video);
    }

    private String getImageExtension(String imageName) {
        if (imageName == null) {
            return null;
        }
        String[] fileNameParts = imageName.split("\\.");
        return fileNameParts[fileNameParts.length - 1];
    }

    private HttpHeaders getHeader(String filename) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache"); header.add("Expires", "0");
        return header;
    }
}