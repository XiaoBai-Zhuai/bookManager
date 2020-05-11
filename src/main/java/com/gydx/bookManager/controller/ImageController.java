package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.service.ImageService;
import com.gydx.bookManager.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@CrossOrigin
@RestController
public class ImageController {

    @Autowired
    ImageUtil imageUtil;
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String imageName) throws Exception {
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/static/" + imageName);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }


    @RequestMapping("/getImgBase64")
    public String getImgBase64(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        String base64;
        try {
            base64 = imageUtil.encodeImageToBase64(receiveData.getImageUrl());
        } catch (Exception e) {
            jsonObject.put("msg", "图片读取失败");
            return jsonObject.toJSONString();
        }
        jsonObject.put("msg", "success");
        jsonObject.put("data", base64);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/uploadImage")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        int i = imageService.uploadImage(file);
        if (i == 0)
            jsonObject.put("msg", "保存失败");
        else
            jsonObject.put("msg", "保存成功");

        return jsonObject.toJSONString();
    }

}
