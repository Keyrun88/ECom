package com.example.keyrun.ECom.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    String uploadImage(String path, MultipartFile image) throws IOException;
}
