package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public int uploadImage(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String path = System.getProperty("user.dir");
        assert filename != null;
        File dest = new File(path + "/src/main/resources/static/" + filename);
        try {
            file.transferTo(dest);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
