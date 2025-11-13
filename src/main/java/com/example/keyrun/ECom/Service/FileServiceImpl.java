package com.example.keyrun.ECom.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements IFileService
{
    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException
    {
        String originalFileName = image.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName =  randomId + originalFileName.substring(originalFileName.lastIndexOf("."));
        String filePath = path + File.separator + fileName;
        File folder = new File(filePath);

        if(!folder.exists()){
            folder.mkdirs();
        }
        Files.copy(image.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
