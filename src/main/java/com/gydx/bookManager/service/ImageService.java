package com.gydx.bookManager.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    int uploadImage(MultipartFile file);
}
