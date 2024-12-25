package com.hexwarrior6.shaoxingshowcasewebbackend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String uploadImage(MultipartFile file, String folder) throws IOException;
}
