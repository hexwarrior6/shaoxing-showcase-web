package com.hexwarrior6.shaoxingshowcasewebbackend.service.impl;

import com.hexwarrior6.shaoxingshowcasewebbackend.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    // 定义基础路径
    private final Path basePath = Paths.get("D:\\ZYH Code Place\\shaoxing-showcase-web\\shaoxing-showcase-web-frontend\\public\\image");

    @Override
    public String uploadImage(MultipartFile file, String folder) throws IOException {
        // 创建文件夹路径
        Path folderPath = basePath.resolve(folder);
        Files.createDirectories(folderPath);

        // 使用UUID生成一个唯一的文件名
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // 文件路径
        Path filePath = folderPath.resolve(uniqueFileName);

        // 保存文件
        Files.copy(file.getInputStream(), filePath);

        return uniqueFileName;
    }
}