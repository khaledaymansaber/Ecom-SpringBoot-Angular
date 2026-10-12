package com.ecom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final Path uploadLocation;

    public ImageService(@Value("${app.upload.dir:uploads/images}") String uploadDir) {
        this.uploadLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadLocation);
        } catch (IOException e) {
            logger.error("Could not create upload directory", e);
        }
    }

    public List<String> saveImages(List<MultipartFile> files) {
        List<String> imageNames = new ArrayList<>();
        if (files == null || files.isEmpty()) {
            return imageNames;
        }

        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String newFileName = UUID.randomUUID() + extension;

                try {
                    Path targetPath = this.uploadLocation.resolve(newFileName);
                    Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    imageNames.add(newFileName);
                } catch (IOException ex) {
                    logger.error("Failed to store image {}", originalFilename, ex);
                }
            }
        }
        return imageNames;
    }

    public boolean deleteImage(String imageName) {
        if (imageName == null || imageName.isBlank()) {
            return false;
        }
        try {
            Path filePath = this.uploadLocation.resolve(imageName);
            return Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            logger.warn("Could not delete image {}", imageName, ex);
            return false;
        }
    }
}
