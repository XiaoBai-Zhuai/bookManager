package com.gydx.bookManager.controller;

import com.gydx.bookManager.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @RequestMapping("/uploadExcel/{name}")
    public String uploadExcel(@PathVariable String name, @RequestParam("file") MultipartFile file) {
        System.out.println(file);
        return null;
    }

}
