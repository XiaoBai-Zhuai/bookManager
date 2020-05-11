package com.gydx.bookManager.util;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 图片工具类
 */
@Component
public class ImageUtil {

    /**
     * 将网络图片转成Base64
     *
     * @param url 要访问的网络图片链接
     * @return 返回图片的base64编码
     */
    public String encodeImageToBase64(String url) throws Exception {
        try {
            byte[] data = getInputStreamByUrl(url);
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("图片上传失败");
        }
    }

    /**
     * 将本地图片转成base64编码格式
     * @param imageUrl
     * @return
     * @throws IOException
     */
    public String nativeImageToBase64(String imageUrl) throws IOException {
        InputStream inputStream;
        byte[] buffer = null;
        inputStream = new FileInputStream(imageUrl);
        int len = 0;
        while(len == 0) {
            len = inputStream.available();
        }
        buffer = new byte[len];
        inputStream.read(buffer);
        inputStream.close();
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * 将base64编码转换成图片
     *
     * @param imageStr  图片的base64编码
     * @param imageName 图片的名字
     * @return 是否转换成功
     */
    public boolean decodeBase64ToImage(String imageStr, String imageName) {
        if (imageStr == null || imageName.equals("")) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imageStr);
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream outputStream = new FileOutputStream("src/main/resources/static" + imageName);
            outputStream.write(b);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据图片链接将图片保存到resources目录下的images目录中
     *
     * @param imageUrl
     * @param imageName
     * @return
     * @throws IOException
     */
    public boolean saveImageByUrl(String imageUrl, String imageName) throws IOException {
        byte[] data = getInputStreamByUrl(imageUrl);
        try {
            File imageFile = new File(System.getProperty("user.dir") + "/src/main/resources/static/" + imageName);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            outputStream.write(data);
            outputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据图片链接获取图片的二进制数据
     *
     * @param imageUrl
     * @return
     * @throws IOException
     */
    public byte[] getInputStreamByUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inputStream = conn.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }

}
