package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.RestBean;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.ImageService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Validated
@RestController
@RequestMapping("/api")
public class ImageController {

    @Resource
    ImageService imageService;

    @PostMapping("/upload-image")
    public RestBean<String> uploadImage(@NotNull @RequestParam("file") MultipartFile file, @NotNull @RequestParam("folder") String folder) throws IOException {
        String fileName = imageService.uploadImage(file, folder);
        return RestBean.success(folder + "/" + fileName);
    }
}